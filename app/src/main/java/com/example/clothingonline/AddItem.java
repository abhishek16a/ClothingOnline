package com.example.clothingonline;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import api.ClothesApi;
import model.Clothes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

import java.io.File;
import java.io.IOException;

public class AddItem extends AppCompatActivity {
    private EditText etItemName, etItemPrice,etItemDescription;
    private ImageView ivView;
    private Button btnAddItem, btnReset,back;
    private String imagePath, imageName;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etItemName = findViewById(R.id.etItemName);
        etItemPrice= findViewById(R.id.etItemPrice);
        etItemDescription= findViewById(R.id.etItemDescription);
        ivView= findViewById(R.id.ivImage);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveItem();
                Intent intent = new Intent(AddItem.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });


    }

    private void SaveItem() {

        SaveImageOnly();
        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
        String itemName = etItemName.getText().toString();
        String itemPrice = etItemPrice.getText().toString();
        String itemDescription = etItemDescription.getText().toString();
        String itemImageName = imageName;
        Clothes clothes = new Clothes(itemName,itemPrice,itemDescription,itemImageName);
        Call<Void> listCall = clothesApi.addItems(clothes);
//                addFieldItems(itemName,itemPrice,itemImageName,itemDescription);

        listCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddItem.this, "Added Item Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddItem.this, "Item Not Added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void BrowseImage(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please Select An Image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
//        etItemName.setText(imagePath);
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String ivImag) {
        File imgFile = new File(ivImag);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivView.setImageBitmap(myBitmap);
        }
    }

    private void SaveImageOnly() {
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        ClothesApi clothesApi = Url.getInstance().create(ClothesApi.class);
        Call<ImageResponse> responseCall = clothesApi.uploadImage(body);

        StrictMode();
        try{
            Response<ImageResponse> imageResponseResponse = responseCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        }catch (IOException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}
