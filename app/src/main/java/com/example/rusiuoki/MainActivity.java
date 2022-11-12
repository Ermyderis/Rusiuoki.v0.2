package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    private MaterialToolbar topBar;
    public CardView barCode, itemCont, itemByWord, helpWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barCode = findViewById(R.id.barCode);
        barCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChoseBarcodeScanOrWrite();
            }
        });

        itemCont = findViewById(R.id.itemContent);
        itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCutContent();
            }
        });

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

    private void openChoseBarcodeScanOrWrite(){
        Intent intent = new Intent(this, ChoseBarcodeScanOrWrite.class);
        startActivity(intent);
        finish();
    }
    private void openCutContent(){
        Intent intent = new Intent(this, NotLogedTrasTypeCutSearch.class);
        startActivity(intent);
        finish();
    }
}