package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrashTypeCut extends AppCompatActivity {
    private Button buttonSaveTrashCutInfo;
    private EditText editTypeCut, editTrashTypeCutRecyclePlace;
    private DatabaseReference databaseReference;
    private ProgressBar progresBar;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TrashTypeCut.this, LogedActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_type_cut);

        progresBar = findViewById(R.id.progressBar);

        editTypeCut = findViewById(R.id.editTrashTypeCut);
        editTrashTypeCutRecyclePlace = findViewById(R.id.editTrashTypeCutRecyclePlace);

        buttonSaveTrashCutInfo = findViewById(R.id.buttonSaveTrashCutInfo);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TrashTypeCutInfo");

        buttonSaveTrashCutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trashTypeCut = editTypeCut.getText().toString().toUpperCase().trim();
                String trashTypeCutRecyclePlace  = editTrashTypeCutRecyclePlace.getText().toString().trim();
                progresBar.setVisibility(View.VISIBLE);
                TrashTypeCutInfo trashTypeCutInfo = new TrashTypeCutInfo(trashTypeCut, trashTypeCutRecyclePlace);
                databaseReference.child(trashTypeCut).setValue(trashTypeCutInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Duomenys įsaugoti", Toast.LENGTH_LONG).show();
                            showMainActivity();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Duomenys neįsaugoti", Toast.LENGTH_LONG).show();
                            showMainActivity();
                        }
                    }
                });

            }
        });
    }
    private void showMainActivity(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
}