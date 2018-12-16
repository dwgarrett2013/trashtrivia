/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee
 */

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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TriviaQuestionPromptActivity extends Activity implements View.OnClickListener{

    private Button button1,button2,button3,button4;

    private TableLayout choicesTable;

    private String loggedInUsername;
    private String loggedInUserRoleName;
    private TextView questionText;
    private String correctAnswer;
    private int numQuestionRemaining;
    private int currentScore;

    private TextView currentTextScoreVal;
    private TextView currentTextNumQuestionReamaining;

    private String selectedQuestionKey;

    Intent intentTriviaCorrectAnswer;
    Intent intentTriviaIncorrectAnswer;

    ArrayList<String> questionBankIdList;


    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question_prompt);

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        choicesTable=findViewById(R.id.profile_content_table);

        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        currentScore=getIntent().getIntExtra("currentScore",0);
        numQuestionRemaining=getIntent().getIntExtra("numQuestionRemaining",5);

        currentTextScoreVal=findViewById(R.id.currentScoreVal);
        currentTextScoreVal.setText(String.valueOf(currentScore));
        currentTextNumQuestionReamaining=findViewById(R.id.numRemainingVal);
        currentTextNumQuestionReamaining.setText(String.valueOf(numQuestionRemaining));

        if(getIntent().getStringArrayListExtra("questionBankIdList")!=null){
            questionBankIdList=getIntent().getStringArrayListExtra("questionBankIdList");
        }
        else{
            questionBankIdList=new ArrayList<>();
        }

        System.out.println("Questionbank id list: "+questionBankIdList);

        final ArrayList<Question> questionList=new ArrayList<Question>();
        final ArrayList<String> possibleAnswers=new ArrayList<String>();

        questionText=findViewById(R.id.editTextQuestions);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        correctAnswer="";

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        database.child("Question").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        questionList.add(child.getValue(Question.class));
                    }

                    if(questionBankIdList.size()==0){
                        for(Question element: questionList){
                            questionBankIdList.add(element.getId());
                        }
                    }
                    else{

                        ArrayList<String> idsToDelete=new ArrayList<>();

                        for(int i=0; i<questionList.size(); i++){
                            String searchId=questionList.get(i).getId();
                            int found=0;
                            for(int c=0; c<questionBankIdList.size(); c++){
                                System.out.println(questionBankIdList.get(c));
                                if(questionBankIdList.get(c).equals(searchId)){
                                    found=1;
                                }
                            }
                            if(found==0){
                                idsToDelete.add(questionList.get(i).getId());
                            }
                        }

                        for(int i=0; i<idsToDelete.size(); i++){
                            for(int c=0; c<questionList.size(); c++){
                                if(questionList.get(c).getId().equals(idsToDelete.get(i))){
                                    questionList.remove(c);
                                }
                            }
                        }

                    }

                    /*
                    for(int i=0; i<questionBankIdList.size(); i++){
                        System.out.println(questionBankIdList.get(i));
                    }
                    */

                    System.out.println("QuestionBankList size: "+questionBankIdList.size());
                    System.out.println("questionlist size: "+questionList.size());


                    int elementToUse=new Random().nextInt(questionBankIdList.size());
                    String idOfQuestionToRemove=questionList.get(elementToUse).getId();
                    for(int i=0; i<questionBankIdList.size(); i++){
                        if(idOfQuestionToRemove.equals(questionBankIdList.get(i))){
                            questionBankIdList.remove(i);
                            break;
                        }
                    }



                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_correct_answer());
                    correctAnswer=questionList.get(elementToUse).getQuestion_correct_answer();
                    selectedQuestionKey=questionList.get(elementToUse).getId();
                    questionText.setText(questionList.get(elementToUse).getQuestion_instructions());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer1());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer2());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer3());
                    Collections.shuffle(possibleAnswers);

                    button1.setText(possibleAnswers.get(0));
                    button2.setText(possibleAnswers.get(1));
                    button3.setText(possibleAnswers.get(2));
                    button4.setText(possibleAnswers.get(3));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    @Override
    public void onClick(View v) {

        if(v==button1){
            if(button1.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                if(questionBankIdList!=null){
                    intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                }

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(questionKey);
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                startActivity(intentTriviaCorrectAnswer);
            }
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                for(int i=0; i<questionBankIdList.size(); i++){
                    System.out.println(questionBankIdList.get(i));
                }
                if(questionBankIdList!=null){
                    intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                }

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaIncorrectAnswer);
            }
        }
        else if (v==button2) {
            if(button2.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentSco0re", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaCorrectAnswer);
            }
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaIncorrectAnswer);
            }

        }
        else if (v==button3) {
            if(button3.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaCorrectAnswer);
            }
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaIncorrectAnswer);
            }
        }
        else if (v==button4) {
            if(button4.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaCorrectAnswer);
            }
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener()  {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        System.out.println(theQuestion.getId());
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(intentTriviaIncorrectAnswer);
            }
        }

    }
}
