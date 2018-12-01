package com.example.david.trashtrivia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question {
    private String id;
    private String question_text;
    private String question_answer_correct;
    private ArrayList<String> question_answer_options;
    private String question_additional_information;
    private int num_times_asked;
    private int num_times_answered_correctly;
    private ArrayList<String> tags;

    public Question(String id,String question_text, String question_answer_correct, String question_answer_options,
                    String question_additional_information,String tags){
        this.id=id;
        this.question_text=question_text;
        this.question_answer_correct=question_answer_correct;
        this.question_additional_information=question_additional_information;
        this.question_answer_options=new ArrayList<String>();
        this.tags=new ArrayList<String>();
        String[] answerOptionArr=question_answer_options.split(",");
        for(int i=0;i<answerOptionArr.length; i++){
            this.question_answer_options.add(answerOptionArr[i]);
        }

        String[] tagArr=tags.split(",");
        for(int i=0;i<tagArr.length; i++){
            this.tags.add(tagArr[i]);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_answer_correct() {
        return question_answer_correct;
    }

    public void setQuestion_answer_correct(String question_answer_correct) {
        this.question_answer_correct = question_answer_correct;
    }

    public ArrayList getQuestion_answer_options() {
        return question_answer_options;
    }

    public void setQuestion_answer_options(ArrayList question_answer_options) {
        this.question_answer_options = question_answer_options;
    }

    public String getQuestion_additional_information() {
        return question_additional_information;
    }

    public void setQuestion_additional_information(String question_additional_information) {
        this.question_additional_information = question_additional_information;
    }

    public int getNum_times_asked() {
        return num_times_asked;
    }

    public void setNum_times_asked(int num_times_asked) {
        this.num_times_asked = num_times_asked;
    }

    public int getNum_times_answered_correctly() {
        return num_times_answered_correctly;
    }

    public void setNum_times_answered_correctly(int num_times_answered_correctly) {
        this.num_times_answered_correctly = num_times_answered_correctly;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
