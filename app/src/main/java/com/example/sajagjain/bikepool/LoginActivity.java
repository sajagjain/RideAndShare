package com.example.sajagjain.bikepool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    TextView number;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number = findViewById(R.id.phone_number);
        submit = findViewById(R.id.login_or_sign_up_submit);

        String from = getIntent().getStringExtra("frompage");



        if (from.equalsIgnoreCase("login")) {
            submit.setText("Login");
        } else if (from.equalsIgnoreCase("signup")) {
            submit.setText("Sign Up");
        }
        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = number.getText().toString();

                if (phoneNumber.length() != 10) {
                    Toast.makeText(LoginActivity.this, "This App Works for 10 Digit Phone Numbers", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phoneNumber)) {
                    number.setError("Invalid phone number.");
                } else if (!TextUtils.isDigitsOnly(phoneNumber)) {
                    number.setError("Please Enter Valid Number");
                } else {
                    startActivity(new Intent(LoginActivity.this, OTPWindow.class).putExtra("phoneNumber", phoneNumber));
                }
            }
        });

    }



}
