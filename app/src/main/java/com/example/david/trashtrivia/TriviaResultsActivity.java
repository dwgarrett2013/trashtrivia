package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TriviaResultsActivity extends Activity implements View.OnClickListener {

    private Button buttonReplay, buttonLeaderboard, buttonReturnHome, buttonReturnToLogin;
    private TextView textFinalscoretext, textFinalscore, textCongrats;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_results);

        buttonReplay = findViewById(R.id.button_trivia_results_replay);
        buttonLeaderboard = findViewById(R.id.button_trivia_results_profile_leaderboard);
        buttonReturnHome = findViewById(R.id.button_trivia_results_return_home);
        buttonReturnToLogin = findViewById(R.id.button__trivia_results_return_to_login);

        textFinalscoretext = findViewById(R.id.textFinalscoretext);
        textFinalscore = findViewById(R.id.textFinalscore);
        textCongrats = findViewById(R.id.textCongrats);

        buttonReplay.setOnClickListener(this);
        buttonLeaderboard.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        if(loggedInUserRoleName.equals("admin")||loggedInUserRoleName.equals("premium")||loggedInUserRoleName.equals("standard")){
            buttonLeaderboard.setVisibility(View.VISIBLE);
        }



        //Set Finalscore = session_score
    }

    @Override
    public void onClick(View v) {

        if (v==buttonReplay){
            Intent intentTriviaInitiate=new Intent(getApplicationContext(),TriviaInitiateActivity.class);
            intentTriviaInitiate.putExtra("username", loggedInUsername);
            intentTriviaInitiate.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaInitiate);

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
