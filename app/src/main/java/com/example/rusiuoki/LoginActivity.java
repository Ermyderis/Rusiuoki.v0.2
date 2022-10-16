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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button buttonLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progresBar;
    private MaterialToolbar topBar;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        topBar = findViewById(R.id.topAppBar);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.login_menu:
                        turnOnLogin();
                        return true;
                    case R.id.main_menu:
                        turnOnHome();
                        return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        progresBar = findViewById(R.id.progressBar);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logn();
            }

        });
    }
    private void logn(){
        loginEmail = findViewById(R.id.editLoginEmail);
        loginPassword = findViewById(R.id.editLoginPassword);

        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Užpildykite visus langelius", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Elektroninis paštas neatitinka modelio", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            progresBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Prijungtas", Toast.LENGTH_LONG).show();
                        showLoged();
                    }else{
                        Toast.makeText(LoginActivity.this, "Nepavyko prisijungti", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            });
        }


    }
    private void showLoged(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
    private void turnOnLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void turnOnHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
