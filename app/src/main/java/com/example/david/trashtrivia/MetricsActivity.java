package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MetricsActivity extends Activity implements View.OnClickListener {

    private Button buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        final TableLayout questionStatsTable=findViewById(R.id.question_stats_table_layout);

        TextView questionBodyHeader=new TextView(getApplicationContext());
        questionBodyHeader.setText("Question");
        questionBodyHeader.setGravity(Gravity.LEFT);
        TextView questionNumTimesAsked=new TextView(getApplicationContext());
        questionNumTimesAsked.setText("NumTimesAsked");
        questionNumTimesAsked.setGravity(Gravity.LEFT);
        TableRow questionRowHeaders=new TableRow(getApplicationContext());
        questionRowHeaders.addView(questionBodyHeader);
        questionRowHeaders.addView(questionNumTimesAsked);
        questionStatsTable.addView(questionRowHeaders);

        database.child("Question").orderByChild("id").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Question theQuestion=dataSnapshot.getValue(Question.class);
                TextView questionBodyTmp=new TextView(getApplicationContext());
                TextView questionNumTimesAskedTmp=new TextView(getApplicationContext());
                TableRow questionRowTmp=new TableRow(getApplicationContext());
                questionNumTimesAskedTmp.setText(String.valueOf(theQuestion.getNum_times_asked()));
                questionBodyTmp.setText(theQuestion.getQuestion_instructions());
                questionRowTmp.addView(questionBodyTmp);
                questionRowTmp.addView(questionNumTimesAskedTmp);
                questionStatsTable.addView(questionRowTmp);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
