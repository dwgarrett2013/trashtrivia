/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

public class NotificationsActivity extends Activity implements View.OnClickListener {

    private Button buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        final TableLayout notificationsTable=findViewById(R.id.notification_table_layout);

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
                            notificationText.setText(postSnapshot.child("notificationText").getValue().toString());
                            String senderId=postSnapshot.child("senderId").getValue().toString();
                            final String recipientId=postSnapshot.child("recipientId").getValue().toString();

                            database.child("User").orderByChild("id").equalTo(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final TextView senderUsername=new TextView(getApplicationContext());
                                    senderUsername.setGravity(Gravity.START);
                                    senderUsername.setMaxWidth(10);
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
                                            //
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    //
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(), "Failed to read", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }
        else if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentReturnToLogin);
        }

    }
}
