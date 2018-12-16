package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TriviaInitiateActivity extends Activity implements View.OnClickListener{

    private Button buttonStartQuiz;
    private Button buttonReturnHome;
    private Button buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_initiate);

        buttonStartQuiz=findViewById(R.id.button_start_trivia);
        buttonStartQuiz.setOnClickListener(this);

        buttonReturnHome=findViewById(R.id.button_trivia_initiate_return_home);
        buttonReturnHome.setOnClickListener(this);

        buttonReturnToLogin=findViewById(R.id.button_trivia_initiate_logout);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

    }

    @Override
    public void onClick(View v) {
        if(v==buttonStartQuiz){
            Intent intentQuestionPrompt=new Intent(getApplicationContext(),TriviaQuestionPromptActivity.class);
            intentQuestionPrompt.putExtra("username", loggedInUsername);
            intentQuestionPrompt.putExtra("role_name", loggedInUserRoleName);
            intentQuestionPrompt.putExtra("numQuestionRemaining", 5);
            intentQuestionPrompt.putExtra("currentScore", 0);

            startActivity(intentQuestionPrompt);
        }
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
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
