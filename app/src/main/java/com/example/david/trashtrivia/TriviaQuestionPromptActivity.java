/*
Team Members: Rang Jin, Babatunji Olotu, David Garrett, Matt Chatiwat Lerdvongveerachai, and Abhi Dua
Group 9-Coffee Coffee Coffee

Question Source: https://www.nps.gov/anch/upload/RecyclingQuizFinal.pdf
 */

package com.example.david.trashtrivia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
The TriviaQuestionPromptActivity activity will display a page under the following scenarios:
1.  After a user initiates a quiz from the TriviaInitiateActivity activity page
2.  After a user views the TriviaIncorrectAnswerActivity or TriviaCorrectAnswerActivity page, and
    presses the next question button assuming there are questions remaining to be asked in the quiz.

The view of this page will display the number of questions remaining in a quiz, the user's current
score on the quiz, question instructions, and a list of 4 potential answers, one of which is
correct.

If this is the first question of the quiz, then the questionBankIdList StringArrayExtra field will
be empty and an arraylist of potential questions will be filled with all available quesitons from
the database.  If this is not the first question of the quiz, this value will be passed from the
page the user had previously visited.  The list of questions and the order of the possible answers
will be shuffled with every question.  Once a user answers a question (correctly or incorrectly),
the currently asked question will be removed from the questionBankIdList so that it is not asked
again.  The questionBankIdList will be passed along over the course of the quiz.  In the event a
new quiz is started, or the user leaves this quiz the questionBankIdList will not be passed along,
and a new one will be created as a result.

If  the user answers correctly:
The user will be forwarded to the TriviaCorrectAnswerActivity view, and the current score and the
number of remaining questions will be updated accordingly.  Statistics relating to the answered
question will also be updated.

If  the user answers correctly:
The user will be forwarded to the TriviaIncorrectAnswerActivity view, and the and the number
of remaining questions will be updated accordingly.  Statistics relating to the answered question
will also be updated.

Lastly, please note that the back button has been disabled to prevent issues with the passing along
of the current score, questionBankIdList, and other variables passed between questions
 */

public class TriviaQuestionPromptActivity extends Activity implements View.OnClickListener{

    //Initialize Button objects to represent the buttons storing the 4 answers
    private Button button1,button2,button3,button4;

    //Initialize a layout to represent the button choices that are avaialble
    private TableLayout choicesTable;

    //Initialize variables to hold the value of the currently logged in user and tehir role
    private String loggedInUsername;
    private String loggedInUserRoleName;

    //Initialize variables to hold the user's current score on the quiz and the number of questions
    //remaining on the quiz
    private int numQuestionRemaining;
    private int currentScore;

    //Initialize an ArrayList to hold a list of the ids of questions that can be asked
    private ArrayList<String> questionBankIdList;
    private ArrayList<Question> questionList;
    private ArrayList<String> possibleAnswers;

    //Initialize a TextView object to show the user their current score on the quiz and number of
    //questions they have remaining
    private TextView currentTextScoreVal;
    private TextView currentTextNumQuestionReamaining;

    //Initialize a TextView object to hold the question instructions
    private TextView questionText;

    //Initialize a string to hold the database id of the question being asked
    private String selectedQuestionKey;

    //Initialize a variable to hold the String text of the correct answer
    private String correctAnswer;

    //Initialize intents to direct the user to the the appropriate view depending on whether the
    //user answers the question correctly
    private Intent intentTriviaCorrectAnswer;
    private Intent intentTriviaIncorrectAnswer;

    //Initialize  FirebaseDatabaseObject
    private DatabaseReference database;

    //Create mAuthObject
    private FirebaseAuth mAuth;

