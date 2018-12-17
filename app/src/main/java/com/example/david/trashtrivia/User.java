/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

/*
This class represent user objects in the database
 */

public class User {
    private String id;  //user id
    private String username;    //username of user
    private String password;    //password of user
    private String roleId;      //role id of the user
    private String securityQuestionId;  //security question type id
    private String securityQuestionAnswer;  //text answer of the sequrity question
    private int numCorrectAnswer;   //number of quesitons answered correctlly
    private int numQuestionCompleted;   //number of questions completed
    private int numQuizzesTaken;       //number of quizzes taken/completed

    public User(){

    }

    public User(String id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
        this.numCorrectAnswer = 0;
        this.numQuestionCompleted = 0;
        this.numQuizzesTaken = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumQuizzesTaken() {
        return numQuizzesTaken;
    }

    public void setNumQuizzesTaken(int numQuizzesTaken) {
        this.numQuizzesTaken = numQuizzesTaken;
    }

    public int getNumQuestionCompleted() {
        return numQuestionCompleted;
    }

    public void setNumQuestionCompleted(int numQuestionCompleted) {
        this.numQuestionCompleted = numQuestionCompleted;
    }

    public int getNumCorrectAnswer() {
        return numCorrectAnswer;
    }

    public void setNumCorrectAnswer(int numCorrectAnswer) {
        this.numCorrectAnswer = numCorrectAnswer;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setSecurityQuestionId(String securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return this.username+this.password;
    }
}
