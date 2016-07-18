package com.up.up_opportunity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by adao1 on 7/9/2016.
 */
public class FoodBankAdapter extends RecyclerView.Adapter<FoodBankAdapter.ViewHolder> {

    private static final String TAG = "FOODBANK_ADAPER";
    private List<Business> foodBanks;
    private Context context;
    private FoodClickListener foodClickListener;

    public interface FoodClickListener{
        void onCardViewClick(String link);
    }


    public FoodBankAdapter(FoodClickListener foodClickListener, List<Business> foodBanks) {
        this.foodClickListener = foodClickListener;
        this.foodBanks = foodBanks;
    }

    @Override
    public FoodBankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.foodbank_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodBankAdapter.ViewHolder holder, int position) {
        Business foodBank = foodBanks.get(position);

        final String url = foodBank.mobileUrl();
        holder.bind(foodClickListener, url);

        holder.titleTV.setText(foodBank.name());
        holder.numberTV.setText(foodBank.displayPhone());
        holder.addressTV.setText(foodBank.location().address().toString().substring(1,foodBank.location().address().toString().length()-1)
                +", "+foodBank.location().city()+", "+foodBank.location().stateCode());

        if (foodBank.imageUrl()!=null) {
            Picasso.with(context)
                    .load(foodBank.imageUrl().replaceAll("ms", "ls"))
                    .into(holder.imageIV);
        }
        
    }

    @Override
    public int getItemCount() {
        return foodBanks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTV;
        public TextView numberTV;
        public TextView addressTV;
        public ImageView imageIV;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTV = (TextView)itemView.findViewById(R.id.foodbank_item_name);
            numberTV = (TextView)itemView.findViewById(R.id.foodbank_item_number);
            addressTV = (TextView)itemView.findViewById(R.id.foodbank_item_address);
            imageIV = (ImageView)itemView.findViewById(R.id.foodbank_item_image);
        }

        public void bind(final FoodClickListener foodClickListener, final String link){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    foodClickListener.onCardViewClick(link);
                }
            });
        }

    }
}
