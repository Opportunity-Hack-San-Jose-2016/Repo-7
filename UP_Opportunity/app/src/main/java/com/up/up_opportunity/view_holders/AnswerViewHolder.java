package com.up.up_opportunity.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.up.up_opportunity.R;

/**
 * Created by adao1 on 7/10/2016.
 */
public class AnswerViewHolder extends RecyclerView.ViewHolder{
    public TextView answerTV;

    public AnswerViewHolder(View itemView) {
        super(itemView);
        answerTV = (TextView)itemView.findViewById(R.id.answer_text);
    }

}
