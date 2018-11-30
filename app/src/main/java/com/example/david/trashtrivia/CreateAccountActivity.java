package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends Activity implements View.OnClickListener{

    //Initialize Button Objects
    private Button buttonCreateAccount, buttonReturnToSignin;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

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

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public void onClick(View v) {
        //if Create Account button is clicked
        if(v == buttonReturnToSignin) {
            Intent intentReturnToSignin = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToSignin);
        }
        else if(v == buttonCreateAccount) {

            User userDbObject=new User("Sasha","thepass","1","1","Bleh Bleh");

            String key = database.child("User").push().getKey();
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
