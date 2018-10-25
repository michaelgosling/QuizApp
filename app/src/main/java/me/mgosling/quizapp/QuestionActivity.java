package me.mgosling.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {

    private Quiz quiz;
    private Button answerAbtn, answerBbtn, answerCbtn, answerDbtn;
    private TextView scoreTextView, defTextView;

    public static final String USER_NAME = "me.mgosling.quizapp.USERNAME";
    public static final String FINAL_SCORE = "me.mgosling.quizapp.FINALSCORE";

    View.OnClickListener answerOCL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // get the intent that started this activity, then extract the username string
        // and create a new quiz object with it.
        Intent intent = getIntent();
        String userName = intent.getStringExtra(MainActivity.USER_NAME);
        quiz = new Quiz(userName, getBaseContext().getResources().openRawResource(R.raw.quiz));

        answerOCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        };

        answerAbtn = findViewById(R.id.answerAbtn);
        answerBbtn = findViewById(R.id.answerBbtn);
        answerCbtn = findViewById(R.id.answerCbtn);
        answerDbtn = findViewById(R.id.answerDbtn);

        scoreTextView = findViewById(R.id.scoreTextView);
        defTextView = findViewById(R.id.defTextView);


        answerAbtn.setOnClickListener(answerOCL);
        answerBbtn.setOnClickListener(answerOCL);
        answerCbtn.setOnClickListener(answerOCL);
        answerDbtn.setOnClickListener(answerOCL);

        displayQuestion();

    }

    private void updateScore() {
        String newScore = "Score: " + quiz.getCurrentScore() + "/" + quiz.getTotalTerms();
        scoreTextView.setText(newScore);
    }

    private void checkAnswer(View v) {
        Button btn = findViewById(v.getId());
        boolean correct = quiz.guessAnswer(btn.getText().toString());
        if (correct) {
            Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
            updateScore();
        } else {
            Toast.makeText(getBaseContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
        }
        if (quiz.isQuizFinished()) {
            Intent intent = new Intent(this, FinalActivity.class);
            intent.putExtra(USER_NAME, quiz.getUserName());
            intent.putExtra(FINAL_SCORE, quiz.getCurrentScore() + "/" + quiz.getTotalTerms());
            startActivity(intent);
        } else {
            displayQuestion();
        }
    }


    private void displayQuestion() {
        defTextView.setText(quiz.getDefinition());

        answerAbtn.setText(quiz.getPossibleAnswers().get(0));
        answerBbtn.setText(quiz.getPossibleAnswers().get(1));
        answerCbtn.setText(quiz.getPossibleAnswers().get(2));
        answerDbtn.setText(quiz.getPossibleAnswers().get(3));
    }


}
