package me.mgosling.quizapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.mgosling.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // static key name for the users name
    public static final String USER_NAME = "me.mgosling.quizapp.USERNAME";

    // databinding property to avoid FindViewById
    protected ActivityMainBinding binding;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize binding with activity_main content view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        // set the fabs onclick listener so it checks to see if there's anything entered for
        // a name. If there is, it calls start quiz. If there's not, it shows a toast explaining
        // to the user that they need to enter their name.
        binding.nameSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nameEditText.getText().toString().length() > 0)
                    startQuiz();
                else
                    Toast.makeText(getBaseContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // starts the quiz by getting the name text and storing it as a string, then starting the
    // Question Activity by creating an intent, passing in the user's name, and starting the activity
    protected void startQuiz(){
        String userName = binding.nameEditText.getText().toString();
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(USER_NAME,userName);
        startActivity(intent);
    }
}
