package com.up.up_opportunity.recycler;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.event.GoogleEvent;

import java.util.ArrayList;

/**
 * Created by Billy on 7/9/16.
 */
public class GoogleEventsRecyclerView extends android.support.v7.widget.RecyclerView.Adapter<GoogleEventsRecyclerView.RecyclerViewHolder> {

    int color;
    private GoogleEvent data;
    private Context context;

    public GoogleEventsRecyclerView(GoogleEvent data) {
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.title.setText(data.getResults().get(position).getName());
        holder.info.setText(data.getResults().get(position).getVicinity());
        holder.imageView.setImageResource(R.drawable.ic_map_black_24dp);
        holder.imageView.setColorFilter(color);

       // Log.d("RV Adapter", "Events Size: " + data.getResults().size());
//        String imageURI = data.getResults().get(position).getIcon();
//
//        if (imageURI.isEmpty()) {
//            imageURI = "R.drawable.blank_white.png";
//        }
//
//        Glide
//                .with(context)
//                .load(imageURI)
//                .centerCrop()
//                .placeholder(R.drawable.blank_white)
//                .crossFade()
//                .override(150,150)
//                .into(holder.imageView);


    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recyclerview_custom_layout, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(view);
        color = ContextCompat.getColor(parent.getContext(), R.color.colorAccentAlpha);

        return vh;
    }

    @Override
    public int getItemCount() {
        return data.getResults().size();
    }


}
