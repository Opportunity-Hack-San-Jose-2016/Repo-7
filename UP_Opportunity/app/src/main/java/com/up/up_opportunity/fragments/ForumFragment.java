package com.up.up_opportunity.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.up.up_opportunity.R;
import com.up.up_opportunity.model.forum.Question;
import com.up.up_opportunity.view_holders.QuestionViewHolder;

/**
 * Created by adao1 on 7/9/2016.
 */
public class ForumFragment extends Fragment {

    private static final String TAG = "FORUM_FRAGMENT";
    private EditText questionET;
    private Button submitButton;
    private Firebase firebase;
    private Firebase questionFB;
    private Firebase questionsFB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        questionET = (EditText) view.findViewById(R.id.forum_ED_id);
        submitButton = (Button) view.findViewById(R.id.forum_submit_id);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        initFirebase();
        setSubmitListener();
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
        questionsFB = firebase.child(question);
        questionFB = questionsFB.child("Question");
        questionFB.setValue(question);
    }

    private void makeQuestionAdapter() {
        FirebaseRecyclerAdapter<Question, QuestionViewHolder> questionAdapter = new FirebaseRecyclerAdapter<Question, QuestionViewHolder>(Question.class,R.layout.question_item,QuestionViewHolder.class,firebase) {
            @Override
            protected void populateViewHolder(QuestionViewHolder questionViewHolder, Question question, int i) {

            }
        };
    }
}