package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaResultsActivity extends Activity implements View.OnClickListener {

    private Button buttonReplay, buttonLeaderboard, buttonReturnHome, buttonReturnToLogin;
    private TextView textFinalscoretext, textFinalscore, textCongrats;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_results);

        buttonReplay = findViewById(R.id.buttonReplay);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);
        buttonReturnHome = findViewById(R.id.buttonTrashcentral);
        buttonReturnToLogin = findViewById(R.id.buttonLogout);

        textFinalscoretext = findViewById(R.id.textFinalscoretext);
        textFinalscore = findViewById(R.id.textFinalscore);
        textCongrats = findViewById(R.id.textCongrats);

        buttonReplay.setOnClickListener(this);
        buttonLeaderboard.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");



        //Set Finalscore = session_score
    }

    @Override
    public void onClick(View v) {

        if (v==buttonReplay){
            Intent intentTriviaQuestionPrompt=new Intent(getApplicationContext(),TriviaQuestionPromptActivity.class);
            intentTriviaQuestionPrompt.putExtra("username", loggedInUsername);
            intentTriviaQuestionPrompt.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaQuestionPrompt);

        }else if(v==buttonLeaderboard){
            Intent intentProfileViewActivity=new Intent(getApplicationContext(),ProfileViewActivity.class);
            intentProfileViewActivity.putExtra("username", loggedInUsername);
            intentProfileViewActivity.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentProfileViewActivity);


        }else if(v== buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);


        }else if(v== buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }
    }
}
