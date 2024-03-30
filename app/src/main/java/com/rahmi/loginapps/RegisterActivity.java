package com.rahmi.loginapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rahmi.loginapps.databinding.ActivityLoginBinding;
import com.rahmi.loginapps.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(view1 -> {
            String user = binding.edtEmail.getText().toString().trim();
            String pass = binding.edtPassword.getText().toString().trim();
            String confirm_pass = binding.edtConfirmPassword.getText().toString().trim();

            if(!confirm_pass.equals(pass)){
                binding.edtPassword.setError("Password Tidak Sama");
                binding.edtConfirmPassword.setError("Password Tidak Sama");
            }else{
                mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Pendaftaran Berhasil.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Pendafaran Gagal, Mohon coba lagi...", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}