package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TriviaResults extends Activity implements View.OnClickListener {

    private Button buttonReplay, buttonLeaderboard, buttonTrashcentral, buttonLogout;
    private TextView textFinalscoretext, textFinalscore, textCongrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_results);

        buttonReplay = findViewById(R.id.buttonReplay);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);
        buttonTrashcentral = findViewById(R.id.buttonTrashcentral);
        buttonLogout = findViewById(R.id.buttonLogout);

        textFinalscoretext = findViewById(R.id.textFinalscoretext);
        textFinalscore = findViewById(R.id.textFinalscore);
        textCongrats = findViewById(R.id.textCongrats);

        buttonReplay.setOnClickListener(this);
        buttonLeaderboard.setOnClickListener(this);
        buttonTrashcentral.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        //Set Finalscore = session_score
    }

    @Override
    public void onClick(View v) {

        if (v==buttonReplay){
            Toast.makeText(getApplicationContext(), "Button Replay clicked", Toast.LENGTH_SHORT).show();

        }else if(v==buttonLeaderboard){
            Toast.makeText(getApplicationContext(), "Button Leaderboard clicked", Toast.LENGTH_SHORT).show();


        }else if(v==buttonTrashcentral){
            Toast.makeText(getApplicationContext(), "Button Trash Central clicked", Toast.LENGTH_SHORT).show();


        }else if(v==buttonLogout){
            Toast.makeText(getApplicationContext(), "Button Log out clicked", Toast.LENGTH_SHORT).show();

        }
    }
}
