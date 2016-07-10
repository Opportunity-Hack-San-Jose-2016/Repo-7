package com.up.up_opportunity.recycler;

import android.content.Context;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.coupons.Coupons;
import com.up.up_opportunity.model.coupons.CouponsArray;

import java.util.ArrayList;

/**
 * Created by Billy on 7/9/16.
 */
public class CouponsRecyclerAdapter extends android.support.v7.widget.RecyclerView.Adapter<CouponsRecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Coupons> data;
    private Context context;

    public CouponsRecyclerAdapter(ArrayList<Coupons> data) {
        this.data = data;
    }

    // this is where we setup TextView
    public class RecyclerViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView info;

        public RecyclerViewHolder (final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageOne_id);
            title = (TextView) itemView.findViewById(R.id.title_id);
            info = (TextView) itemView.findViewById(R.id.info_ID);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.title.setText(data.get(position).getDealTitle());
        String imageURI = data.get(position).getShowImageStandardBig();
        holder.info.setText(data.get(position).getDealinfo());

        if (imageURI.isEmpty()) {
            imageURI = "R.drawable.blank_white.png";
        }

        Glide
                .with(context)
                .load(imageURI)
                .centerCrop()
                .placeholder(R.drawable.blank_white)
                .crossFade()
                .override(150,150)
                .into(holder.imageView);

    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_custom_layout, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
