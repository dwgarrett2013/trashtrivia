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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnswerSecurityQuestionActivity extends Activity implements View.OnClickListener {

    private Button buttonSubmitAnswer, buttonReturnToLogin;

    private TextView textViewSecurityQuestion;

    private EditText editTextEnterSecurityAnswer;

    private String requestedUsername;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_security_question);

        buttonSubmitAnswer = findViewById(R.id.button_submit_security_question_answer);
        buttonReturnToLogin = findViewById(R.id.button_return_to_login);

        textViewSecurityQuestion = findViewById(R.id.security_question_text);
        editTextEnterSecurityAnswer = findViewById(R.id.edit_text_forgot_username_answer);

        buttonSubmitAnswer.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        requestedUsername = getIntent().getStringExtra("requestedUsername");

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Find the security question for the user
        database.child("User").orderByChild("username").equalTo(requestedUsername).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userSecurityQuestionId = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userSecurityQuestionId = postSnapshot.child("securityQuestionId").getValue().toString();
                }
                database.child("SecurityQuestion").orderByChild("id").equalTo(userSecurityQuestionId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()==0){
                            Toast.makeText(getApplicationContext(), "Security question does not exist.  Returning Home", Toast.LENGTH_SHORT).show();
                            Intent returnToLogin=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(returnToLogin);
                        }
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            textViewSecurityQuestion.setText(postSnapshot.child("security_question_text").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
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
        else if(v == buttonSubmitAnswer){

            database.child("User").orderByChild("username").equalTo(requestedUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String retrievedSecurityQuestionAnswer = "";
                    String retrievedSecurityAnswerUserId="";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        retrievedSecurityQuestionAnswer = postSnapshot.child("securityQuestionAnswer").getValue().toString();
                        retrievedSecurityAnswerUserId=postSnapshot.getKey();
                    }
                    if (retrievedSecurityQuestionAnswer.equals(editTextEnterSecurityAnswer.getText().toString())) {
                        Intent intentViewAnswer = new Intent(getApplicationContext(), ForgotPasswordResultActivity.class);
                        intentViewAnswer.putExtra("userid",retrievedSecurityAnswerUserId);
                        startActivity(intentViewAnswer);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Answer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