    //"Disable" the back button to keep the Extras passed between views from getting out of sync.
    //Display a Toast indicating that the back button is disabled.
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back button disabled during quiz.", Toast.LENGTH_SHORT).show();
    }

    //When the Activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question_prompt);

        //Get most of the extras from the previous page and set them accordingly.  Note that the
        //default number of questions in the quiz is 5, as indicated by the default value for
        //numQuestionRemaining
        loggedInUsername=getIntent().getStringExtra("username");
        loggedInUserRoleName=getIntent().getStringExtra("role_name");
        currentScore=getIntent().getIntExtra("currentScore",0);
        numQuestionRemaining=getIntent().getIntExtra("numQuestionRemaining",5);

        //If the questionBankIdList is passed as an extra, get the value, if not, create a blank
        //ArrayList to be filled later
        if(getIntent().getStringArrayListExtra("questionBankIdList")!=null){
            questionBankIdList=getIntent().getStringArrayListExtra("questionBankIdList");
        }
        else{
            questionBankIdList=new ArrayList<>();
        }

        //Set question and answer fields to be blank initially
        questionList=new ArrayList<Question>();
        possibleAnswers=new ArrayList<String>();
        correctAnswer="";;

        //create Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Set the choices table to be the object in the view which will hold the contents of the
        //page.
        choicesTable=findViewById(R.id.profile_content_table);

        //Set the current score and num questions remaining values according to what was passed
        //from the previous page
        currentTextScoreVal=findViewById(R.id.currentScoreVal);
        currentTextScoreVal.setText(String.valueOf(currentScore));
        currentTextNumQuestionReamaining=findViewById(R.id.numRemainingVal);
        currentTextNumQuestionReamaining.setText(String.valueOf(numQuestionRemaining));

        //Assign question instructions and answers to TexViews and buttons
        questionText=findViewById(R.id.editTextQuestions);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        //Assign onclickListeners to buttons
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //Get the list of questions that can be asked and the question to asked.
        //Note that loops are used since Extras do not pass Map objects.  If future versions of
        //Android allow passing maps, then use those instead of arraylists
        database.child("Question").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //Add all questions in the database to the questionList
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        questionList.add(child.getValue(Question.class));
                    }

                    //If the questionBankIdList is empty (no previous question), add the ids of all
                    //questions in the database to the list
                    if(questionBankIdList.size()==0){
                        for(Question element: questionList){
                            questionBankIdList.add(element.getId());
                        }
                    }

                    //if the questionBankIdList is not empty (page has been accessed as a result of
                    //another quiz question, then delete the questions whose ids are not in the
                    //questionBankIdList from the question list
                    else{

                        //Temp variable to hold the ids to delete
                        ArrayList<String> idsToDelete=new ArrayList<>();

                        //Loop through question list and check if quesiton is in the
                        //questionBankIdList.  If not, add it's id for deletion, if yes, do nothing
                        for(int i=0; i<questionList.size(); i++){
                            String searchId=questionList.get(i).getId();
                            int found=0;
                            for(int c=0; c<questionBankIdList.size(); c++){
                                if(questionBankIdList.get(c).equals(searchId)){
                                    found=1;
                                }
                            }
                            if(found==0){
                                idsToDelete.add(questionList.get(i).getId());
                            }
                        }

                        //Once the list of all ids to be deleted is gathered, loop through the
                        //questionList and remove those questions from the arraylist
                        for(int i=0; i<idsToDelete.size(); i++){
                            for(int c=0; c<questionList.size(); c++){
                                if(questionList.get(c).getId().equals(idsToDelete.get(i))){
                                    questionList.remove(c);
                                }
                            }
                        }

                    }

                    //Of the questions that can be asked, randomly select a question to ask and
                    //And then remove the question from the questionBankIdList.  Break out of the
                    //loop once the id is removed.
                    int elementToUse=new Random().nextInt(questionBankIdList.size());
                    String idOfQuestionToRemove=questionList.get(elementToUse).getId();
                    for(int i=0; i<questionBankIdList.size(); i++){
                        if(idOfQuestionToRemove.equals(questionBankIdList.get(i))){
                            questionBankIdList.remove(i);
                            break;
                        }
                    }

                    //Get the information about the question to add and store in variables
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_correct_answer());
                    correctAnswer=questionList.get(elementToUse).getQuestion_correct_answer();
                    selectedQuestionKey=questionList.get(elementToUse).getId();
                    questionText.setText(questionList.get(elementToUse).getQuestion_instructions());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer1());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer2());
                    possibleAnswers.add(questionList.get(elementToUse).getQuestion_wrong_answer3());

                    //Shuffl the arraylist of answers and set them to the buttons in the view
                    Collections.shuffle(possibleAnswers);
                    button1.setText(possibleAnswers.get(0));
                    button2.setText(possibleAnswers.get(1));
                    button3.setText(possibleAnswers.get(2));
                    button4.setText(possibleAnswers.get(3));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onClick(View v) {

        //If the first answer is selected, determine if it is correct or incorrect.
        //If correct, add to current score, and reduce the number of questions remaining.
        //If incorrect, only reduce the number of questions remaining

        //Regardless, attach the username, role_name, currentScore, numQuestionRemaining, and
        //questionBankIdlist as Extras to be used on the next page.

        //Also update the question stats according to how the question was answered.  Note that user
        //stats will not be updated until the user completes the quiz

        if(v==button1){

            //If question answered correctly
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

                //update question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //Kickoff next activity
                startActivity(intentTriviaCorrectAnswer);
            }

            //If question answered incorrectly
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
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
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(intentTriviaIncorrectAnswer);
            }
        }

        //If the second answer is selected, determine if it is correct or incorrect.
        //If correct, add to current score, and reduce the number of questions remaining.
        //If incorrect, only reduce the number of questions remaining

        //Regardless, attach the username, role_name, currentScore, numQuestionRemaining, and
        //questionBankIdlist as Extras to be used on the next page.

        //Also update the question stats according to how the question was answered.  Note that user
        //stats will not be updated until the user completes the quiz

        else if (v==button2) {

            //if answered correctly
            if(button2.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                //update Question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //initiate appropriate activity
                startActivity(intentTriviaCorrectAnswer);
            }
            //if answered incorectly
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
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                //initiate appropriate activity
                startActivity(intentTriviaIncorrectAnswer);
            }
        }

        //If the third answer is selected, determine if it is correct or incorrect.
        //If correct, add to current score, and reduce the number of questions remaining.
        //If incorrect, only reduce the number of questions remaining

        //Regardless, attach the username, role_name, currentScore, numQuestionRemaining, and
        //questionBankIdlist as Extras to be used on the next page.

        //Also update the question stats according to how the question was answered.  Note that user
        //stats will not be updated until the user completes the quiz

        else if (v==button3) {

            //If answered correctly
            if(button3.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                //Update question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //initiate appropriate activity
                startActivity(intentTriviaCorrectAnswer);
            }

            //If answered incorrectly
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                //update question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //initiate appropriate activity
                startActivity(intentTriviaIncorrectAnswer);
            }
        }

        //If the fourth answer is selected, determine if it is correct or incorrect.
        //If correct, add to current score, and reduce the number of questions remaining.
        //If incorrect, only reduce the number of questions remaining

        //Regardless, attach the username, role_name, currentScore, numQuestionRemaining, and
        //questionBankIdlist as Extras to be used on the next page.

        //Also update the question stats according to how the question was answered.  Note that user
        //stats will not be updated until the user completes the quiz

        else if (v==button4) {

            //if answered correctly
            if(button4.getText().toString().equals(correctAnswer)){
                currentScore++;
                numQuestionRemaining--;
                intentTriviaCorrectAnswer=new Intent(getApplicationContext(),TriviaCorrectAnswerActivity.class);
                intentTriviaCorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaCorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaCorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaCorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaCorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                //Update question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        theQuestion.setNum_times_answered_correctly(theQuestion.getNum_times_answered_correctly()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //initiate appropriate activity
                startActivity(intentTriviaCorrectAnswer);
            }

            //if answered incorrectly
            else {
                numQuestionRemaining--;
                intentTriviaIncorrectAnswer=new Intent(getApplicationContext(),TriviaIncorrectAnswerActivity.class);
                intentTriviaIncorrectAnswer.putExtra("username", loggedInUsername);
                intentTriviaIncorrectAnswer.putExtra("role_name", loggedInUserRoleName);
                intentTriviaIncorrectAnswer.putExtra("currentScore", currentScore);
                intentTriviaIncorrectAnswer.putExtra("numQuestionRemaining", numQuestionRemaining);
                intentTriviaIncorrectAnswer.putStringArrayListExtra("questionBankIdList",questionBankIdList);

                //update question stats
                database.child("Question").orderByChild("id").equalTo(selectedQuestionKey).addListenerForSingleValueEvent(new ValueEventListener()  {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String questionKey="";
                        Question theQuestion=new Question();
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            questionKey=child.getKey();
                            theQuestion=child.getValue(Question.class);
                        }
                        theQuestion.setNum_times_asked(theQuestion.getNum_times_asked()+1);
                        database.child("Question").child(questionKey).setValue(theQuestion);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                //initiate appropriate activity
                startActivity(intentTriviaIncorrectAnswer);
            }
        }

    }
}
