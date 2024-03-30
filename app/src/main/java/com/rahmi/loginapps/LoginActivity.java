package com.rahmi.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.rahmi.loginapps.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public static final String SHARED_PREF_CREDENTIAL = "mySPC";

    private ActivityLoginBinding binding;

    DatabaseHelper db;

    FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(SHARED_PREF_CREDENTIAL, MODE_PRIVATE);

        String savedUsername = sharedPreferences.getString("username", null);
        String savedPassword = sharedPreferences.getString("password", null);

        if (savedUsername != null && savedPassword != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.btnLogin.setOnClickListener(view1 -> {
            String user = binding.edtEmail.getText().toString().trim();
            String pass = binding.edtPassword.getText().toString().trim();

            if (user.isEmpty() && pass.isEmpty()) {
                Toast.makeText(this, "Username atau password belum diisi.", Toast.LENGTH_SHORT).show();
            } else {
                showLoading(true);
                mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
                    showLoading(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login sukses.", Toast.LENGTH_SHORT).show();
                        Intent intent_login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_login);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Gagal. Silahkan coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tvForgetPassword.setOnClickListener(view2 -> {
            Intent intent_forgot_pass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent_forgot_pass);
        });

        binding.tvRegister.setOnClickListener(view2 -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        binding.ivMicrosoft.setOnClickListener(view3 -> {
            OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
            showLoading(true);

            mAuth.startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(authResult -> {
                        showLoading(false);

                        Toast.makeText(LoginActivity.this, "Login with Microsoft success.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        showLoading(false);
                        Toast.makeText(LoginActivity.this, "Login with Microsoft failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}