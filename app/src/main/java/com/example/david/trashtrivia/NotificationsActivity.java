package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class NotificationsActivity extends Activity {


    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        final TableLayout notificationsTable=findViewById(R.id.notification_table_layout);

        TableRow a=new TableRow(this);
        a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView txt=new TextView(getApplicationContext());
        txt.setText("bleh");
        a.addView(txt);
        //notificationsTable.addView(a);

        database.child("Notification").orderByChild("ts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println("bleh");
                    //System.out.println(postSnapshot.getValue().toString());

                    final TextView timeSent=new TextView(getApplicationContext());
                    timeSent.setText(postSnapshot.child("id").getValue().toString());

                    String senderId=postSnapshot.child("senderId").getValue().toString();
                    final String recipientId=postSnapshot.child("recipientId").getValue().toString();

                    database.child("User").orderByChild("id").equalTo(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final TextView senderUsername=new TextView(getApplicationContext());
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
                                    row.addView(timeSent);
                                    row.addView(senderUsername);
                                    row.addView(recipientUsername);
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
                //System.out.println(notificationsTableLayout.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Failed to read", Toast.LENGTH_SHORT).show();
            }
        });


        init();
    }

    public void init(){
        final TableLayout notificationsTableLayout=findViewById(R.id.notification_table_layout);

        database.child("Notification").orderByChild("ts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println("bleh");
                    //System.out.println(postSnapshot.getValue().toString());
                    TableRow row=new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp);
                    for(DataSnapshot child : postSnapshot.getChildren()) {
                        TextView tempView=new TextView(getApplicationContext());
                        tempView.setText(child.getValue().toString());
                        //System.out.println(tempView.getText().toString());
                        row.addView(tempView,new TableLayout.LayoutParams(1,TableLayout.LayoutParams.WRAP_CONTENT,1.0f));
                    }
                    notificationsTableLayout.addView(row);
                }
                System.out.println(notificationsTableLayout.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Failed to read", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
