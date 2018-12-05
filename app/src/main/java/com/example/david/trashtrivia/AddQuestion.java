package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddQuestion extends Activity implements View.OnClickListener {

    private Button buttonCreate, buttonReturnHome;
    private EditText editTextCreateQuestion, editTextCreateCorrectAnswer,editTextCreateAnswer1,editTextCreateAnswer2, editTextCreateAnswer3, editTextCreateAdditionalInformation;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        buttonCreate = findViewById(R.id.buttonCreate);
        buttonReturnHome = findViewById(R.id.button_return_home_add_question);
        editTextCreateQuestion = findViewById(R.id.editTextCreateQuestion);
        //editTextCreateCorrectAnswer = findViewById(R.id.editTextCreateCorrectAnswer);
        //editTextCreateAnswer1 = findViewById(R.id.editTextCreateAnswer1);
        //editTextCreateAnswer2 = findViewById(R.id.editTextCreateAnswer2);
        //editTextCreateAnswer3 = findViewById(R.id.editTextCreateAnswer3);
        //editTextCreateAdditionalInformation = findViewById(R.id.editTextCreateAdditionalInformation);



        buttonCreate.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);

        loggedInUsername = getIntent().getStringExtra("username");
        loggedInUserRoleName = getIntent().getStringExtra("role_name");

    }

    @Override
    public void onClick(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Question");

        if (v == buttonCreate) {
            String createQuestion = editTextCreateQuestion.getText().toString();
            //String createCorrectAnswer = editTextCreateAnswer.getText().toString();
            //String createAnswer1 = editTextCreateAnswer.getText().toString();
            //String createAnswer2 = editTextCreateAnswer.getText().toString();
            //String createAnswer3 = editTextCreateAnswer.getText().toString();
            //String createAdditionalInformation = editTextCreateAnswer.getText().toString();




            //Question newQuestion = new Question(createQuestion, createCorrectAnswer);

            //myRef.push().setValue(newQuestion);

        } else if (v == buttonReturnHome) {
            Intent intentReturnHome = new Intent(this, HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
    }
}
