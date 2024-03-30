package com.rahmi.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                Intent intentToMain = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentToMain);
            } else {
                Intent intentToLogin = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intentToLogin);
            }
            finish();
        }, 2000);
    }
}