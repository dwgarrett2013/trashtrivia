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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordResultActivity extends Activity implements View.OnClickListener{

    Button buttonReturnToLogin;

    TextView passwordResult;

    String userid;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_result);

        passwordResult = findViewById(R.id.password_text);
        buttonReturnToLogin = findViewById(R.id.button_return_to_login);

        buttonReturnToLogin.setOnClickListener(this);

        userid = getIntent().getStringExtra("userid");
        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

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

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }

    }
}
