/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee

Citation Notes:
https://www.recycling.com/wp-content/uploads/2016/06/recycling-symbol-icon-outline-solid-magenta.png
https://www.kissclipart.com/recycling-sign-clipart-recycling-symbol-clip-art-4ki5g3/
 */

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
This activity loads the first the user will arrive at in the application.  It allows the user to login,
create an account, retrive a forgotten password, or login as a guest user (note that there is limited functionality for
guest users.

If invalid credentials are provided, the user will not be allowed to login.

Please note that the back button is disabled to preventu issues
 */

public class MainActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonLogin, buttonForgotPassword, buttonCreateAccount, buttonProceedAsGuest;

    //Initialize EditText Objects
    private EditText editTextEmail, editTextPassword;

    //Initialize FirebaseAuth objects
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    //Disable the back button to avoid issues with unintentional access
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled on login screeen.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link objects to elements in the view
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonForgotPassword=findViewById(R.id.buttonForgotPassword);
        buttonCreateAccount = findViewById(R.id.button_submit_security_question_answer);
        buttonProceedAsGuest=findViewById(R.id.button_return_to_login);
        editTextEmail = findViewById(R.id.edit_text_forgot_username_answer);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Add OnClickListeners to Button objects
        buttonLogin.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
        buttonCreateAccount.setOnClickListener(this);
        buttonProceedAsGuest.setOnClickListener(this);

        //create FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
    }

    //Handles click events
    @Override
    public void onClick(View v) {

        //if Create Account button is clicked
        if(v == buttonCreateAccount) {
            Intent intentCreateAccount = new Intent(getApplicationContext(),CreateAccountActivity.class);
            startActivity(intentCreateAccount);
        }

        //if Forgot password button is clicked
        else if(v == buttonForgotPassword){
            Intent intentForgotPassword = new Intent(getApplicationContext(),ForgotPasswordAskPasswordActivity.class);
            startActivity(intentForgotPassword);
        }

        //if Proceed as guest button is clicked
        else if(v ==buttonProceedAsGuest) {
            Intent intentHomepage=new Intent(getApplicationContext(),HomepageActivity.class);
            intentHomepage.putExtra("role_name", "guest");
            //start Homepage intent
            startActivity(intentHomepage);
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
                                    final FirebaseUser user = mAuth.getCurrentUser();

                                    // Read from the database
                                    database.child("User").orderByChild("username").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String roleId="";
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                roleId=postSnapshot.child("roleId").getValue().toString();
                                            }
                                            database.child("Role").orderByChild("id").equalTo(roleId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String roleName = "";
                                                    for (DataSnapshot postRoleSnapshot : dataSnapshot.getChildren()) {
                                                        roleName = postRoleSnapshot.child("roleName").getValue().toString();
                                                    }
                                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                    Intent intentHomepage = new Intent(getApplicationContext(), HomepageActivity.class);
                                                    intentHomepage.putExtra("username", user.getEmail());
                                                    intentHomepage.putExtra("role_name", roleName);
                                                    //start Homepage intent
                                                    startActivity(intentHomepage);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
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
