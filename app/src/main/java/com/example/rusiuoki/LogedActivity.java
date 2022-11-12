package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogedActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private MaterialToolbar topBar;
    public String emailInLoged;
    public CardView logedBarcodeCard, logedRegisterUser, logedItemContent;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogedActivity.this, LogedActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged);

        logedItemContent = (CardView) findViewById(R.id.logedItemContent);
        logedItemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTrachYpeCut();
            }
        });

        logedBarcodeCard = (CardView) findViewById(R.id.logedBarcodeCard);
        logedBarcodeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBarcodeList();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView textViewLastName = findViewById(R.id.textViewLastName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    emailInLoged = userProfile.email;
                    textViewLastName.setText(emailInLoged);
                }
                else{
                    textViewLastName.setText("Cant Show");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logedRegisterUser = (CardView) findViewById(R.id.logedRegisterUser);
        logedRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LogedActivity.this, RegisterActivity.class);
                intent.putExtra("userEmail", emailInLoged);
                startActivity(intent);
                finish();
            }
        });

        topBar = findViewById(R.id.topAppBarLoged);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logedout_menu:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LogedActivity.this, "Atsijungta nuo paskyros", Toast.LENGTH_LONG).show();
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
    private void openBarcodeList(){
        Intent intent = new Intent(this, LogedDatabaseBarcodeList.class);
        intent.putExtra("userEmail", emailInLoged);
        startActivity(intent);
        finish();
    }
    private void openTrachYpeCut(){
        Intent intent = new Intent(this, TrashTypeCut.class);
        intent.putExtra("userEmail", emailInLoged);
        startActivity(intent);
        finish();
    }

}