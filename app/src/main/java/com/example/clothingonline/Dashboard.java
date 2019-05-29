package com.example.onlineclothshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clothingonline.R;

import java.util.List;

import api.ClothesApi;
import model.Clothes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import url.Url;

public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btn,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        btn=findViewById(R.id.btn);
        back=findViewById(R.id.back);

        recyclerView = findViewById(R.id.recyclerView);

        ShowItem();


        //FloatingActionButton fab = findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddItem.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void ShowItem() {
        Retrofit retrofit = Url.getInstance();
        ClothesApi clothesApi = retrofit.create(ClothesApi.class);

        Call<List<Clothes>> listCall = clothesApi.getClothes();

        listCall.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                if(response.body() != null) {
                    List<Clothes> clothes = response.body();
                    ClothesAdapter clothesAdapter = new ClothesAdapter(Dashboard.this, clothes);
                    recyclerView.setAdapter(clothesAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Dashboard.this));
                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Can't load clothes", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
