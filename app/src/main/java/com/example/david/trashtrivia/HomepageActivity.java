package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomepageActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonPlayTrashTrivia, buttonViewProfile, buttonLogout;

    private String loggedInUserId;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Link homepage Button Objects to elements in the view
        buttonPlayTrashTrivia=findViewById(R.id.buttonPlayTrashTrivia);
        buttonViewProfile=findViewById(R.id.buttonViewProfile);
        buttonLogout=findViewById(R.id.buttonLogout);

        //Add OnClickListeners to homepage Button objects
        buttonPlayTrashTrivia.setOnClickListener(this);
        buttonViewProfile.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        loggedInUserId=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        System.out.println(loggedInUserId);
        System.out.println(loggedInUserRoleName);
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
