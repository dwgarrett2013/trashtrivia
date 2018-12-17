/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
This activity prompps the user to enter their username and forwards them along to the
AnswerSecurityQuestionActivity where they will answer the question to their security question
 */

public class ForgotPasswordAskPasswordActivity extends Activity implements View.OnClickListener {

    //Initialize buttons to move the user to the next screen or return to login
    private Button buttonNext, buttonReturnToLogin;

    //Initialize an EditText field for the user to insert their username
    private EditText editTextEnterUserName;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_ask_password);

        //Link initialized objects to field elements
        buttonNext=findViewById(R.id.button_submit_security_question_answer);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);
        editTextEnterUserName=findViewById(R.id.edit_text_forgot_username_answer);

        //set appropriate onclicklisteners
        buttonNext.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);
    }

    //handle clicks
    @Override
    public void onClick(View v) {
        //if return to login is pressed, return the user to login
        if(v == buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }
        //if return to button next is pressed, make sure they have entered a username and then
        //forward the user to the AnswerSecurityQuestionActivity page
        else if(v == buttonNext){
            if(editTextEnterUserName.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intentAnswerSecurityQuestion=new Intent(getApplicationContext(),AnswerSecurityQuestionActivity.class);
                intentAnswerSecurityQuestion.putExtra("requestedUsername",editTextEnterUserName.getText().toString());
                startActivity(intentAnswerSecurityQuestion);
            }
        }
    }
}
