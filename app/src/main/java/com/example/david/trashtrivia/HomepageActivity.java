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
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
This Activity presents the home page once a user has logged in or has returned to it from another
page in the application.  It is only accessible to logged in users or guest users.  It will display
navigation options according to a user's role.  The back button is also disabled on the page.
 */

public class HomepageActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonPlayTrashTrivia, buttonNotifications, buttonViewProfile, buttonMetrics,
            buttonQuestionMgmt, buttonLogout;

    //Initialize TableRow objects to store information about each row object
    private TableRow tableRowPlayTrashTrivia, tableRowNotifications, tableRowProfile, tableRowMetrics,
            tableRowQuestionMgmt, tableRowLogout;

    //Initialize values to store the username and role of the currently logged in user
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    //"Disable" the back button on this page to prevent unintended navigation.
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled from home page.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //get intent objects
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mauth object
        mAuth=FirebaseAuth.getInstance();

        //create database object
        database = FirebaseDatabase.getInstance().getReference();

        //Link homepage Objects to elements in the view
        buttonPlayTrashTrivia=findViewById(R.id.button_play_trash_trivia);
        buttonNotifications =findViewById(R.id.button_delete_question);
        buttonViewProfile=findViewById(R.id.buttonViewProfile);
        buttonMetrics=findViewById(R.id.buttonMetrics);
        buttonQuestionMgmt =findViewById(R.id.button_return_home);
        buttonLogout=findViewById(R.id.button_return_to_login);
        tableRowPlayTrashTrivia =findViewById(R.id.table_row_notifications);
        tableRowNotifications =findViewById(R.id.table_row_delete_question);
        tableRowProfile=findViewById(R.id.table_row_profile);
        tableRowMetrics=findViewById(R.id.table_row_metrics);
        tableRowQuestionMgmt=findViewById(R.id.table_row_return_home);
        tableRowLogout=findViewById(R.id.table_row_return_to_login);

        //Add OnClickListeners to homepage Button objects
        buttonPlayTrashTrivia.setOnClickListener(this);
        buttonNotifications.setOnClickListener(this);
        buttonViewProfile.setOnClickListener(this);
        buttonMetrics.setOnClickListener(this);
        buttonQuestionMgmt.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        //display buttons according to the user role
        if(loggedInUserRoleName.equals("standard")||loggedInUserRoleName.equals("admin")||loggedInUserRoleName.equals("premium")){
            tableRowProfile.setVisibility(View.VISIBLE);
            tableRowNotifications.setVisibility(View.VISIBLE);
        }
        if(loggedInUserRoleName.equals("premium")||loggedInUserRoleName.equals("admin")){
            tableRowMetrics.setVisibility(View.VISIBLE);
        }
        if(loggedInUserRoleName.equals("admin")){
            tableRowQuestionMgmt.setVisibility(View.VISIBLE);
        }
    }

    //handle clicks
    @Override
    public void onClick(View v) {

        //if play trash trivia button clicked take the user to a quiz
        if(v==buttonPlayTrashTrivia){
            Intent intentPlayTrashTrivia=new Intent(getApplicationContext(),TriviaInitiateActivity.class);
            intentPlayTrashTrivia.putExtra("username", loggedInUsername);
            intentPlayTrashTrivia.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentPlayTrashTrivia);
        }

        //if notifications button clicked take the user to a the notifications page
        else if(v==buttonNotifications){
            Intent intentNotifications=new Intent(getApplicationContext(),NotificationsActivity.class);
            intentNotifications.putExtra("username", loggedInUsername);
            intentNotifications.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentNotifications);
        }

        //if profile button clicked take the user to the profile page
        else if(v==buttonViewProfile){
            Intent intentViewProfile=new Intent(getApplicationContext(),ProfileViewActivity.class);
            intentViewProfile.putExtra("username", loggedInUsername);
            intentViewProfile.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentViewProfile);
        }

        //if metrics button clicked take the user to the metrics page
        else if(v==buttonMetrics){
            Intent intentMetrics=new Intent(getApplicationContext(),MetricsActivity.class);
            intentMetrics.putExtra("username", loggedInUsername);
            intentMetrics.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentMetrics);
        }

        //if Question management button clicked take the user to the question management page
        else if(v==buttonQuestionMgmt){
            Intent intentQuestionHome=new Intent(getApplicationContext(),QuestionHomeActivity.class);
            intentQuestionHome.putExtra("username", loggedInUsername);
            intentQuestionHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentQuestionHome);
        }

        //if Logout button is clicked, log the user out and sign them out
        else if(v==buttonLogout){
            Toast.makeText(getApplicationContext(), "Logout Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentLogin);
        }
    }
}
