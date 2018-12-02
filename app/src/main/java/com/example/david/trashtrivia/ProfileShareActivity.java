package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class ProfileShareActivity extends Activity implements View.OnClickListener{

    private Button buttonShareProfile, buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_profile);

        buttonShareProfile=findViewById(R.id.button_share_profile_with_friend);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        buttonShareProfile.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
    }

    @Override
    public void onClick(View v) {
        if(v==buttonShareProfile){
            //final String key = database.child("Notification").push().getKey();

            //final Notification notificationDbObject=new Notification(key, loggedInUsername,editTextPassword.getText().toString());
            //userDbObject.setSecurityQuestionAnswer(editTextSecurityQuestionAnswer.getText().toString());


            Toast.makeText(getApplicationContext(), "Friend Invited Button Clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
        else if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }

    }
}
