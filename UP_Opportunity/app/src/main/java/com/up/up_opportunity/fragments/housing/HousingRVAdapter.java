package com.up.up_opportunity.fragments.housing;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        TextView houseAddress;
        TextView cityTextView;
        TextView agencyTextView;
        TextView phoneTextView;

        public HousingViewHolder(View itemView) {
            super(itemView);
            jobCardView = (CardView)itemView.findViewById(R.id.job_cardView);
            houseAddress = (TextView)itemView.findViewById(R.id.housing_address_textView);
            cityTextView = (TextView)itemView.findViewById(R.id.housing_city_textView);
            agencyTextView = (TextView)itemView.findViewById(R.id.housing_agency_textView);
            phoneTextView = (TextView)itemView.findViewById(R.id.housing_phone_textView);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_housing, parent, false);
        HousingViewHolder housingViewHolder = new HousingViewHolder(view);

        color = ContextCompat.getColor(parent.getContext(), R.color.colorPrimary);


        return housingViewHolder;
    }

    @Override
    public void onBindViewHolder(HousingViewHolder holder, int position) {

        holder.cityTextView.setText(results.getHousing().get(position).getCity());
        holder.houseAddress.setText(results.getHousing().get(position).getAdr1());
        holder.agencyTextView.setText(results.getHousing().get(position).getNme());
        holder.phoneTextView.setText(results.getHousing().get(position).getPhone1());

        final String url = results.getHousing().get(position).getWeburl();
        Log.i(TAG,"Clicked URL" + url);
        holder.bind(housingClickListener, url);

    }

    @Override
    public int getItemCount() {
        return results.getHousing().size();
    }
}
