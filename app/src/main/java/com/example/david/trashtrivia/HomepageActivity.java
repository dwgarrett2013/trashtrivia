package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

public class HomepageActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonPlayTrashTrivia, buttonNotifications, buttonViewProfile, buttonMetrics,
            buttonQuestionMgmt, buttonLogout;

    private TableRow tableRowPlayTrashTrivia, tableRowNotifications, tableRowProfile, tableRowMetrics,
            tableRowQuestionMgmt, tableRowLogout;

    private String loggedInUserId;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Link homepage Button Objects to elements in the view
        buttonPlayTrashTrivia=findViewById(R.id.buttonPlayTrashTrivia);
        buttonNotifications =findViewById(R.id.buttonNotifications);
        buttonViewProfile=findViewById(R.id.buttonViewProfile);
        buttonMetrics=findViewById(R.id.buttonMetrics);
        buttonQuestionMgmt =findViewById(R.id.buttonQuestionMgmt);
        buttonLogout=findViewById(R.id.buttonLogout);

        tableRowPlayTrashTrivia =findViewById(R.id.table_row_play_trashtrivia);
        tableRowNotifications =findViewById(R.id.table_row_notifications);
        tableRowProfile=findViewById(R.id.table_row_profile);
        tableRowMetrics=findViewById(R.id.table_row_metrics);
        tableRowQuestionMgmt=findViewById(R.id.table_row_question_mgmt);
        tableRowLogout=findViewById(R.id.table_row_logout);

        //Add OnClickListeners to homepage Button objects
        buttonPlayTrashTrivia.setOnClickListener(this);
        buttonNotifications.setOnClickListener(this);
        buttonViewProfile.setOnClickListener(this);
        buttonMetrics.setOnClickListener(this);
        buttonQuestionMgmt.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        loggedInUserId=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        System.out.println(loggedInUserId);
        System.out.println(loggedInUserRoleName);

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
            Toast.makeText(HomepageActivity.this, "button clicked Successful", Toast.LENGTH_SHORT).show();
        }

        //if Check Inventory button is clicked
        else if(v==buttonViewProfile){
            Toast.makeText(HomepageActivity.this, "button clicked Successful", Toast.LENGTH_SHORT).show();
        }

        //if Logout button is clicked
        else if(v==buttonLogout){
            Toast.makeText(getApplicationContext(), "Logout Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intentLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentLogin);
        }

    }
}
