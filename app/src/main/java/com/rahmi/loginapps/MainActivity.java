package com.rahmi.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.rahmi.loginapps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREF_CREDENTIAL = "mySPC";

    private ActivityMainBinding binding;

    SharedPreferences sharedPreferences;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(SHARED_PREF_CREDENTIAL, MODE_PRIVATE);

        binding.btnLogout.setOnClickListener(view1 -> {
            Logout();
        });
    }

    private void Logout() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(MainActivity.this)
                .setTitle("Log out")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    if (sharedPreferences.getString("username", null) != null) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", null);
                        editor.putString("password", null);
                        editor.apply();
                    }
                    if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getProviderId().equals("microsoft.com")) {
                        mAuth.signOut();
                    } else {
                        mAuth.signOut();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                    Toast.makeText(MainActivity.this, "Logout Success!", Toast.LENGTH_SHORT).show();
                    Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(logout);
                    dialogInterface.dismiss();
                    finish();
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Logout();
    }
}