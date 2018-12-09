package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TriviaCorrectAnswerActivity extends Activity implements View.OnClickListener{

    private Button buttonCompleteQuiz;
    private Button buttonReturnToHome;
    private Button buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    private TextView textViewCurrentScore, textViewNumRemaining;

    private int numQuestionRemaining;
    private int currentScore;

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

        textViewCurrentScore=findViewById(R.id.editTextCurrentScore);
        textViewNumRemaining=findViewById(R.id.editTextQuestionRemaining);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        currentScore=getIntent().getIntExtra("currentScore",0);
        numQuestionRemaining=getIntent().getIntExtra("numQuestionRemaining",0);

        textViewCurrentScore.setText(String.valueOf(currentScore));
        textViewNumRemaining.setText(String.valueOf(numQuestionRemaining));




    }

    @Override
    public void onClick(View v) {
        if(v==buttonCompleteQuiz){
            Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
            intentTriviaResults.putExtra("username", loggedInUsername);
            intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
            intentTriviaResults.putExtra("currentScore",currentScore);
            intentTriviaResults.putExtra("numQuestionRemaining",numQuestionRemaining);
            startActivity(intentTriviaResults);
        }
        else if(v==buttonReturnToHome){
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
