package com.up.up_opportunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.up.up_opportunity.model.forum.Answer;
import com.up.up_opportunity.view_holders.AnswerViewHolder;

public class AnswerActivity extends AppCompatActivity {

    private static final String TAG = "ANSWER ACTIVITY";
    private EditText answerET;
    private Button submitButton;
    private RecyclerView answerRV;
    private Firebase questionFB;
    private FirebaseRecyclerAdapter<Answer,AnswerViewHolder> answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        initViews();
        initFirebase();

        setSubmitListener();
        setAnswerRV();
    }

    private void initViews(){
        answerET = (EditText)findViewById(R.id.answer_ED_id);
        submitButton = (Button)findViewById(R.id.answer_submit_id);
        answerRV = (RecyclerView)findViewById(R.id.answer_RV);
    }

    private String getStringIntent(){
        Intent recieveIntent = getIntent();
        return recieveIntent.getStringExtra("ANSWER_KEY");
    }

    private void initFirebase(){
        Firebase firebase = new Firebase("https://up-app.firebaseio.com/");
        questionFB = firebase.child(getStringIntent());
    }
    private void setSubmitListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFirebase(answerET.getText().toString());
                answerET.getText().clear();
            }
        });
    }

    private void saveToFirebase(String answer) {
        questionFB.child("Answers").push().setValue(answer);

    }

    private void makeQuestionAdapter() {
        answerAdapter = new FirebaseRecyclerAdapter<Answer, AnswerViewHolder>(Answer.class, R.layout.answer_item,AnswerViewHolder.class,questionFB.child("Answers")) {
            @Override
            protected void populateViewHolder(AnswerViewHolder answerViewHolder, Answer answer, int i) {
                Log.i(TAG, "populateViewHolder: "+answer.getAnswer());
                answerViewHolder.answerTV.setText(answer.getAnswer());
            }
        };
    }

    private void setAnswerRV(){
        makeQuestionAdapter();
        answerRV.setAdapter(answerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        answerRV.setLayoutManager(layoutManager);
    }


}
