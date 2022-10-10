package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editPassword, editRPassword, editEmail, editName, editSurename;
    private Button btnRegister;
    private ProgressBar progresBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //Check if user is already authenticated or not
        btnRegister = findViewById(R.id.buttonRegistration);
        progresBar = findViewById(R.id.progressBar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }
    private void registerUser() {
        editPassword = findViewById(R.id.registerPassword);
        editEmail = findViewById(R.id.registerEmail);
        editRPassword = findViewById(R.id.registerRepeatPassword);
        editName = findViewById(R.id.registerName);
        editSurename = findViewById(R.id.registerSurname);

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String rPassword = editRPassword.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String surename = editSurename.getText().toString().trim();



        if (name.isEmpty() || surename.isEmpty() ||password.isEmpty() || email.isEmpty() || rPassword.isEmpty()) {
            Toast.makeText(this, "Užpildykite visus langelius", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!password.equals(rPassword)){
            Toast.makeText(this, "Slaptažodžiai nesutampa", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Elektroninis paštas neatitinka modelio", Toast.LENGTH_LONG).show();
            return;
        }
        else if (password.length() < 6){
            Toast.makeText(this, "Slaptažodį turi sudaryti bet 6 simboliai", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            progresBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(name, surename, email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Vartotojas užregistruotas", Toast.LENGTH_LONG).show();
                                    progresBar.setVisibility(View.GONE);
                                    //editPassword.setText("");
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Vartotojo nebuvo galima užregistruoti", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Klaida registruojant vartotoją", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }





    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}