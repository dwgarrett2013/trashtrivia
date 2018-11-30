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

public class MainActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonRegister, buttonLogin;

    //Initialize EditText Objects
    private EditText editTextEmail, editTextPassword;

    //Initialize FirebaseAuth objects
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link Button Objects to elements in the view
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        //Link EditText Objects to elements in the view
        editTextEmail = findViewById(R.id.editTextEMail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Add OnClickListeners to Button objects
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        //create FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
    }

    //Handles click events
    @Override
    public void onClick(View v) {

        //if Register User button is clicked
        if(v == buttonRegister) {

            //If the user has not provided inputs for both username and password, alert them
            if(editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter both a username and password to register or login.", Toast.LENGTH_SHORT).show();
            }

            //if username and password have been provided, attempt to register a user
            else {
                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //If user is successfully registered, post a message on the indicating the user has been registered
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "User Registration successful. "+ user.getEmail()+" can now log in.", Toast.LENGTH_SHORT).show();
                                }

                                //If user is registration fails, post a message on the indicating user registration failed
                                else {
                                    Toast.makeText(MainActivity.this, "User Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

        //if Login button is clicked
        else if(v == buttonLogin) {

            //Check to be sure the user has provided inputs for both username and password before proceeding
            if(editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()){
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
