package me.mgosling.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    TextView scoreText;
    TextView nameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // bind text views from layout to variable
        scoreText = findViewById(R.id.scoreTextView);
        nameText = findViewById(R.id.nameTextView);

        // get intent that started this activity, grab final score and users name
        // and set the text of the views accordingly.
        Intent intent = getIntent();
        scoreText.setText(intent.getStringExtra(QuestionActivity.FINAL_SCORE));
        nameText.setText(intent.getStringExtra(QuestionActivity.USER_NAME));

    }
}
