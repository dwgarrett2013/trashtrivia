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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewActivity extends Activity implements View.OnClickListener{

    private Button buttonReturnHome, buttonReturnToLogin, buttonShareProfile;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        final LinearLayout fullLinear=findViewById(R.id.full_linear);

        float scale = getResources().getDisplayMetrics().density;

        final int usernameDpAsPixels = (int) (150*scale + 0.5f);
        final int scoreDpAsPixels = (int) (112*scale + 0.5f);
        final int quizzesTakenDpAsPixels = (int) (60*scale + 0.5f);

        final TextView numCorrect=findViewById(R.id.textView_Correct);
        final TextView numAsked=findViewById(R.id.textView_Completed);
        final TextView numTaken=findViewById(R.id.textView_taken);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();
        //create mautho object
        mAuth=FirebaseAuth.getInstance();

        final TableLayout profileContentTable=findViewById(R.id.profile_content_table);

        database.child("User").orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String userId;
                 int numCorrectAnswer=0;
                 int numQuestionsCompleted=0;
                 int numQuizzesTaken=0;
                 for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                     userId=postSnapshot.child("id").getValue().toString();
                     numCorrectAnswer=Integer.parseInt(postSnapshot.child("numCorrectAnswer").getValue().toString());
                     numQuestionsCompleted=Integer.parseInt(postSnapshot.child("numQuestionCompleted").getValue().toString());
                     numQuizzesTaken=Integer.parseInt(postSnapshot.child("numQuizzesTaken").getValue().toString());
                 }
                 TableRow tableRowNumCorrectAnswer=new TableRow(getApplicationContext());
                 tableRowNumCorrectAnswer.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                 numCorrect.setText((String.valueOf(numCorrectAnswer)));
                 numAsked.setText(String.valueOf(numQuestionsCompleted));
                 numTaken.setText(String.valueOf(numQuizzesTaken));

                 final TableLayout userScoreTable=new TableLayout(getApplicationContext());
                 userScoreTable.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                 database.child("User").addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {



                             TextView textViewUsername = new TextView(getApplicationContext());
                             textViewUsername.setWidth(usernameDpAsPixels);
                             textViewUsername.setText(postSnapshot.child("username").getValue().toString());


                             TextView textViewNumCorrectAnswer = new TextView(getApplicationContext());
                             textViewNumCorrectAnswer.setWidth(scoreDpAsPixels);
                             textViewNumCorrectAnswer.setText(postSnapshot.child("numCorrectAnswer").getValue().toString());

                             TextView textViewNumQuizzesTaken = new TextView(getApplicationContext());
                             textViewNumQuizzesTaken.setWidth(quizzesTakenDpAsPixels);
                             textViewNumQuizzesTaken.setText(postSnapshot.child("numQuizzesTaken").getValue().toString());

                             TableRow tableRowUserScoreTable = new TableRow(getApplicationContext());
                             tableRowUserScoreTable.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                             tableRowUserScoreTable.addView(textViewUsername);
                             tableRowUserScoreTable.addView(textViewNumCorrectAnswer);
                             tableRowUserScoreTable.addView(textViewNumQuizzesTaken);

                             userScoreTable.addView(tableRowUserScoreTable);
                         }
                         profileContentTable.removeView(userScoreTable);
                         profileContentTable.addView(userScoreTable);

                         TableRow tableRowShareProfileButton=new TableRow(getApplicationContext());
                         tableRowShareProfileButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                         buttonShareProfile=new Button(getApplicationContext());
                         buttonShareProfile.setText("Share Profile");
                         buttonShareProfile.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Intent intentShareProfile=new Intent(getApplicationContext(),ProfileShareActivity.class);
                                 intentShareProfile.putExtra("username", loggedInUsername);
                                 intentShareProfile.putExtra("role_name", loggedInUserRoleName);
                                 startActivity(intentShareProfile);
                             }
                         });
                         tableRowShareProfileButton.addView(buttonShareProfile);

                         TableRow tableRowReturnHomeButton=new TableRow(getApplicationContext());
                         tableRowReturnHomeButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                         buttonReturnHome=new Button(getApplicationContext());
                         buttonReturnHome.setText("Return to Homepage");
                         buttonReturnHome.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Intent intentReturnHome=new Intent(getApplicationContext(),HomepageActivity.class);
                                 intentReturnHome.putExtra("username", loggedInUsername);
                                 intentReturnHome.putExtra("role_name", loggedInUserRoleName);
                                 startActivity(intentReturnHome);
                             }
                         });
                         tableRowReturnHomeButton.addView(buttonReturnHome);

                         TableRow tableRowReturnToLoginButton=new TableRow(getApplicationContext());
                         tableRowReturnToLoginButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                         buttonReturnToLogin=new Button(getApplicationContext());
                         buttonReturnToLogin.setText("Return to Login");
                         buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Intent intentReturnToLogin=new Intent(getApplicationContext(),MainActivity.class);
                                 startActivity(intentReturnToLogin);
                             }
                         });
                         tableRowReturnToLoginButton.addView(buttonReturnToLogin);

                         fullLinear.addView(tableRowShareProfileButton);
                         fullLinear.addView(tableRowReturnHomeButton);
                         fullLinear.addView(tableRowReturnToLoginButton);

                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {
                         //
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

    }
}
