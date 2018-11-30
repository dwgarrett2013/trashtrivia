package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonLogin, buttonForgotPassword, buttonCreateAccount, buttonProceedAsGuest;

    //Initialize EditText Objects
    private EditText editTextEmail, editTextPassword;

    //Initialize FirebaseAuth objects
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link Button Objects to elements in the view
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonForgotPassword=findViewById(R.id.buttonForgotPassword);
        buttonCreateAccount = findViewById(R.id.button_create_account);
        buttonProceedAsGuest=findViewById(R.id.button_return_to_login);

        //Link EditText Objects to elements in the view
        editTextEmail = findViewById(R.id.editTextEMail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Add OnClickListeners to Button objects
        buttonLogin.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
        buttonCreateAccount.setOnClickListener(this);
        buttonProceedAsGuest.setOnClickListener(this);

        //create FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //create Firebase Database
        database = FirebaseDatabase.getInstance();
    }

    //Handles click events
    @Override
    public void onClick(View v) {

        //if Create Account button is clicked
        if(v == buttonCreateAccount) {
            Intent intentCreateAccount = new Intent(getApplicationContext(),CreateAccountActivity.class);
            startActivity(intentCreateAccount);
        }

        //if Login button is clicked
        else if(v == buttonLogin) {

            //Check to be sure the user has provided inputs for both username and password before proceeding
            if(editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){

                // Write a message to the database
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("boo how");
                Toast.makeText(MainActivity.this, "Please enter both a username and password to register or login.", Toast.LENGTH_SHORT).show();
            }

            //if username and password have been provided, attempt to login
            else {
                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //If user login is successful , post a message on the indicating the user has been registered and navigate them to the project homepage
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intentHomepage = new Intent(getApplicationContext(),HomepageActivity.class);
                                    startActivity(intentHomepage);
                                }

                                //If user login fails , post a message on the indicating the user login failed
                                else {
                                    Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
