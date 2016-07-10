package com.up.up_opportunity.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.up.up_opportunity.R;
import com.up.up_opportunity.model.forum.Question;
import com.yelp.clientlib.entities.Business;

/**
 * Created by adao1 on 7/9/2016.
 */
public class QuestionViewHolder extends RecyclerView.ViewHolder{
    public TextView questionTV;
    public TextView timeTV;
    public TextView dateTV;
    public TextView numAnswersTV;
    public QuestionViewHolder(View itemView) {
        super(itemView);
        questionTV = (TextView)itemView.findViewById(R.id.question_text_id);
        timeTV = (TextView)itemView.findViewById(R.id.question_time_id);
        dateTV = (TextView)itemView.findViewById(R.id.question_date_id);
        numAnswersTV = (TextView)itemView.findViewById(R.id.question_numAnswers_id);
    }
    public void bind(final Question question, final OnQuestionClickListener listener){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onQuestionClick(question);
            }
        });
    }
    public interface OnQuestionClickListener{
        void onQuestionClick(Question question);
    }

}
