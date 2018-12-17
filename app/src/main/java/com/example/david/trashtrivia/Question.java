/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

/*
This class represents a Question object in the database
 */

public class Question {
    private String id;  //id in the database
    private String question_instructions;   //Question instructions text
    private String question_correct_answer; //text of the correct answer
    private String question_wrong_answer1;  //text of incorrect answer1
    private String question_wrong_answer2;  //text of incorrect answer2
    private String question_wrong_answer3;  //text of incorrect answer3
    private String question_additional_information; ////text additional information
    private int num_times_asked;    //number of times the question has been asked
    private int num_times_answered_correctly;   //number of times the question has been answered correctly

    public Question(String id, String question_instructions, String question_correct_answer,
                    String question_wrong_answer1, String question_wrong_answer2,
                    String question_wrong_answer3, String question_additional_information) {
        this.id = id;
        this.question_instructions = question_instructions;
        this.question_correct_answer = question_correct_answer;
        this.question_wrong_answer1 = question_wrong_answer1;
        this.question_wrong_answer2 = question_wrong_answer2;
        this.question_wrong_answer3 = question_wrong_answer3;
        this.question_additional_information = question_additional_information;
        this.num_times_answered_correctly=0;
        this.num_times_asked=0;
    }

    public Question(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_instructions() {
        return question_instructions;
    }

    public void setQuestion_instructions(String question_instructions) {
        this.question_instructions = question_instructions;
    }

    public String getQuestion_correct_answer() {
        return question_correct_answer;
    }

    public void setQuestion_correct_answer(String question_correct_answer) {
        this.question_correct_answer = question_correct_answer;
    }

    public String getQuestion_wrong_answer1() {
        return question_wrong_answer1;
    }

    public void setQuestion_wrong_answer1(String question_wrong_answer1) {
        this.question_wrong_answer1 = question_wrong_answer1;
    }

    public String getQuestion_wrong_answer2() {
        return question_wrong_answer2;
    }

    public void setQuestion_wrong_answer2(String question_wrong_answer2) {
        this.question_wrong_answer2 = question_wrong_answer2;
    }

    public String getQuestion_wrong_answer3() {
        return question_wrong_answer3;
    }

    public void setQuestion_wrong_answer3(String question_wrong_answer3) {
        this.question_wrong_answer3 = question_wrong_answer3;
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
}
