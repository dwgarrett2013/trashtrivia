/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
This activity presents the valid logged in user (as determined by navigation options
with stats about each of the questions in the database.
 */

public class MetricsActivity extends Activity implements View.OnClickListener {

    //Initialize buttons to return home and to login
    private Button buttonReturnHome, buttonReturnToLogin;

    //Initialize values to store the username and role of the currently logged in user
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize FireBaseAuth object
    private FirebaseAuth mAuth;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create mauth object
        mAuth=FirebaseAuth.getInstance();

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Link objects to elements in view
        buttonReturnHome=findViewById(R.id.button_return_home);
        buttonReturnToLogin=findViewById(R.id.button_return_to_login);

        //set on click listeners
        buttonReturnHome.setOnClickListener(this);
        buttonReturnToLogin.setOnClickListener(this);

        //Create a table layout to store stats about questions
        final TableLayout questionStatsTable=findViewById(R.id.question_stats_table_layout);

        //Create table headers and add them to the table
        SpannableString questionString = new SpannableString("Question");
        questionString.setSpan(new StyleSpan(Typeface.BOLD), 0, questionString.length(), 0);
        TextView questionBodyHeader=new TextView(getApplicationContext());
        questionBodyHeader.setTextColor(Color.BLACK);
        questionBodyHeader.setText(questionString);
        questionBodyHeader.setGravity(Gravity.LEFT);
        SpannableString numAskedString = new SpannableString("# Times Asked");
        numAskedString.setSpan(new StyleSpan(Typeface.BOLD), 0, numAskedString.length(), 0);
        TextView questionNumTimesAsked=new TextView(getApplicationContext());
        questionNumTimesAsked.setText(numAskedString);
        questionNumTimesAsked.setTextColor(Color.BLACK);
        questionNumTimesAsked.setGravity(Gravity.LEFT);
        TableRow questionRowHeaders=new TableRow(getApplicationContext());
        questionRowHeaders.addView(questionBodyHeader);
        questionRowHeaders.addView(questionNumTimesAsked);
        questionStatsTable.addView(questionRowHeaders);

        //dynamically generate a list of the questions and their stats and add them to the view
        database.child("Question").orderByChild("id").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                float scale = getResources().getDisplayMetrics().density;

                int questionBodyWidthTextPixels = (int) (200*scale + 0.5f);
                int questionNumTimesAskedWidthTextPixels = (int) (50*scale + 0.5f);

                TableRow questionRowTmp=new TableRow(getApplicationContext());

                TextView questionBodyTmp=new TextView(getApplicationContext());
                Question theQuestion=dataSnapshot.getValue(Question.class);
                questionBodyTmp.setText(theQuestion.getQuestion_instructions());
                questionBodyTmp.setWidth(questionBodyWidthTextPixels);
                questionBodyTmp.setTextColor(Color.BLACK);

                TextView questionNumTimesAskedTmp=new TextView(getApplicationContext());
                questionNumTimesAskedTmp.setText(String.valueOf(theQuestion.getNum_times_asked()));
                questionNumTimesAskedTmp.setWidth(questionNumTimesAskedWidthTextPixels);
                questionNumTimesAskedTmp.setTextColor(Color.BLACK);

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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Handle clicks
    @Override
    public void onClick(View v) {
        //If return home is pressed, take the user back to the homepage
        if(v==buttonReturnHome){
            Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
            intentReturnHome.putExtra("username", loggedInUsername);
            intentReturnHome.putExtra("role_name", loggedInUserRoleName);
            startActivity(intentReturnHome);
        }

        //if the button return to login is pressed, return the user to the login screen
        else if(v==buttonReturnToLogin){
            Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
            mAuth.signOut();
            startActivity(intentReturnToLogin);
        }
    }
}
