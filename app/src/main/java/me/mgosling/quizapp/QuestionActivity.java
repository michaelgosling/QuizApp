package me.mgosling.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuestionActivity extends AppCompatActivity {

    String userName, currentScoreText, term, possibleAnswers[], correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // get the intent that started this activity, then extract the string
        Intent intent = getIntent();
        userName = intent.getStringExtra(MainActivity.USER_NAME);


    }

}
