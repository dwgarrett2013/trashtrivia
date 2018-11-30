package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateAccountActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonCreateAccount, buttonReturnToSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Link Button Objects to elements in the view
        buttonCreateAccount = findViewById(R.id.button_create_account);
        buttonReturnToSignin = findViewById(R.id.button_return_to_login);

        //Add OnClickListeners to Button objects
        buttonCreateAccount.setOnClickListener(this);
        buttonReturnToSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //if Create Account button is clicked
        if(v == buttonReturnToSignin) {
            Intent intentReturnToSignin = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToSignin);
        }
        else if(v == buttonCreateAccount) {
            Toast.makeText(this, "Say Something", Toast.LENGTH_SHORT).show();
        }

    }
}
