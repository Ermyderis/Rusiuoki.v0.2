package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotLogedTrasTypeCutSearch extends AppCompatActivity {

    private Button buttonContentSaveTrashCutInfo;
    private MaterialToolbar topBar;
    private Spinner spinnerCutCode;
    DatabaseReference databaseReference;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotLogedTrasTypeCutSearch.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tras_type_cut_search);


        spinnerCutCode = findViewById(R.id.spinnerContentTrashTypeCut);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.TrashCutCode, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCutCode.setAdapter(adapter1);

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

        buttonContentSaveTrashCutInfo = findViewById(R.id.buttonContentSaveTrashCutInfo);
        buttonContentSaveTrashCutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoByCut();
            }
        });
    }
    private void getInfoByCut(){
        String spinerData = spinnerCutCode.getSelectedItem().toString();
        //Toast.makeText(TrasTypeCutContent.this, cutString, Toast.LENGTH_LONG).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("TrashTypeCutInfo");
        databaseReference.child(spinerData).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClassTrashTypeCutInfo trashTypeCutInfo = snapshot.getValue(ClassTrashTypeCutInfo.class);
                if (trashTypeCutInfo != null){
                    Intent intent = new Intent(NotLogedTrasTypeCutSearch.this, NotLogedTrashTypeCutContentShow.class);
                    intent.putExtra("cut", trashTypeCutInfo.trashCut.toString());
                    intent.putExtra("recyclePlace", trashTypeCutInfo.trashRecyclePlaceByCut.toString());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(NotLogedTrasTypeCutSearch.this, "Neegzistuoja", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NotLogedTrasTypeCutSearch.this, "Duomenų nerasta", Toast.LENGTH_LONG).show();
                turnOnHome();
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