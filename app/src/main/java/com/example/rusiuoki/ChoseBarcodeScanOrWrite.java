package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ChoseBarcodeScanOrWrite extends AppCompatActivity implements View.OnClickListener{

    Button scanBarCode;
    Button writeBarCode;
    DatabaseReference databaseReference;
    private MaterialToolbar topBar;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChoseBarcodeScanOrWrite.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_barcode_scan_or_write);

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

        scanBarCode = (Button) findViewById(R.id.buttonScanBarCode);
        scanBarCode.setOnClickListener(this);

        writeBarCode = (Button) findViewById(R.id.buttonWriteBarCode);
        writeBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWriteBarCode();
            }
        });
    }
    public void openWriteBarCode(){
        Intent intent = new Intent(this, WriteBarcode.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(false);
        integrator.setTimeout(10000);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Brūkšninio kodo skanavimas");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String barcodeForSearch = result.getContents().toString();



                databaseReference.child(barcodeForSearch).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BarCode barCode = snapshot.getValue(BarCode.class);
                        if(barCode != null && barCode.activityType.equals("AprovedTrue"))
                        {
                            Intent intent = new Intent(ChoseBarcodeScanOrWrite.this, BarcodeDataExist.class);
                            intent.putExtra("fullBarcode", barcodeForSearch);
                            intent.putExtra("barCodePackageName", barCode.packageName.toString());
                            intent.putExtra("barCodePackageType", barCode.packageType.toString());
                            intent.putExtra("barCodeRecyclePlace", barCode.packageRecyclePlace.toString());
                            startActivity(intent);
                            finish();
                        }
                        else if (barCode != null && barCode.activityType.equals("notAproved"))
                        {
                            Toast.makeText(ChoseBarcodeScanOrWrite.this, "Duomenys laukia patvirtinimo" , Toast.LENGTH_LONG).show();
                            turnOnHome();
                        }
                        else{
                            Intent intent = new Intent(ChoseBarcodeScanOrWrite.this, BarCodeData.class);
                            intent.putExtra("barCodeContent", result.getContents());
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            } else {
                Toast.makeText(this, "Nėra rezultatų", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);

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