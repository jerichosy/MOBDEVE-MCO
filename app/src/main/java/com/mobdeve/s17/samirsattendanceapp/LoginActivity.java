package com.mobdeve.s17.samirsattendanceapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.login_et_email);
        password = findViewById(R.id.login_et_password);
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    public void onLogin(View v) {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // If the password is incorrect or the email is formatted incorrectly
                            Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (exception instanceof FirebaseAuthInvalidUserException) {
                            // If the user corresponding to the email does not exist or has been disabled
                            Toast.makeText(LoginActivity.this, "Invalid user. Account may be disabled or not exist.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Other errors could be related to network issues, etc.
                            Toast.makeText(LoginActivity.this, "Login failed. Maybe check your connection and try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}