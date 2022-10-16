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

public class WriteBarcode extends AppCompatActivity {
    private MaterialToolbar topBar;
    String barcodeString;
    EditText barcodeEditText;
    Button searchByBarCode;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_barcode);

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("BarcodeDataByUsers");

        barcodeEditText = (EditText) findViewById(R.id.barcodeEditText);
        searchByBarCode = findViewById(R.id.searchByBarCode);

        searchByBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcodeForSearch = barcodeEditText.getText().toString();
                databaseReference.child(barcodeForSearch).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BarCode barCode = snapshot.getValue(BarCode.class);
                        if(barCode != null){
                            Intent intent = new Intent(WriteBarcode.this, BarcodeDataExist.class);
                            intent.putExtra("fullBarcode", barcodeForSearch);
                            intent.putExtra("barCodePackageName", barCode.packageName.toString());
                            intent.putExtra("barCodePackageType", barCode.packageType.toString());
                            intent.putExtra("barCodeRecyclePlace", barCode.packageRecyclePlace.toString());
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(WriteBarcode.this, "Rezultatų nerasta", Toast.LENGTH_LONG).show();
                            turnOnHome();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });
    }

    public void openbarcodeData(){
        Intent intent = new Intent(this, BarCodeData.class);
        intent.putExtra("barCodeContent", barcodeString);
        startActivity(intent);
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