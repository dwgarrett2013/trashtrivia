/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

/*
This class represents a security question type in the database
 */

public class SecurityQuestion {
    private String id;  //security question id
    private String security_question_text;  //security question text

    public SecurityQuestion(String id, String security_question_text) {
        this.id = id;
        this.security_question_text = security_question_text;
    }

    public String getSecurity_question_text() {
        return security_question_text;
    }

    public void setSecurity_question_text(String security_question_text) {
        this.security_question_text = security_question_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
