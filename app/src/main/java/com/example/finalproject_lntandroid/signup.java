package com.example.finalproject_lntandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject_lntandroid.databinding.ActivityMainBinding;
import com.example.finalproject_lntandroid.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView((View) binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, home.class));
        }
    }

    public void trySignup(View view){
        boolean isError = false;

        if(binding.txtFullname.getText().length() < 5) {
            binding.txtFullname.setError("Your name is required with at least 5 characters.");
            isError = true;
        }

        if(binding.txtID.getText().length() == 0) {
            binding.txtID.setError("Password is required.");
            isError = true;
        }

        String email = binding.txtEmail.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()
           || !Pattern.compile("^.*\\.com$", Pattern.CASE_INSENSITIVE).matcher(email).matches()){
            binding.txtEmail.setError("Must be a valid Email Address and ends with '.com'.");
            isError = true;
        }

        if(binding.txtPassword.getText().length() == 0){
            binding.txtPassword.setError("Password is required.");
            isError = true;
        }

        if(!binding.txtPassword.getText().toString().equals(binding.txtConfirm.getText().toString())){
            binding.txtConfirm.setError("Confirm password is not same as above.");
            isError = true;
        }

        if(!isError) doSignup();
    }

    public void doSignup(){
        String member_id = binding.txtID.getText().toString().toUpperCase();
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtPassword.getText().toString();

        db.collection("users").document(member_id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> db_task) {
                        if (db_task.isSuccessful()) {
                            if(db_task.getResult().exists()){
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> u_task) {
                                                if(u_task.isSuccessful()){
                                                    db.collection("users").document(member_id).delete();

                                                    String uid = u_task.getResult().getUser().getUid();
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("member_id", member_id);
                                                    user.put("user_id", uid);
                                                    user.put("registered", FieldValue.serverTimestamp());

                                                    db.collection("users").document(uid).set(user);

                                                    startActivity(new Intent(getApplicationContext(), home.class));
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(),
                                                            u_task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "You are not a member or member account is already registered.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), db_task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void back(View view){
        finish();
    }
}