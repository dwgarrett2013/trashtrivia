/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TriviaResultsActivity extends Activity implements View.OnClickListener {

    private Button buttonReplay, buttonLeaderboard, buttonReturnHome, buttonReturnToLogin;
    private TextView textFinalscoretext, textFinalscore, textCongrats;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    private int currentScore;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_results);

        database = FirebaseDatabase.getInstance().getReference();

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

        currentScore=getIntent().getIntExtra("currentScore",0);

        textFinalscore.setText(String.valueOf(currentScore));

        if(loggedInUserRoleName.equals("admin")||loggedInUserRoleName.equals("premium")||loggedInUserRoleName.equals("standard")){
            buttonLeaderboard.setVisibility(View.VISIBLE);
        }

        if(loggedInUserRoleName.equals("guest")){

        }
        else{
            database.child("User").orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userKey;
                    User theUser=new User();
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        userKey=child.getKey();
                        theUser=child.getValue(User.class);
                    }
                    theUser.setNumCorrectAnswer(theUser.getNumCorrectAnswer()+currentScore);
                    theUser.setNumQuizzesTaken(theUser.getNumQuizzesTaken()+1);
                    theUser.setNumQuestionCompleted(theUser.getNumQuestionCompleted()+1);
                    database.child("User").child(theUser.getId()).setValue(theUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
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
