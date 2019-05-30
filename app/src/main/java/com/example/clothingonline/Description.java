package com.example.clothingonline;

import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.clothingonline.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Description extends AppCompatActivity {
    private TextView tvName, tvPrice, tvDescription;
    private CircleImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        tvName = findViewById(R.id.tvItemName_description);
        tvPrice = findViewById(R.id.tvPrice_description);
        tvDescription = findViewById(R.id.tvDescription_description);
        img = findViewById(R.id.circleImg_description);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            tvName.setText(bundle.getString("itemName"));
            tvPrice.setText(bundle.getString("price"));
            tvDescription.setText(bundle.getString("description"));
            String a = bundle.getString("image");
            StrictMode();
            try{
                URL url = new URL(a);
                img.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}