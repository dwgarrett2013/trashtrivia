package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuestionHomeActivity extends Activity implements View.OnClickListener {

    private Button buttonAddQuestion, buttonModifyQuestion, buttonDeleteQuestion, buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_home);

        buttonAddQuestion=findViewById(R.id.button_add_question);
        //buttonModifyQuestion.findViewById(R.id.button_modify_question);
        //buttonDeleteQuestion.findViewById(R.id.button_delete_question);
        //buttonReturnHome=findViewById(R.id.button_return_home);
        //buttonReturnToLogin.findViewById(R.id.button_return_to_login);

        buttonAddQuestion.setOnClickListener(this);
        //buttonModifyQuestion.setOnClickListener(this);
        //buttonDeleteQuestion.setOnClickListener(this);
        //buttonReturnHome.setOnClickListener(this);
        //buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
    }

    @Override
    public void onClick(View v) {
        if(v==buttonAddQuestion){
            Toast.makeText(getApplicationContext(), "Button add clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
