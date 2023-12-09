package com.mobdeve.s17.samirsattendanceapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        editTextFirstName = (EditText) findViewById(R.id.register_et_firstName);
        editTextLastName = (EditText) findViewById(R.id.register_et_lastName);
        editTextEmail = (EditText) findViewById(R.id.register_et_email);
        editTextPassword = (EditText) findViewById(R.id.register_et_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.register_et_confirmPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "User logged in. Redirecting to Login.", Toast.LENGTH_SHORT).show();
            updateUI(currentUser);
        }
    }

    public void onRegister(View v) {
        if (editTextFirstName.getText().toString().isEmpty() || editTextLastName.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMatchingPassword()) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(editTextFirstName.getText().toString() + " " + editTextLastName.getText().toString()).build());
                            updateUI(user);
                        } else {
                            // If register fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "Email already exists.",
                                        Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters.",
                                        Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(RegisterActivity.this, "Invalid email.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // General authentication failure message
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean isMatchingPassword() {
        return editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString());
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}