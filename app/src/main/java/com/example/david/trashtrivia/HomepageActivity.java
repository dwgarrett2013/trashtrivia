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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomepageActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonPlayTrashTrivia, buttonNotifications, buttonViewProfile, buttonMetrics,
            buttonQuestionMgmt, buttonLogout;

    private TableRow tableRowPlayTrashTrivia, tableRowNotifications, tableRowProfile, tableRowMetrics,
            tableRowQuestionMgmt, tableRowLogout;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled from home page.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Link homepage Button Objects to elements in the view
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

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        database = FirebaseDatabase.getInstance().getReference();

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

    @Override
    public void onClick(View v) {

        //if Add Inventory button is clicked
        if(v==buttonPlayTrashTrivia){
            Intent intentPlayTrashTrivia=new Intent(getApplicationContext(),TriviaInitiateActivity.class);
            intentPlayTrashTrivia.putExtra("username", loggedInUsername);
            intentPlayTrashTrivia.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentPlayTrashTrivia);
        }

        //if Check Inventory button is clicked
        else if(v==buttonNotifications){
            Intent intentNotifications=new Intent(getApplicationContext(),NotificationsActivity.class);
            intentNotifications.putExtra("username", loggedInUsername);
            intentNotifications.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentNotifications);
        }

        //if Check Inventory button is clicked
        else if(v==buttonViewProfile){
            Intent intentViewProfile=new Intent(getApplicationContext(),ProfileViewActivity.class);
            intentViewProfile.putExtra("username", loggedInUsername);
            intentViewProfile.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentViewProfile);
        }

        //if Check Inventory button is clicked
        else if(v==buttonMetrics){
            Intent intentMetrics=new Intent(getApplicationContext(),MetricsActivity.class);
            intentMetrics.putExtra("username", loggedInUsername);
            intentMetrics.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentMetrics);
        }

        //if Check Inventory button is clicked
        else if(v==buttonQuestionMgmt){
            Intent intentQuestionHome=new Intent(getApplicationContext(),QuestionHomeActivity.class);
            intentQuestionHome.putExtra("username", loggedInUsername);
            intentQuestionHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentQuestionHome);
        }

        //if Logout button is clicked
        else if(v==buttonLogout){
            Toast.makeText(getApplicationContext(), "Logout Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentLogin);
        }

    }
}
