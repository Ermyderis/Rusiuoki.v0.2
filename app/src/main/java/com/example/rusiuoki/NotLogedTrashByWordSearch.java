package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

public class NotLogedTrashByWordSearch extends AppCompatActivity {

    private EditText editTextTrashByWord;
    private Button buttonContentSaveTrashByWord;
    private String trashByWord;
    private MaterialToolbar topBar;

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
                trashByWord = editTextTrashByWord.getText().toString().trim();

                Intent intent = new Intent(NotLogedTrashByWordSearch.this, NotLogedFindedTrashesByWordList.class);
                intent.putExtra("trashWord", trashByWord);
                startActivity(intent);
                finish();
            }
        });
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