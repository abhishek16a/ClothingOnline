package com.example.clothingonline;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Clothes;
import url.Url;

class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder> {

    Context mContext;
    List<Clothes> clothesList;

    public ClothesAdapter(Context mContext, List<Clothes> clothesList) {
        this.mContext = mContext;
        this.clothesList = clothesList;
    }

    @NonNull
    @Override
    public ClothesAdapter.ClothesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return  new ClothesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesAdapter.ClothesViewHolder clothesViewHolder, int i) {

        final Clothes clothes = clothesList.get(i);
        final String imgPath = Url.BASE_URL + "uploads/" + clothes.getItemimage();
        StrictMode();
        try{
            URL url = new URL(imgPath);
            clothesViewHolder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        clothesViewHolder.tvItemName.setText(clothes.getItemname());

        clothesViewHolder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Description.class);
                i.putExtra("itemName", clothes.getItemname());
                i.putExtra("price", clothes.getItemprice());
                i.putExtra("description", clothes.getItemdesc());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothesList.size();
    }

    public class ClothesViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imgItem;
        TextView tvItemName;
        public ClothesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItemPic);
            tvItemName = itemView.findViewById(R.id.tvItemName);
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}