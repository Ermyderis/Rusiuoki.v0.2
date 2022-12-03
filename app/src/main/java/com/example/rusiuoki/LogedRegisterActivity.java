package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LogedRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editPassword, editRPassword, editEmail, editName, editSurename;
    private Button btnRegister;
    private MaterialToolbar topBar;
    private ProgressBar progresBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogedRegisterActivity.this, LogedActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView textViewLastName = findViewById(R.id.textViewLastName);
        String userEmail = getIntent().getStringExtra("userEmail");
        textViewLastName.setText(userEmail);


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

        topBar = findViewById(R.id.topAppBarLoged);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logedout_menu:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LogedRegisterActivity.this, "Atsijungta nuo paskyros", Toast.LENGTH_LONG).show();
                        showMainActivity();
                        return true;
                    case R.id.main_menu:
                        turnOnHome();
                        return true;
                }
                return false;
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

        char element;
        int digit = 0;
        for(int index = 0; index < password.length(); index++ ){
            element = password.charAt( index );
            if( Character.isDigit(element) ){
                digit++;
            }
        }

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
        else if (password.length() < 8){
            Toast.makeText(this, "Slaptažodį turi sudaryti bet 6 simboliai", Toast.LENGTH_LONG).show();
            return;
        }
        else if( digit < 2 ){
            Toast.makeText(this, "Slaptažodis turi turėti bent 2 skaičius", Toast.LENGTH_LONG).show();
            return;
        }
        if( !password.matches("[a-zA-Z0-9]+") ){
            /* A non-alphanumeric character was found, return false */
            Toast.makeText(this, "Slaptažodis turi turėti ir skaičius ir raides", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            progresBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        ModelUser user = new ModelUser(name, surename, email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(LogedRegisterActivity.this, "Vartotojas užregistruotas", Toast.LENGTH_LONG).show();
                                    progresBar.setVisibility(View.GONE);
                                    FirebaseAuth.getInstance().signOut();
                                    showMainActivity();
                                }
                                else{
                                    Toast.makeText(LogedRegisterActivity.this, "Vartotojo nebuvo galima užregistruoti", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(LogedRegisterActivity.this, "Klaida registruojant vartotoją", Toast.LENGTH_LONG).show();
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
    private void turnOnHome(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
}