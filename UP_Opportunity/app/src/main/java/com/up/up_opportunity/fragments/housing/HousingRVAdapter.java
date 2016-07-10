package com.up.up_opportunity.fragments.housing;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.up_opportunity.R;
import com.up.up_opportunity.model.housing.HousingHUD;

import java.util.List;

/**
 * Created by samsiu on 7/10/16.
 */
public class HousingRVAdapter extends RecyclerView.Adapter<HousingRVAdapter.HousingViewHolder> {
    private static final String TAG = HousingRVAdapter.class.getSimpleName();

    int color;
    HousingHUD results;
    private HousingClickListener housingClickListener;

    public interface HousingClickListener{
        void onCardViewClick(String link);
    }


    public static class HousingViewHolder extends RecyclerView.ViewHolder{

        CardView jobCardView;
        TextView titleTextView;
        TextView companyTextView;
        TextView locationTextView;
        TextView postedTextView;
        ImageView jobImageView;

        public HousingViewHolder(View itemView) {
            super(itemView);
            jobCardView = (CardView)itemView.findViewById(R.id.job_cardView);
            titleTextView = (TextView)itemView.findViewById(R.id.job_title_textView);
            companyTextView = (TextView)itemView.findViewById(R.id.job_company_textView);
            locationTextView = (TextView)itemView.findViewById(R.id.job_location_textView);
            postedTextView = (TextView)itemView.findViewById(R.id.job_posted_textView);
            jobImageView = (ImageView)itemView.findViewById(R.id.job_imageView);

        }

        public void bind(final HousingClickListener housingClickListener, final String link){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    housingClickListener.onCardViewClick(link);
                }
            });
        }

    }

    public HousingRVAdapter(HousingClickListener housingClickListener, HousingHUD results){
        this.housingClickListener = housingClickListener;
        this.results = results;
    }

    @Override
    public void onViewAttachedToWindow(HousingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public HousingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_jobs, parent, false);
        HousingViewHolder housingViewHolder = new HousingViewHolder(view);

        color = ContextCompat.getColor(parent.getContext(), R.color.colorPrimary);


        return housingViewHolder;
    }

    @Override
    public void onBindViewHolder(HousingViewHolder holder, int position) {

        holder.titleTextView.setText(results.getHousing().get(position).getAgc_ADDR_LATITUDE());
        holder.companyTextView.setText(results.getHousing().get(position).getAdr2());
        holder.locationTextView.setText(results.getHousing().get(position).getAdr1());
        holder.postedTextView.setText(results.getHousing().get(position).getCity());
        holder.jobImageView.setImageResource(R.drawable.ic_card_travel_black_24dp);
        holder.jobImageView.setColorFilter(color);

        final String url = results.getHousing().get(position).getWeburl();
        holder.bind(housingClickListener, url);

    }

    @Override
    public int getItemCount() {
        return results.getHousing().size();
    }
}
