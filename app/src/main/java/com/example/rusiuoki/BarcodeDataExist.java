package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class BarcodeDataExist extends AppCompatActivity {
    private MaterialToolbar topBar;
    TextView textViewFullbarcdoe, textViewPackageName, textViewTrashType, textViewTrashPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_data_exist);

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

        textViewFullbarcdoe = findViewById(R.id.textViewFullbarcdoe);
        String barCodeNumbers = getIntent().getStringExtra("fullBarcode");
        textViewFullbarcdoe.setText(barCodeNumbers);

        textViewPackageName = findViewById(R.id.textViewPackageName);
        String packageName= getIntent().getStringExtra("barCodePackageName");
        textViewPackageName.setText(packageName);

        textViewTrashType = findViewById(R.id.textViewTrashType);
        String trashType= getIntent().getStringExtra("barCodePackageType");
        textViewTrashType.setText(trashType);

        textViewTrashPlace = findViewById(R.id.textViewTrashPlace);
        String trashPlace= getIntent().getStringExtra("barCodeRecyclePlace");
        textViewTrashPlace.setText(trashPlace);
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