package me.mgosling.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // static key name for the users name
    public static final String USER_NAME = "me.mgosling.quizapp.USERNAME";

    EditText nameEditText;
    Button nameSubmitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        nameSubmitBtn = findViewById(R.id.nameSubmitBtn);


        // set the fabs onclick listener so it checks to see if there's anything entered for
        // a name. If there is, it calls start quiz. If there's not, it shows a toast explaining
        // to the user that they need to enter their name.
        nameSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().length() > 0)
                    startQuiz();
                else
                    Toast.makeText(getBaseContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // starts the quiz by getting the name text and storing it as a string, then starting the
    // Question Activity by creating an intent, passing in the user's name, and starting the activity
    protected void startQuiz(){
        String userName = nameEditText.getText().toString();
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(USER_NAME,userName);
        startActivity(intent);
    }
}
