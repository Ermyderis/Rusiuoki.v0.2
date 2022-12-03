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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotLogedTrashByWordSearch extends AppCompatActivity {

    private EditText editTextTrashByWord;
    private Button buttonContentSaveTrashByWord;
    private String trashByWord;
    private MaterialToolbar topBar;
    DatabaseReference databaseReference;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotLogedTrashByWordSearch.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_loged_trash_by_word_search);

        editTextTrashByWord = findViewById(R.id.editTextTrashByWord);
        buttonContentSaveTrashByWord = findViewById(R.id.buttonContentSaveTrashByWord);

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

        buttonContentSaveTrashByWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataByWord();
            }
        });
    }

    private void getDataByWord(){
        trashByWord = editTextTrashByWord.getText().toString().toLowerCase().trim();
        boolean result = trashByWord.matches("[a-zA-Z]+");
        if(trashByWord.length() == 0){
            Toast.makeText(NotLogedTrashByWordSearch.this, "Užpildykite laukelį", Toast.LENGTH_LONG).show();
        }
        else if(trashByWord.length() >= 20){
            Toast.makeText(NotLogedTrashByWordSearch.this, "Daugiausia gali būti 20 simbolių", Toast.LENGTH_LONG).show();
        }
        else if(result == false){
            Toast.makeText(NotLogedTrashByWordSearch.this, "Žodis gali būti tik vienas ir tik raidės", Toast.LENGTH_LONG).show();
        }
        else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("TrashByWord");
            databaseReference.child(trashByWord).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ModelTrashByWord trashByWord = snapshot.getValue(ModelTrashByWord.class);
                    if (trashByWord != null){
                        Intent intent = new Intent(NotLogedTrashByWordSearch.this, NotLogedFindedTrashesByWord.class);
                        intent.putExtra("trashWord", trashByWord.trashWord.toString());
                        intent.putExtra("recyclePlace", trashByWord.trashRecyclePlaceByWord.toString());
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(NotLogedTrashByWordSearch.this, "Neegzistuoja", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

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