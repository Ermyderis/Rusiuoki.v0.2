package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LogedEditableViewHolder extends AppCompatActivity {

    String barcodeNumber, trashName, aprovedOrNotAproved;
    EditText pname;
    private Spinner spinnerTrashType, spinnerTrashPlace;
    private String packageType, choiceTrashePlace;
    Switch swichActiveType;
    Button buttonSave;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_editable_view_holder);
        pname = findViewById(R.id.edittextPopupPackageName);

        spinnerTrashType = findViewById(R.id.spinnerPopupTrashCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TrashType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashType.setAdapter(adapter);

        spinnerTrashPlace = findViewById(R.id.spinnerPopupTrashPlac);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.TrashPlace, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrashPlace.setAdapter(adapter1);

        swichActiveType = findViewById(R.id.switchPopupActive);
        buttonSave = findViewById(R.id.buttonPopupSave);

        if(getIntent().getBundleExtra("data")!=null){
            Bundle bundle=getIntent().getBundleExtra("data");
            pname.setText(bundle.getString("packageName"));
            spinnerTrashType.setSelection(getIndex(spinnerTrashType, bundle.getString("trashType")));
            spinnerTrashPlace.setSelection(getIndex(spinnerTrashPlace, bundle.getString("trashPlace")));
            barcodeNumber = (bundle.getString("barcodeNumber"));
        }
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trashName = pname.getText().toString().trim();
                packageType = spinnerTrashType.getSelectedItem().toString();
                choiceTrashePlace =  spinnerTrashPlace.getSelectedItem().toString();
                if(swichActiveType.isChecked()){
                    aprovedOrNotAproved = "AprovedTrue";
                    updateData(barcodeNumber, trashName, packageType, choiceTrashePlace, aprovedOrNotAproved);

                }
                else{
                    aprovedOrNotAproved = "notAproved";
                    updateData(barcodeNumber, trashName, packageType, choiceTrashePlace, aprovedOrNotAproved);
                }
            }
        });

    }
    private void updateData(String barCodeNumbers, String packageName, String packageType, String choiceTrashePlace, String aprovedOrNotAproved){
        HashMap BarCode = new HashMap();
        BarCode.put("barCode", barCodeNumbers);
        BarCode.put("packageName", packageName);
        BarCode.put("packageRecyclePlace", choiceTrashePlace);
        BarCode.put("packageType", packageType);
        BarCode.put("activityType", aprovedOrNotAproved);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("BarcodeDataByUsers");
        databaseReference.child(barCodeNumbers).updateChildren(BarCode).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogedEditableViewHolder.this, "Duomenys Pakeisti" , Toast.LENGTH_LONG).show();
                    showLogedDatabaseBarcodeList();
                }
                else{
                    Toast.makeText(LogedEditableViewHolder.this, "Duomenų nepavyko pakeisti" , Toast.LENGTH_LONG).show();
                    showLogedDatabaseBarcodeList();
                }
            }
        });
    }
    private void showLogedDatabaseBarcodeList(){
        Intent intent = new Intent(this, LogedDatabaseBarcodeList.class);
        startActivity(intent);
        finish();
    }
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}