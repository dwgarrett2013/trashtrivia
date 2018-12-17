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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/*
This activity allows the user to start a quiz.
Please note that the back button is disabled to prevent issues
 */

public class TriviaInitiateActivity extends Activity implements View.OnClickListener{

    //initialize buttons
    private Button buttonStartQuiz;
    private Button buttonReturnHome;
    private Button buttonReturnToLogin;

    //Initialize values to store the value from intents
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //"disable" back button to prevent issues.
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_initiate);

        //get intents from previous page
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //Link objects to elements on view
        buttonStartQuiz=findViewById(R.id.button_start_trivia);
        buttonReturnToLogin=findViewById(R.id.button_trivia_initiate_logout);
        buttonReturnHome=findViewById(R.id.button_trivia_initiate_return_home);

        //add onclick listeners
        buttonReturnToLogin.setOnClickListener(this);
        buttonStartQuiz.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
    }

    //handle clicks
    @Override
    public void onClick(View v) {

        //if start quiz is pressed, start the quiz
        if(v==buttonStartQuiz){
            Intent intentQuestionPrompt=new Intent(getApplicationContext(),TriviaQuestionPromptActivity.class);
            intentQuestionPrompt.putExtra("username", loggedInUsername);
            intentQuestionPrompt.putExtra("role_name", loggedInUserRoleName);
            intentQuestionPrompt.putExtra("numQuestionRemaining", 5);
            intentQuestionPrompt.putExtra("currentScore", 0);

            startActivity(intentQuestionPrompt);
        }
        //if return home is pressed, return home
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //if logout is pressed. sign the user out and return them hoome
        else if(v==buttonReturnToLogin){
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentLogin);
        }

    }
}
