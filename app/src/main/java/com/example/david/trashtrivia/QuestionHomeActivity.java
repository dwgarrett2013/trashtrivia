package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuestionHomeActivity extends Activity implements View.OnClickListener {

    private Button buttonAddQuestion, buttonModifyQuestion, buttonDeleteQuestion, buttonReturnHome;
    private Button buttonReturnLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home);

        buttonAddQuestion=findViewById(R.id.button_add_question);
        buttonModifyQuestion=findViewById(R.id.button_modify_question);
        buttonDeleteQuestion=findViewById(R.id.button_delete_question);
        buttonReturnHome=findViewById(R.id.button_question_home);
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
            Toast.makeText(getApplicationContext(), "Button add clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v==buttonModifyQuestion){
            Toast.makeText(getApplicationContext(), "Button add clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v==buttonDeleteQuestion){
            Toast.makeText(getApplicationContext(), "Button add clicked", Toast.LENGTH_SHORT).show();
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
