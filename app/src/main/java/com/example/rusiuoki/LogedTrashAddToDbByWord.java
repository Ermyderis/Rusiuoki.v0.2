package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogedTrashAddToDbByWord extends AppCompatActivity {

    private EditText edittextTrashWord, edittextTrashThrowPlace;
    private Button buttonContentSaveTrashCutInfo;
    private String trashWord, trashThrowPlace;
    private DatabaseReference databaseReference;
    private MaterialToolbar topBar;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogedTrashAddToDbByWord.this, LogedActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_trash_add_to_db_by_word);

        buttonContentSaveTrashCutInfo = findViewById(R.id.buttonContentSaveTrashCutInfo);
        edittextTrashWord = findViewById(R.id.edittextTrashWord);
        edittextTrashThrowPlace = findViewById(R.id.edittextTrashThrowPlace);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TrashByWord");
        


        final TextView textViewLastName = findViewById(R.id.textViewLastName);
        String userEmail = getIntent().getStringExtra("userEmail");
        textViewLastName.setText(userEmail);
        topBar = findViewById(R.id.topAppBarLoged);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logedout_menu:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LogedTrashAddToDbByWord.this, "Atsijungta nuo paskyros", Toast.LENGTH_LONG).show();
                        showMainActivity();
                        return true;
                    case R.id.main_menu:
                        turnOnHome();
                        return true;
                }
                return false;
            }
        });

        buttonContentSaveTrashCutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trashWord = edittextTrashWord.getText().toString().trim();
                trashThrowPlace = edittextTrashThrowPlace.getText().toString().trim();

                if (trashWord.length() == 0 || trashThrowPlace.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Užpildykite visus langelius", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.orderByChild("trashWord").equalTo(trashWord).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Tokia atlieka jau egzistuoja", Toast.LENGTH_LONG).show();
                            }
                            else{
                                ClassTrashByWord trashByWord = new ClassTrashByWord(trashWord, trashThrowPlace);
                                databaseReference.child(trashWord).setValue(trashByWord).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Duomenys įsaugoti", Toast.LENGTH_LONG).show();
                                            showMainActivity();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Duomenys neįsaugoti", Toast.LENGTH_LONG).show();
                                            showMainActivity();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }
    private void showMainActivity(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
    private void turnOnHome(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
}