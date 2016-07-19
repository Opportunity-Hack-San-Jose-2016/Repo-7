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

    private CouponsArray data;
    private Context context;
    private CouponClickListener couponClickListener;

    public interface CouponClickListener{
        void onCardViewClick(String link);
    }


    // this is where we setup TextView
    public class RecyclerViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CardView couponCardView;
        ImageView imageView;
        TextView title;
        TextView info;

        public RecyclerViewHolder (final View itemView) {
            super(itemView);
            couponCardView = (CardView)itemView.findViewById(R.id.custom_cardView);
            imageView = (ImageView) itemView.findViewById(R.id.imageOne_id);
            title = (TextView) itemView.findViewById(R.id.title_id);
            info = (TextView) itemView.findViewById(R.id.info_ID);
        }

        public void bind(final CouponClickListener couponClickListener, final String link){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    couponClickListener.onCardViewClick(link);
                }
            });
        }
    }


    public CouponsRecyclerAdapter(CouponClickListener couponClickListener, CouponsArray data) {
        this.couponClickListener = couponClickListener;
        this.data = data;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.title.setText(data.getCoupons().get(position).getDealTitle());
        holder.info.setVisibility(View.GONE);

        String url = data.getCoupons().get(position).getURL();
        holder.bind(couponClickListener, url);

        String imageURI = data.getCoupons().get(position).getShowImageStandardBig();

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
        return data.getCoupons().size();
    }


}
