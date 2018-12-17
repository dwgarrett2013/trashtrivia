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
This activity allows the user to share their profile with another valid user
 */

public class ProfileShareActivity extends Activity implements View.OnClickListener{

    //initialize buttons
    private Button buttonShareProfile, buttonReturnHome, buttonReturnToLogin;

    //initialize text input field to indicate username of user to share profile
    private TextView text_edit_input_friend;

    //Initialize values to store the username and role of the currently logged in user
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //String to store the key of the friend to share with
    private String friendKey;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_share);

        //get intents from previous page
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Link objects to elements in view
        buttonShareProfile=findViewById(R.id.button_share_profile_with_friend);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);
        text_edit_input_friend=findViewById(R.id.text_edit_input_friend);

        //Set onclicklisteners
        buttonShareProfile.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);
    }

    //handle clicks
    @Override
    public void onClick(View v) {

        //if share profile pressed, make sure the provided user name is the name of the user to send
        //then share the profile by creating a notification in teh database with the targeted user's id
        //as the recipient
        if(v==buttonShareProfile){
            String friendUsername=text_edit_input_friend.getText().toString();
            database.child("User").orderByChild("username").equalTo(friendUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() == 1) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            friendKey = postSnapshot.getKey();
                        }
                        database.child("User").orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() == 1) {
                                    String currentUserKey = "";
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        currentUserKey = postSnapshot.getKey();
                                    }
                                    String notificationPushKey = database.child("Notification").push().getKey();
                                    Notification newNotification = new Notification(notificationPushKey, currentUserKey, friendKey, "Quiz Challenge Received");
                                    database.child("Notification").child(notificationPushKey).setValue(newNotification);
                                    Toast.makeText(getApplicationContext(), "Notification Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                        });
                    } else if (dataSnapshot.getChildrenCount() == 0) {
                        Toast.makeText(getApplicationContext(), "The provided username does not yet exist in the application", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //if return home pressed, return to homepage
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //if return to login pressed, return to login
        else if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }

    }
}
