package com.example.david.trashtrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class TriviaQuizPage extends Activity implements View.OnClickListener {

    private EditText editTextCurrentScore, editTextQuestionRemaining, editTextQuestions;
    private CheckBox checkBoxA, checkBoxB, checkBoxC, checkBoxD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_quiz_page);

        //Link EditText Objects to elements in the view
        editTextCurrentScore = findViewById(R.id.editTextCurrentScore);
        editTextQuestionRemaining = findViewById(R.id.editTextQuestionRemaining);
        editTextQuestions = findViewById(R.id.editTextQuestions);

        //Link CheckBox Objects to elements in the view
        checkBoxA = findViewById(R.id.checkBoxA);
        checkBoxB = findViewById(R.id.checkBoxB);
        checkBoxC = findViewById(R.id.checkBoxC);
        checkBoxD = findViewById(R.id.checkBoxD);

    }

    @Override
    public void onClick(View v) {

    }
}
