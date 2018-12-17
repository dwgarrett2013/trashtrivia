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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
This activity will add questions to the database for use in trivia quizzes.
 */

public class AddQuestionActivity extends Activity implements View.OnClickListener {

    //Initialize buttons to create a question, return to homepage, and logout
    private Button buttonCreate, buttonReturnHome, buttonLogout;

    //Initialize text input fields
    private EditText editTextCreateQuestion, editTextCreateCorrectAnswer,editTextCreateAnswer1,editTextCreateAnswer2,
            editTextCreateAnswer3, editTextCreateAdditionalInformation;

    //Initialize variables to hold the value of the currently logged in user and tehir role
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Set Database information
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    //When the pages is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        //get intents from the previous page
        loggedInUsername = getIntent().getStringExtra("username");
        loggedInUserRoleName = getIntent().getStringExtra("role_name");

        //Assign buttons to the appropriate view elements
        buttonCreate = findViewById(R.id.buttonAddQuestion);
        buttonReturnHome = findViewById(R.id.button_return_home);
        buttonLogout=findViewById(R.id.button_return_to_login);
        editTextCreateQuestion = findViewById(R.id.editTextCreateQuestions);
        editTextCreateCorrectAnswer = findViewById(R.id.editTextRightAnswer);
        editTextCreateAnswer1 = findViewById(R.id.editTextWrongAnswer1);
        editTextCreateAnswer2 = findViewById(R.id.editTextWrongAnswer2);
        editTextCreateAnswer3 = findViewById(R.id.editTextWrongAnswer3);
        editTextCreateAdditionalInformation = findViewById(R.id.editTextAdditionalInformation);

        //Set OnClickListeners for buttons
        buttonCreate.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        //create mauth object
        mAuth=FirebaseAuth.getInstance();

        //Set database and database reference
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Question");
    }

    //handle clicks
    @Override
    public void onClick(View v) {

        //If the create question button is pressed, make sure all fields are filled and then create
        //a Question in the database and make a confirmation message.
        if (v == buttonCreate) {
            String createQuestionText = editTextCreateQuestion.getText().toString();
            String createCorrectAnswer = editTextCreateCorrectAnswer.getText().toString();
            String createWrongAnswer1 = editTextCreateAnswer1.getText().toString();
            String createWrongAnswer2 = editTextCreateAnswer2.getText().toString();
            String createWrongAnswer3 = editTextCreateAnswer3.getText().toString();
            String createAdditionalInformation = editTextCreateAdditionalInformation.getText().toString();
            
            if(createQuestionText.isEmpty() || createCorrectAnswer.isEmpty() || createWrongAnswer1.isEmpty()
                    || createWrongAnswer2.isEmpty() || createWrongAnswer3.isEmpty() || createAdditionalInformation.isEmpty() ){
                Toast.makeText(getApplicationContext(), "Please ensure all fields are populated", Toast.LENGTH_SHORT).show();
            }
            else{
                String key=myRef.push().getKey();
                Question newQuestion=new Question(key, createQuestionText,createCorrectAnswer,createWrongAnswer1,
                        createWrongAnswer2,createWrongAnswer3,createAdditionalInformation);
                myRef.push().setValue(newQuestion);
                Toast.makeText(getApplicationContext(), "Question Added", Toast.LENGTH_SHORT).show();
            }
        }

        //If Return to home page button pressed, return home
        else if (v == buttonReturnHome) {
            Intent intentReturnHome = new Intent(this, HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //If Logout button pressed, signout and return to login
        else if(v==buttonLogout){
            Intent intentReturnToLogin = new Intent(this, MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }
    }
}
