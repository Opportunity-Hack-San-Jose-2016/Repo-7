package com.up.up_opportunity.model.forum;

import java.util.ArrayList;

/**
 * Created by adao1 on 7/9/2016.
 */
public class Question {
    private String question;
    private int numAnswers;
    private int timestamp;
    private ArrayList<String> answers;

    public Question() {
    }

    public Question(String question, int timestamp) {
        this.question = question;
        this.timestamp = timestamp;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNumAnswers() {
        return answers.size();
    }


    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
