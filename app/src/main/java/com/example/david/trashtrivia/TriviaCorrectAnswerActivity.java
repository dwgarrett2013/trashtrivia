package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TriviaCorrectAnswerActivity extends Activity implements View.OnClickListener{

    private Button buttonCompleteQuiz;
    private Button buttonReturnToHome;
    private Button buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_correct_answer);

        buttonCompleteQuiz=findViewById(R.id.button_correct_answer_complete_quiz);
        buttonCompleteQuiz.setOnClickListener(this);

        buttonReturnToHome=findViewById(R.id.button_correct_answer_homepage);
        buttonReturnToHome.setOnClickListener(this);

        buttonReturnToLogin=findViewById(R.id.button_correct_answer_logout);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");


    }

    @Override
    public void onClick(View v) {
        if(v==buttonCompleteQuiz){
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaResults);
        }
        else if(v==buttonReturnToHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
        else if(v==buttonReturnToLogin){
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentLogin);
        }
    }
}
