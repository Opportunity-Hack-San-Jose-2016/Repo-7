package com.up.up_opportunity.fragments.jobs;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.up_opportunity.R;
import com.up.up_opportunity.model.job.IndeedResults;

import java.util.List;

/**
 * Created by samsiu on 7/9/16.
 */
public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.JobsViewHolder> {
    private static final String TAG = JobsRVAdapter.class.getSimpleName();

    int color;
    List<IndeedResults> results;
    private JobClickListener jobClickListener;

    public interface JobClickListener{
        void onCardViewClick(String link);
    }


    public static class JobsViewHolder extends RecyclerView.ViewHolder{

        CardView jobCardView;
        TextView titleTextView;
        TextView companyTextView;
        TextView locationTextView;
        TextView postedTextView;
        ImageView jobImageView;

        public JobsViewHolder(View itemView) {
            super(itemView);
            jobCardView = (CardView)itemView.findViewById(R.id.job_cardView);
            titleTextView = (TextView)itemView.findViewById(R.id.job_title_textView);
            companyTextView = (TextView)itemView.findViewById(R.id.job_company_textView);
            locationTextView = (TextView)itemView.findViewById(R.id.job_location_textView);
            postedTextView = (TextView)itemView.findViewById(R.id.job_posted_textView);
            jobImageView = (ImageView)itemView.findViewById(R.id.job_imageView);

        }

        public void bind(final JobClickListener jobClickListener, final String link){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobClickListener.onCardViewClick(link);
                }
            });
        }

    }

    public JobsRVAdapter(JobClickListener jobClickListener, List<IndeedResults> results){
        this.jobClickListener = jobClickListener;
        this.results = results;
    }

    @Override
    public void onViewAttachedToWindow(JobsViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_jobs, parent, false);
        JobsViewHolder jobsViewHolder = new JobsViewHolder(view);

        color = ContextCompat.getColor(parent.getContext(), R.color.colorAccent);


        return jobsViewHolder;
    }

    @Override
    public void onBindViewHolder(JobsViewHolder holder, int position) {

        holder.titleTextView.setText(results.get(position).getJobtitle());
        holder.companyTextView.setText(results.get(position).getCompany());
        holder.locationTextView.setText(results.get(position).getFormattedLocationFull());
        holder.postedTextView.setText(results.get(position).getFormattedRelativeTime());
        holder.jobImageView.setImageResource(R.drawable.ic_card_travel_black_24dp);
        holder.jobImageView.setColorFilter(color);

        final String url = results.get(position).getUrl();
        holder.bind(jobClickListener, url);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
