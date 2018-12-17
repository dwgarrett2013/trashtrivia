/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
This Activity allows the user to see the notifications that have been sent to them
 */

public class NotificationsActivity extends Activity implements View.OnClickListener {

    //Initialize return home and return to login buttons
    private Button buttonReturnHome, buttonReturnToLogin;

    //Initialize values to store the username and role of the currently logged in user
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize a layouts to add notifications to
    private LinearLayout fullLinear;
    private TableLayout notificationsTable;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //get intents from previous page
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Link objects to elements in view
        fullLinear=findViewById(R.id.full_linear);
        notificationsTable=findViewById(R.id.notification_table_layout);
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        //Set onclicklisteners
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //get the scale and set pixel conversions
        float scale = getResources().getDisplayMetrics().density;
        final int notificationTextAsPixels = (int) (150*scale + 0.5f);
        final int senderAsPixels = (int) (112*scale + 0.5f);

        //get notifications for the logged in user from the database and display them on the page
        database.child("User").orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userId="";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userId=postSnapshot.child("id").getValue().toString();
                }
                database.child("Notification").orderByChild("recipientId").equalTo(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            final TextView notificationText=new TextView(getApplicationContext());
                            notificationText.setWidth(notificationTextAsPixels);
                            notificationText.setText(postSnapshot.child("notificationText").getValue().toString());
                            String senderId=postSnapshot.child("senderId").getValue().toString();
                            final String recipientId=postSnapshot.child("recipientId").getValue().toString();

                            database.child("User").orderByChild("id").equalTo(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final TextView senderUsername=new TextView(getApplicationContext());
                                    senderUsername.setGravity(Gravity.START);
                                    senderUsername.setWidth(senderAsPixels);


                                    //senderUsername.setMaxWidth(10);
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        senderUsername.setText(postSnapshot.child("username").getValue().toString());
                                    }
                                    database.child("User").orderByChild("id").equalTo(recipientId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            TextView recipientUsername=new TextView(getApplicationContext());
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                                recipientUsername.setText(postSnapshot.child("username").getValue().toString());
                                            }
                                            TableRow row=new TableRow(getApplicationContext());
                                            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                                            row.addView(notificationText);
                                            row.addView(senderUsername);

                                            notificationsTable.addView(row);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Handle clicks
    @Override
    public void onClick(View v) {
        //If return home button is pressed, return the homepage
        if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
        //If logout button is pressed, log the user out and return to login
        else if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }

    }
}
