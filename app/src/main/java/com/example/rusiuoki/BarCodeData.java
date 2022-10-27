package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BarCodeData extends AppCompatActivity {
    EditText editBarCodeData, editTrashName;
    private Spinner spinnerTrashType, spinnerTrashPlace;
    DatabaseReference databaseReference;
    Button buttonSave;
    private String choiceType, choiceTrashePlace;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BarCodeData.this, ChoseBarcodeScanOrWrite.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_data);


        editBarCodeData = findViewById(R.id.editBarCodeData);
        String barCodeNumbers = getIntent().getStringExtra("barCodeContent");
        editBarCodeData.setText(barCodeNumbers);

        buttonSave = findViewById(R.id.buttonSave);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("BarcodeDataByUsers");

        spinnerTrashType = findViewById(R.id.spinnerTrashCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TrashType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashType.setAdapter(adapter);

        spinnerTrashPlace = findViewById(R.id.spinnerTrashPlace);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.TrashPlace, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashPlace.setAdapter(adapter1);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTrashName = findViewById(R.id.editTrashName);
                String trashName = editTrashName.getText().toString().trim();
                choiceType = spinnerTrashType.getSelectedItem().toString();
                choiceTrashePlace =  spinnerTrashPlace.getSelectedItem().toString();
                saveValue(barCodeNumbers, trashName, choiceType, choiceTrashePlace);
            }
        });

    }


    void saveValue(String barCodeNumbers, String trashName, String choiceType, String choiceTrashePlace){
        if (choiceType == "-Pasirinkti-") {
            Toast.makeText(getApplicationContext(), "Pasirinkite atliekos tipą", Toast.LENGTH_LONG).show();
        }
        if (trashName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Įveskite atliekos pavadinimą", Toast.LENGTH_LONG).show();
        }
        else{
            BarCode barCode = new BarCode(barCodeNumbers,trashName, choiceType, choiceTrashePlace, "notAproved");
            databaseReference.child(barCodeNumbers).setValue(barCode);
            Toast.makeText(getApplicationContext(), "Duomenys pateikti", Toast.LENGTH_LONG).show();
            showMainActivity();
        }
    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}