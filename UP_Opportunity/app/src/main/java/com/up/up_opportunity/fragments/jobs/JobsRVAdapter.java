package com.up.up_opportunity.fragments.jobs;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.up.up_opportunity.R;
import com.up.up_opportunity.model.job.IndeedResults;

import java.util.List;

/**
 * Created by samsiu on 7/9/16.
 */
public class JobsRVAdapter extends RecyclerView.Adapter<JobsRVAdapter.JobsViewHolder> {

    List<IndeedResults> results;

    public static class JobsViewHolder extends RecyclerView.ViewHolder{

        CardView jobCardView;
        TextView titleTextView;
        TextView companyTextView;
        TextView cityTextView;
        TextView stateTextView;
        TextView postedTextView;

        public JobsViewHolder(View itemView) {
            super(itemView);
            jobCardView = (CardView)itemView.findViewById(R.id.job_cardView);
            titleTextView = (TextView)itemView.findViewById(R.id.job_title_textView);
            companyTextView = (TextView)itemView.findViewById(R.id.job_company_textView);
            cityTextView = (TextView)itemView.findViewById(R.id.job_city_textView);
            stateTextView = (TextView)itemView.findViewById(R.id.job_state_textView);
            postedTextView = (TextView)itemView.findViewById(R.id.job_posted_textView);
        }
    }

    public JobsRVAdapter(List<IndeedResults> results){
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

        return jobsViewHolder;
    }

    @Override
    public void onBindViewHolder(JobsViewHolder holder, int position) {

        holder.titleTextView.setText(results.get(position).getJobtitle());
        holder.companyTextView.setText(results.get(position).getCompany());
        holder.cityTextView.setText(results.get(position).getCity());
        holder.stateTextView.setText(results.get(position).getState());
        holder.postedTextView.setText(results.get(position).getFormattedRelativeTime());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
