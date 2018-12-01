package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnswerSecurityQuestionActivity extends Activity {

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

        requestedUsername = getIntent().getStringExtra("requestedUsername");
        System.out.println("This is the username receiveed: " + requestedUsername);

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
}
