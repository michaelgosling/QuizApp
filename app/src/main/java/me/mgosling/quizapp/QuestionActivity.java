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

    // keys for username and final score
    public static final String USER_NAME = "me.mgosling.quizapp.USERNAME";
    public static final String FINAL_SCORE = "me.mgosling.quizapp.FINALSCORE";

    // on click listener for the answer buttons
    View.OnClickListener answerOCL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // get the intent that started this activity, then extract the username string
        // and create a new quiz object with it as the input stream for the quiz file
        Intent intent = getIntent();
        String userName = intent.getStringExtra(MainActivity.USER_NAME);
        quiz = new Quiz(userName, getBaseContext().getResources().openRawResource(R.raw.quiz));

        // OCL for answer buttons call check answer and pass their view in.
        answerOCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        };

        // bind buttons and text views to layout
        answerAbtn = findViewById(R.id.answerAbtn);
        answerBbtn = findViewById(R.id.answerBbtn);
        answerCbtn = findViewById(R.id.answerCbtn);
        answerDbtn = findViewById(R.id.answerDbtn);
        scoreTextView = findViewById(R.id.scoreTextView);
        defTextView = findViewById(R.id.defTextView);


        // set on click listener for answer buttons
        answerAbtn.setOnClickListener(answerOCL);
        answerBbtn.setOnClickListener(answerOCL);
        answerCbtn.setOnClickListener(answerOCL);
        answerDbtn.setOnClickListener(answerOCL);

        // display the first question
        displayQuestion();

    }

    // updates the score in the UI by grabbing the current score and total terms from
    // the quiz object
    private void updateScore() {
        String newScore = "Score: " + quiz.getCurrentScore() + "/" + quiz.getTotalTerms();
        scoreTextView.setText(newScore);
    }

    // gets the text from the button and uses it to guess the answer on the quiz.
    private void checkAnswer(View v) {
        Button btn = findViewById(v.getId());
        boolean correct = quiz.guessAnswer(btn.getText().toString());

        // display a message to the user to inform them if they were successful.
        // if they were right update their score in the UI
        if (correct) {
            Toast.makeText(getBaseContext(), "Correct!", Toast.LENGTH_SHORT).show();
            updateScore();
        } else {
            Toast.makeText(getBaseContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        // if the quiz is finished, add the user name and score to an intent for the final
        // activity and then start it
        if (quiz.isQuizFinished()) {
            Intent intent = new Intent(this, FinalActivity.class);
            intent.putExtra(USER_NAME, quiz.getUserName());
            intent.putExtra(FINAL_SCORE, quiz.getCurrentScore() + "/" + quiz.getTotalTerms());
            startActivity(intent);
        } else {
            // if it's not finished, display the next question
            displayQuestion();
        }
    }


    // grabs question information from the quiz object and sets the text views accordingly
    private void displayQuestion() {
        defTextView.setText(quiz.getDefinition());

        answerAbtn.setText(quiz.getPossibleAnswers().get(0));
        answerBbtn.setText(quiz.getPossibleAnswers().get(1));
        answerCbtn.setText(quiz.getPossibleAnswers().get(2));
        answerDbtn.setText(quiz.getPossibleAnswers().get(3));
    }


}
