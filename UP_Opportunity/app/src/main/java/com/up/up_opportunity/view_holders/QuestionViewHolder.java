package com.up.up_opportunity.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.up.up_opportunity.R;

/**
 * Created by adao1 on 7/9/2016.
 */
public class QuestionViewHolder extends RecyclerView.ViewHolder{
    public TextView questionTV;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        questionTV = (TextView)itemView.findViewById(R.id.question_text_id);
    }
}
