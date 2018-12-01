package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuestionMgmtHomeActivity extends Activity implements View.OnClickListener{

    private Button buttonAddQuestion, buttonModifyQuestion, buttonDeleteQuestion, buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home);

        //buttonAddQuestion.findViewById(R.id.buttonAddQuestion);
        //buttonModifyQuestion.findViewById(R.id.button_modify_question);
        //buttonDeleteQuestion.findViewById(R.id.button_delete_question);
        //buttonReturnHome=findViewById(R.id.button_return_home);
        //buttonReturnToLogin.findViewById(R.id.button_return_to_login);

        //buttonAddQuestion.setOnClickListener(this);
        //buttonModifyQuestion.setOnClickListener(this);
        //buttonDeleteQuestion.setOnClickListener(this);
        //buttonReturnHome.setOnClickListener(this);
        //buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
    }

    @Override
    public void onClick(View v) {
        if(v==buttonReturnHome){
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
