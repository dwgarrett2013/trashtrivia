package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuestionHomeActivity extends Activity implements View.OnClickListener {

    private Button buttonAddQuestion, buttonModifyQuestion, buttonDeleteQuestion, buttonReturnHome, buttonReturnLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home);

        buttonAddQuestion=findViewById(R.id.button_invite_friend);
        buttonModifyQuestion=findViewById(R.id.button_modify_question);
        buttonDeleteQuestion=findViewById(R.id.button_delete_question);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnLogin=findViewById(R.id.button_return_to_login);

        buttonAddQuestion.setOnClickListener(this);
        buttonModifyQuestion.setOnClickListener(this);
        buttonDeleteQuestion.setOnClickListener(this);
        buttonReturnHome.setOnClickListener(this);
        buttonReturnLogin.setOnClickListener(this);

        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
    }

    @Override
    public void onClick(View v) {
        if(v==buttonAddQuestion){
            Intent intentAddQuestion = new Intent(this, AddQuestionActivity.class);
            intentAddQuestion.putExtra("username", loggedInUsername);
            intentAddQuestion.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentAddQuestion);
        }
        else if(v==buttonModifyQuestion){
            Intent intentModifyQuestion = new Intent(this, ModifyQuestion.class);
            startActivity(intentModifyQuestion);
        }
        else if(v==buttonDeleteQuestion){
            Intent intentDeleteQuestion = new Intent(this, DeleteQuestion.class);
            startActivity(intentDeleteQuestion);
        }
        else if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
        else if(v==buttonReturnLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }

    }
}
