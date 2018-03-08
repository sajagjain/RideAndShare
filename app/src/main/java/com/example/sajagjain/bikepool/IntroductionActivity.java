package com.example.sajagjain.bikepool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IntroductionActivity extends AppCompatActivity {

    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_introduction);

        login = findViewById(R.id.sign_in_button);
        signup = findViewById(R.id.sign_up_button);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(IntroductionActivity.this,HomeActivity.class));
            finishAfterTransition();
        }
    }

    public void signUpClicked(View view) {
        startActivity(new Intent(IntroductionActivity.this,LoginActivity.class).putExtra("frompage","signup"));
        finishAfterTransition();
    }

    public void loginClicked(View view) {
        startActivity(new Intent(IntroductionActivity.this,LoginActivity.class).putExtra("frompage","login"));
        finishAfterTransition();
    }
}
