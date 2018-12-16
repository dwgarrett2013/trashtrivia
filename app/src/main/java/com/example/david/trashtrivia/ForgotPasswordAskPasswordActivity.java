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

public class ForgotPasswordAskPasswordActivity extends Activity implements View.OnClickListener {

    private Button buttonNext, buttonReturnToLogin;

    private EditText editTextEnterUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_ask_password);

        buttonNext=findViewById(R.id.button_submit_security_question_answer);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);
        editTextEnterUserName=findViewById(R.id.edit_text_forgot_username_answer);

        buttonNext.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }
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
