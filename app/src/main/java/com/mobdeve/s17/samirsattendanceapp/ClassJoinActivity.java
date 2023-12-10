package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ClassJoinActivity extends AppCompatActivity {

    EditText et_join_code_input;
    Button btn_join_class;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_join);

        et_join_code_input = findViewById(R.id.et_join_code_input);
        btn_join_class = findViewById(R.id.btn_join_class);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_join_class.setOnClickListener(v -> {
            String join_code = et_join_code_input.getText().toString();
            // check if join code exists in database
            db.collection("classes").whereEqualTo("join_code", join_code).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (queryDocumentSnapshots.isEmpty()) {
                    // join code does not exist
                    et_join_code_input.setError("Join code does not exist!");
                    Toast.makeText(getApplicationContext(), "Join code does not exist!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if user is already a member of the class
                db.collection("memberships").whereEqualTo("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).whereEqualTo("join_code", join_code).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                    if (!queryDocumentSnapshots1.isEmpty()) {
                        // user is already a member of the class
                        et_join_code_input.setError("You are already a member of this class!");
                        Toast.makeText(getApplicationContext(), "You are already a member of this class!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // add user to the class if user is not member of class
                    db.collection("memberships").add(new MembershipData(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), join_code));
                    db.collection("classes").whereEqualTo("join_code", join_code).get().addOnSuccessListener(queryDocumentSnapshots2 -> {
                        String class_id = queryDocumentSnapshots2.getDocuments().get(0).getId();
                        db.collection("classes").document(class_id).update("members", queryDocumentSnapshots2.getDocuments().get(0).getLong("members") + 1);
                    });
                    Toast.makeText(getApplicationContext(), "Class joined!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }

}