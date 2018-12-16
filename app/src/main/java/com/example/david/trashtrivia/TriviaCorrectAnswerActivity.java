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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaCorrectAnswerActivity extends Activity implements View.OnClickListener{

    private Button buttonCompleteQuizOrNextQuestion;
    private Button buttonReturnToHome;
    private Button buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    private TextView textViewCurrentScore, textViewNumRemaining;

    private int numQuestionRemaining;
    private int currentScore;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_correct_answer);

        buttonCompleteQuizOrNextQuestion =findViewById(R.id.button_correct_answer_complete_quiz_or_next_question);
        buttonCompleteQuizOrNextQuestion.setOnClickListener(this);

        buttonReturnToHome=findViewById(R.id.button_correct_answer_homepage);
        buttonReturnToHome.setOnClickListener(this);

        buttonReturnToLogin=findViewById(R.id.button_correct_answer_logout);
        buttonReturnToLogin.setOnClickListener(this);

        textViewCurrentScore=findViewById(R.id.editTextCurrentScore);
        textViewNumRemaining=findViewById(R.id.editTextQuestionRemaining);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        currentScore=getIntent().getIntExtra("currentScore",0);
        System.out.println(getIntent().getIntExtra("currentScore",5));
        System.out.println(currentScore);
        numQuestionRemaining=getIntent().getIntExtra("numQuestionRemaining",0);

        textViewCurrentScore.setText(String.valueOf(currentScore));
        textViewNumRemaining.setText(String.valueOf(numQuestionRemaining));

        if(numQuestionRemaining==0){
            buttonCompleteQuizOrNextQuestion.setText("Complete Quiz");
        }
        else{
            buttonCompleteQuizOrNextQuestion.setText("Next Question");
        }




    }

    @Override
    public void onClick(View v) {
        if(v== buttonCompleteQuizOrNextQuestion){

            if(numQuestionRemaining==0){
                Intent intentTriviaResults=new Intent(getApplicationContext(),TriviaResultsActivity.class);
                intentTriviaResults.putExtra("username", loggedInUsername);
                intentTriviaResults.putExtra("role_name", loggedInUserRoleName);
                intentTriviaResults.putExtra("currentScore",currentScore);
                intentTriviaResults.putExtra("numQuestionRemaining",numQuestionRemaining);
                startActivity(intentTriviaResults);
            }
            else {
                Intent intentTriviaNextQuestion=new Intent(getApplicationContext(),TriviaQuestionPromptActivity.class);
                intentTriviaNextQuestion.putExtra("username", loggedInUsername);
                intentTriviaNextQuestion.putExtra("role_name", loggedInUserRoleName);
                intentTriviaNextQuestion.putExtra("currentScore",currentScore);
                intentTriviaNextQuestion.putExtra("numQuestionRemaining",numQuestionRemaining);
                ArrayList<String> questionBankIdList=getIntent().getStringArrayListExtra("questionBankIdList");
                if(questionBankIdList==null){
                    System.out.println("issue with idlist");
                    System.out.println("blank id list");
                }
                else{
                    System.out.println("Question Bank Id List" +questionBankIdList.size());
                }
                intentTriviaNextQuestion.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                startActivity(intentTriviaNextQuestion);
            }
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
