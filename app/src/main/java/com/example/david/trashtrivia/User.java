package com.example.david.trashtrivia;

public class User {
    private String id;
    private String username;
    private String password;
    private String roleId;
    private String securityQuestionId;
    private String securityQuestionAnswer;
    private int numCorrectAnswer;
    private int numQuestionCompleted;
    private int numQuizzesTaken;

    public User(String username, String password, String roleId, String securityQuestionId, String securityQuestionAnswer){
        this.username=username;
        this.password=password;
        this.roleId = roleId;
        this.securityQuestionId = securityQuestionId;
        this.securityQuestionAnswer = securityQuestionAnswer;
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
