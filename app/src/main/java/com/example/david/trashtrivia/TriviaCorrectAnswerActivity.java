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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/*
This activity will present the user with a page that indicate the user answered a trivia question correct.

It will also provide the option to see the next question or complete the quiz, depending on how many question remain.

The user can also logout or return to the home page, but they will not get credit for their answers in their profile.
Note that the question stats are recorded after each question is answered, so they will be stored anyway.

Please also note that the back button is "disabled" to prevent issues
 */

public class TriviaCorrectAnswerActivity extends Activity implements View.OnClickListener{

    //Initialize button
    private Button buttonCompleteQuizOrNextQuestion;
    private Button buttonReturnToHome;
    private Button buttonReturnToLogin;

    //Initialize values to store the value from intents
    private String loggedInUsername;
    private String loggedInUserRoleName;
    private int numQuestionRemaining;
    private int currentScore;

    //initialize textview objects
    private TextView textViewCurrentScore, textViewNumRemaining;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Disable the back button to prevent issues
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_correct_answer);

        //get intents from previous page
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        currentScore=getIntent().getIntExtra("currentScore",0);
        numQuestionRemaining=getIntent().getIntExtra("numQuestionRemaining",0);

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //Link objects to elements on view
        buttonCompleteQuizOrNextQuestion =findViewById(R.id.button_correct_answer_complete_quiz_or_next_question);
        buttonReturnToHome=findViewById(R.id.button_correct_answer_homepage);
        buttonReturnToLogin=findViewById(R.id.button_correct_answer_logout);
        textViewCurrentScore=findViewById(R.id.editTextCurrentScore);
        textViewNumRemaining=findViewById(R.id.editTextQuestionRemaining);

        //set onclick listeners
        buttonCompleteQuizOrNextQuestion.setOnClickListener(this);
        buttonReturnToHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //set the value of current score and num remainig
        textViewCurrentScore.setText(String.valueOf(currentScore));
        textViewNumRemaining.setText(String.valueOf(numQuestionRemaining));

        //set the value of complete quiz or next quesiton button according to the provided values
        if(numQuestionRemaining==0){
            buttonCompleteQuizOrNextQuestion.setText("Complete Quiz");
        }
        else{
            buttonCompleteQuizOrNextQuestion.setText("Next Question");
        }
    }

    @Override
    public void onClick(View v) {
        //if the button complete or next question is pressed, take the appropriate action according
        //to the number of question left
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
                intentTriviaNextQuestion.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                startActivity(intentTriviaNextQuestion);
            }
        }
        //if the return home button is pressed, take the user back to the homepage
        else if(v==buttonReturnToHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //if the logout button is pressed, return the user to the login screen
        else if(v==buttonReturnToLogin){
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentLogin);
        }
    }
}
