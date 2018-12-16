/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonCreateAccount, buttonReturnToLogin;

    //Link EditText Objects to elements in the view
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextSecurityQuestionAnswer;

    private RadioGroup radioGroupAccountType, radioGroupSecurityQuestion;

    private RadioButton radioButtonAccountType, radioButtonSecurityQuestion;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Link Button Objects to elements in the view
        buttonCreateAccount = findViewById(R.id.button_submit_security_question_answer);
        buttonReturnToLogin = findViewById(R.id.button_return_to_login);

        editTextEmail=findViewById(R.id.edit_text_forgot_username_answer);
        editTextPassword=findViewById(R.id.editTextAccountCreatePassword);
        editTextSecurityQuestionAnswer=findViewById(R.id.editTextSecurityQuestionAnswer);

        radioGroupAccountType = findViewById(R.id.radio_group_select_account_type);
        radioGroupSecurityQuestion = findViewById(R.id.radio_group_select_security_question);

        //Add OnClickListeners to Button objects
        buttonCreateAccount.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        //create mautho object
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        //if Create Account button is clicked
        if(v == buttonReturnToLogin) {
            Intent intentReturnToLogin = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }
        else if(v == buttonCreateAccount) {

            if(editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() ||
                    editTextSecurityQuestionAnswer.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please ensure that an email, password, and " +
                        "security question are provided", Toast.LENGTH_SHORT).show();
            }
            else{
                int selectedAccountTypeRadioButtonId=radioGroupAccountType.getCheckedRadioButtonId();
                int selectedSecurityQuestionRadioButtonId=radioGroupSecurityQuestion.getCheckedRadioButtonId();

                radioButtonAccountType=findViewById(selectedAccountTypeRadioButtonId);
                radioButtonSecurityQuestion=findViewById(selectedSecurityQuestionRadioButtonId);
                String radioButtonAccountTypeText=radioButtonAccountType.getText().toString();
                final String radioButtonSecurityQuestionText=radioButtonSecurityQuestion.getText().toString();
                String radioButtonAccountTypeTextClean="";


                if(radioButtonAccountTypeText.equals("Admin User")){
                    radioButtonAccountTypeTextClean="admin";
                }
                else if(radioButtonAccountTypeText.equals("Premium User")){
                    radioButtonAccountTypeTextClean="premium";
                }
                else{
                    radioButtonAccountTypeTextClean="standard";
                }

                final String key = database.child("User").push().getKey();

                final User userDbObject=new User(key,editTextEmail.getText().toString(),editTextPassword.getText().toString());
                userDbObject.setSecurityQuestionAnswer(editTextSecurityQuestionAnswer.getText().toString());

                database.child("Role").orderByChild("roleName").equalTo(radioButtonAccountTypeTextClean).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //Toast.makeText(getApplicationContext(), "crud", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), postSnapshot.child("roleName").getValue().toString(), Toast.LENGTH_SHORT).show();
                            userDbObject.setRoleId(postSnapshot.child("id").getValue().toString());
                        }
                        //after updating userDbObject with role, id do same with securityQuesitonid
                        database.child("SecurityQuestion").orderByChild("security_question_text").equalTo(radioButtonSecurityQuestionText).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    userDbObject.setSecurityQuestionId(postSnapshot.child("id").getValue().toString());
                                    //Toast.makeText(getApplicationContext(), postSnapshot.child("username").getValue().toString()+postSnapshot.child("password").getValue().toString(), Toast.LENGTH_SHORT).show();
                                }
                                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                database.child("User").child(key).setValue(userDbObject).
                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(getApplicationContext(), "Successes for all", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(), "User registration failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "User registration failed with mauth", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Toast.makeText(getApplicationContext(), "Failed to read Security id", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "failed to read role id", Toast.LENGTH_SHORT).show();
                    }
                });

                //Toast.makeText(this, "Say Something", Toast.LENGTH_SHORT).show();

                //note that you can attach onece and it will work

                // Read from the database
                database.child("User").orderByChild("username").equalTo("Sasha").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Toast.makeText(getApplicationContext(), postSnapshot.child("username").getValue().toString()+postSnapshot.child("password").getValue().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(), "Failed to read", Toast.LENGTH_SHORT).show();
                    }
                });
            }



        }

    }
}
