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

/*
This activity will display the user's password if they correctly answer their security
question (correct security question answer determined from previous page)
 */

public class ForgotPasswordResultActivity extends Activity implements View.OnClickListener{

    //Initialize a butotn to return to login
    Button buttonReturnToLogin;

    //Initialize a TextView to hold the password
    TextView passwordResult;

    //Initialize a String field to hold the user id
    String userid;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_result);

        //Set userid to object obtained from extra passed by previous page
        userid = getIntent().getStringExtra("userid");

        //link objects to elements on view
        passwordResult = findViewById(R.id.password_text);
        buttonReturnToLogin = findViewById(R.id.button_return_to_login);

        //set OnClickListeners
        buttonReturnToLogin.setOnClickListener(this);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Retrieve the password from the database and display it
        database.child("User").orderByChild("id").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userPassword = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userPassword = postSnapshot.child("password").getValue().toString();
                }
                passwordResult.setText(userPassword);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //handle clicks
    @Override
    public void onClick(View v) {

        //if return to login pressed, return the user to login
        if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }
    }
}
