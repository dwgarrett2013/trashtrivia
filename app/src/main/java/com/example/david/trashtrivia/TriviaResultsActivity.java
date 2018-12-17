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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
This activity allows the user to see the results of the quiz they just completed.

Note that the back button is disabled
 */

public class TriviaResultsActivity extends Activity implements View.OnClickListener {

    //initialize buttons
    private Button buttonReplay, buttonLeaderboard, buttonReturnHome, buttonReturnToLogin;

    //initialize textviews to show quiz informatoin
    private TextView textFinalscoretext, textFinalscore, textCongrats;

    //Initialize values to store the value from intents
    private String loggedInUsername;
    private String loggedInUserRoleName;
    private int currentScore;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

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

        //get intents from previous page
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        currentScore=getIntent().getIntExtra("currentScore",0);

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //create db object
        database = FirebaseDatabase.getInstance().getReference();

        //Link objects to elements on view
        buttonReplay = findViewById(R.id.button_trivia_results_replay);
        buttonLeaderboard = findViewById(R.id.button_trivia_results_profile_leaderboard);
        buttonReturnHome = findViewById(R.id.button_trivia_results_return_home);
        buttonReturnToLogin = findViewById(R.id.button__trivia_results_return_to_login);
        textFinalscoretext = findViewById(R.id.textFinalscoretext);
        textFinalscore = findViewById(R.id.textFinalscore);
        textCongrats = findViewById(R.id.textCongrats);

        //add onclick listeners
        buttonReplay.setOnClickListener(this);
        buttonLeaderboard.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //set the final score text according to what has been passed
        textFinalscore.setText(String.valueOf(currentScore));

        //make buttons visible only to the viewers who can see them
        if(loggedInUserRoleName.equals("admin")||loggedInUserRoleName.equals("premium")||loggedInUserRoleName.equals("standard")){
            buttonLeaderboard.setVisibility(View.VISIBLE);
        }

        //update the profile values for the user if they are not a guest
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
                    theUser.setNumQuestionCompleted(theUser.getNumQuestionCompleted()+5);
                    database.child("User").child(theUser.getId()).setValue(theUser);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        //if the replay button is pressed have the user play another quiz
        if (v==buttonReplay){
            Intent intentTriviaInitiate=new Intent(getApplicationContext(),TriviaInitiateActivity.class);
            intentTriviaInitiate.putExtra("username", loggedInUsername);
            intentTriviaInitiate.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentTriviaInitiate);

        }

        //if the leaderboard button is pressed, have the user view their profile page
        else if(v==buttonLeaderboard){
            Intent intentProfileViewActivity=new Intent(getApplicationContext(),ProfileViewActivity.class);
            intentProfileViewActivity.putExtra("username", loggedInUsername);
            intentProfileViewActivity.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentProfileViewActivity);


        }

        //if the return home button is pressed, have the user return to the homepage
        else if(v== buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);


        }

        //if the button return to login is pressed, have the user signout and return to the login
        else if(v== buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }
    }
}
