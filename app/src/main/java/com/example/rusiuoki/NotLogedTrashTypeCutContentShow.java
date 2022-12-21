package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class NotLogedTrashTypeCutContentShow extends AppCompatActivity {

    private TextView textViewContentTrashTypeCut, textViewContentTrashTypeRecyclePlace;
    private MaterialToolbar topBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotLogedTrashTypeCutContentShow.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_type_cut_content_show);

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

        textViewContentTrashTypeCut = findViewById(R.id.textViewContentTrashTypeCut);
        String contentTrashTypeCut = getIntent().getStringExtra("cut");
        textViewContentTrashTypeCut.setText(contentTrashTypeCut);

        textViewContentTrashTypeRecyclePlace = findViewById(R.id.textViewContentTrashTypeRecyclePlace);
        String contentTrashTypeRecyclePlace = getIntent().getStringExtra("recyclePlace");
        textViewContentTrashTypeRecyclePlace.setText(contentTrashTypeRecyclePlace);
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