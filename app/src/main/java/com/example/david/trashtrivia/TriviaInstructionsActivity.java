package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TriviaInstructionsActivity extends Activity implements View.OnClickListener{

    private Button buttonStartTrivia, buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartTrivia=findViewById(R.id.button_start_trivia);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        loggedInUsername =getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");


    }

    @Override
    public void onClick(View view) {
        if (view == buttonStartTrivia) {
            Toast.makeText(this, "Begin Quiz", Toast.LENGTH_SHORT).show();
        } else if (view == buttonReturnHome) {
            Toast.makeText(this, "Return", Toast.LENGTH_SHORT).show();
        } else if (view == buttonReturnToLogin) {
            Toast.makeText(this, "Sign-Out/ Sign-In", Toast.LENGTH_SHORT).show();
        }
    }
}
