package com.up.up_opportunity.model.forum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adao1 on 7/9/2016.
 */
public class Question {
    private String question;
    private int numAnswers;
    private String timestamp;
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(String question, String timestamp) {
        this.question = question;
        this.timestamp = timestamp;
//        answers = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNumAnswers() {
        return answers.size()+"";
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
