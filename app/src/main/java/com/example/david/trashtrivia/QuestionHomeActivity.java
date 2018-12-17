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

import com.google.firebase.auth.FirebaseAuth;

/*
This activity allows valid users (as identified by the previous page) to manage the questions in the
database
 */

public class QuestionHomeActivity extends Activity implements View.OnClickListener {

    //initialize buttons
    private Button buttonAddQuestion, buttonModifyQuestion, buttonDeleteQuestion, buttonReturnHome, buttonReturnLogin;

    //Initialize values to store the username and role of the currently logged in user
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home);

        //get intents from previous page
        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //Link objects to elements on view
        buttonAddQuestion=findViewById(R.id.button_invite_friend);
        buttonModifyQuestion=findViewById(R.id.button_modify_question);
        buttonDeleteQuestion=findViewById(R.id.button_delete_question);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnLogin=findViewById(R.id.button_return_to_login);

        //set onclick listeners
        buttonAddQuestion.setOnClickListener(this);
        buttonModifyQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //if the add question button pressed, send the user to the add question page
        if(v==buttonAddQuestion){
            Intent intentAddQuestion = new Intent(this, AddQuestionActivity.class);
            intentAddQuestion.putExtra("username", loggedInUsername);
            intentAddQuestion.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentAddQuestion);
        }

        //if the modify question button pressed, send the user to the modify question page
        else if(v==buttonModifyQuestion){
            Intent intentModifyQuestion = new Intent(this, ModifyQuestionActivity.class);
            startActivity(intentModifyQuestion);
        }

        //if the delete question button pressed, send the user to the delete question page
        else if(v==buttonDeleteQuestion){
            Intent intentDeleteQuestion = new Intent(this, DeleteQuestionActivity.class);
            startActivity(intentDeleteQuestion);
        }

        //if the return home button pressed, send the user to the homepage
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //if the logout button pressed, log the user out and send them to the login screen
        else if(v==buttonReturnLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }

    }
}
