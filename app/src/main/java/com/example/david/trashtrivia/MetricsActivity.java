package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MetricsActivity extends Activity {

    private Button buttonReturnHome, buttonReturnToLogin;

    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        TableLayout questionStatsTable=findViewById(R.id.question_stats_table_layout);
        TableLayout tagStatsTable=findViewById(R.id.tag_stats_table_layout);

        TextView questionBodyHeader=new TextView(getApplicationContext());
        questionBodyHeader.setText("Question Text");
        TextView questionNumCorrectHeader=new TextView(getApplicationContext());
        questionNumCorrectHeader.setText("Number times Answered Correctly");
        TextView questionNumTimesAsked=new TextView(getApplicationContext());
        questionNumTimesAsked.setText("NumTimesAsked");
        TableRow questionRowHeaders=new TableRow(getApplicationContext());
        questionRowHeaders.addView(questionBodyHeader);
        questionRowHeaders.addView(questionNumCorrectHeader);
        questionRowHeaders.addView(questionNumTimesAsked);
        questionStatsTable.addView(questionRowHeaders);

        TextView questionBodyTmp=new TextView(getApplicationContext());
        TextView questionNumCorrectTmp=new TextView(getApplicationContext());
        TextView questionNumTimesAskedTmp=new TextView(getApplicationContext());
        TableRow questionRowTmp=new TableRow(getApplicationContext());
        //autogenerate questions stats
        for(int i=0; i<3; i++) {
            questionBodyTmp.setText("body");
            questionNumCorrectTmp.setText(i);
            questionNumTimesAskedTmp.setText(i);
            questionRowTmp.addView(questionBodyTmp);
            questionRowTmp.addView(questionNumCorrectTmp);
            questionRowTmp.addView(questionNumTimesAskedTmp);
            questionStatsTable.addView(questionRowTmp);
        }

        TextView tagBodyHeader=new TextView(getApplicationContext());
        tagBodyHeader.setText("Tag Text");
        TextView tagNumCorrectHeader=new TextView(getApplicationContext());
        tagNumCorrectHeader.setText("Number times Answered Correctly");
        TextView tagNumTimesAsked=new TextView(getApplicationContext());
        tagNumTimesAsked.setText("NumTimesAsked");
        TableRow tagRowHeaders=new TableRow(getApplicationContext());
        tagRowHeaders.addView(tagBodyHeader);
        tagRowHeaders.addView(tagNumCorrectHeader);
        tagRowHeaders.addView(tagNumTimesAsked);
        tagStatsTable.addView(tagRowHeaders);

        TextView tagBodyTmp=new TextView(getApplicationContext());
        TextView tagNumCorrectTmp=new TextView(getApplicationContext());
        TextView tagNumTimesAskedTmp=new TextView(getApplicationContext());
        TableRow tagRowTmp=new TableRow(getApplicationContext());

        //autogenerate tag stats
        for(int i=0; i<3; i++) {

            tagBodyTmp.setText("body");
            tagNumCorrectTmp.setText(i);
            tagNumTimesAskedTmp.setText(i);
            tagRowTmp.addView(tagBodyTmp);
            tagRowTmp.addView(tagNumCorrectTmp);
            tagRowTmp.addView(tagNumTimesAskedTmp);
            tagStatsTable.addView(tagRowTmp);
        }
    }
}
