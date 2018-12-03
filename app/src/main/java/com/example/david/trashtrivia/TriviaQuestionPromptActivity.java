package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TriviaQuestionPromptActivity extends Activity implements View.OnClickListener{

    private Button button1,button2,button3,button4;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question_prompt);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
    }

    @Override
    public void onClick(View v) {
        if(v==button1){
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaResults);
        }
        else if (v==button2) {
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaResults);
        }
        else if (v==button3) {
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaResults);
        }
        else if (v==button4) {
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaResults);
        }

    }
}
