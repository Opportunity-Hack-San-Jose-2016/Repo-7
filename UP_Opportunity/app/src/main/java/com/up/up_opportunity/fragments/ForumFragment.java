package com.up.up_opportunity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.up.up_opportunity.AnswerActivity;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.forum.Question;
import com.up.up_opportunity.recycler.RecyclerClickListener;
import com.up.up_opportunity.recycler.RecyclerViewListener;
import com.up.up_opportunity.view_holders.QuestionViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adao1 on 7/9/2016.
 */
public class ForumFragment extends Fragment implements QuestionViewHolder.OnQuestionClickListener {

    private static final String TAG = "FORUM_FRAGMENT";
    private EditText questionET;
    private Button submitButton;
    private RecyclerView questionsRV;
    private Firebase firebase;
    private Firebase questionFB;
    private Firebase questionsFB;
    private QuestionViewHolder.OnQuestionClickListener listener;
    private FirebaseRecyclerAdapter<Question, QuestionViewHolder> questionAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view){
        questionET = (EditText) view.findViewById(R.id.forum_ED_id);
        submitButton = (Button) view.findViewById(R.id.forum_submit_id);
        questionsRV = (RecyclerView)view.findViewById(R.id.forum_RV_id);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.i(TAG, "onViewCreated: ");
        listener = this;
        initFirebase();
        setSubmitListener();
        setQuestionRV();
    }

    private void setSubmitListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFirebase(questionET.getText().toString());
                questionET.getText().clear();
            }
        });
    }

    private void initFirebase() {
        firebase = new Firebase("https://up-app.firebaseio.com/");
    }

    private void saveToFirebase(String question) {
        Question question1 = new Question(question,getCurrentTimeStamp());
        questionsFB = firebase.child(question.replace(".","").replace("#","").replace("$","").replace("[","").replace("]",""));
        questionsFB.setValue(question1);
//        questionFB = questionsFB.child("Question");
//        questionFB.setValue(question);
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private void makeQuestionAdapter() {
        questionAdapter = new FirebaseRecyclerAdapter<Question, QuestionViewHolder>(Question.class, R.layout.question_item,QuestionViewHolder.class,firebase) {
            @Override
            protected void populateViewHolder(QuestionViewHolder questionViewHolder, Question question, int i) {
                questionViewHolder.questionTV.setText(question.getQuestion());
                questionViewHolder.numAnswersTV.setText(question.getNumAnswers());
                questionViewHolder.dateTV.setText(question.getTimestamp().substring(0,11));
                questionViewHolder.timeTV.setText(question.getTimestamp().substring(11));
                questionViewHolder.bind(question,listener);
            }
        };
    }

    private void setQuestionRV(){
        makeQuestionAdapter();
        if(questionsRV != null){
            questionsRV.setAdapter(questionAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            questionsRV.setLayoutManager(layoutManager);
        }

    }

    @Override
    public void onQuestionClick(Question question) {
        Intent answerIntent = new Intent(getActivity(), AnswerActivity.class);
        answerIntent.putExtra("ANSWER_KEY",question.getQuestion().replace(".","").replace("#","").replace("$","").replace("[","").replace("]",""));
//        startActivity(answerIntent);
    }
    private void setClicker(){
       questionsRV.addOnItemTouchListener(new RecyclerViewListener(getContext(), questionsRV, new RecyclerClickListener() {
           @Override
           public void onClick(View view, int position) {

           }

           @Override
           public void onLongClick(View view, int position) {

           }
       }));
    }

}