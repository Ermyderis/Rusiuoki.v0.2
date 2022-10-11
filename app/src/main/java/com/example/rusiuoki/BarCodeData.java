package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BarCodeData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editBarCodeData;
    private Spinner spinnerTrashType, spinnerTrashPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_data);

        editBarCodeData = findViewById(R.id.editBarCodeData);
        String barCodeNumbers = getIntent().getStringExtra("barCodeContent");
        editBarCodeData.setText(barCodeNumbers);

        spinnerTrashType = findViewById(R.id.spinnerTrashCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TrashType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashType.setAdapter(adapter);
        spinnerTrashType.setOnItemSelectedListener(this);

        spinnerTrashPlace = findViewById(R.id.spinnerTrashPlace);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.TrashPlace, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashPlace.setAdapter(adapter1);
        spinnerTrashPlace.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        String barCodeNumbers = getIntent().getStringExtra("barCodeContent");
        Toast.makeText(getApplicationContext(), barCodeNumbers + choice, Toast.LENGTH_LONG).show();
        String choice1 = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice1, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}