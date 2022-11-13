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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class LogedAddLocation extends AppCompatActivity {
    private EditText editLocationName, editLatitude, editLongitude;
    private Spinner spinnerLocation;
    private Button buttonSaveLocation;
    private MaterialToolbar topBar;
    private DatabaseReference databaseReference;
    private ProgressBar progresBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogedAddLocation.this, LogedActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_add_location);

        editLocationName = findViewById(R.id.editLocationName);
        editLatitude = findViewById(R.id.editLatitude);
        editLongitude = findViewById(R.id.editLongitude);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        buttonSaveLocation = findViewById(R.id.buttonSaveLocation);
        progresBar = findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("RecyclePLaceLocation");

        buttonSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        //Menu
        final TextView textViewLastName = findViewById(R.id.textViewLastName);
        String userEmail = getIntent().getStringExtra("userEmail");
        textViewLastName.setText(userEmail);
        topBar = findViewById(R.id.topAppBarLoged);
        topBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logedout_menu:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LogedAddLocation.this, "Atsijungta nuo paskyros", Toast.LENGTH_LONG).show();
                        showMainActivity();
                        return true;
                    case R.id.main_menu:
                        turnOnHome();
                        return true;
                }
                return false;
            }
        });
    }
    private void saveData() {
        String locationName = editLocationName.getText().toString().toUpperCase().toUpperCase(Locale.ROOT).trim();
        String latitude = editLatitude.getText().toString().trim();
        String longitude = editLongitude.getText().toString().trim();
        String location = spinnerLocation.getSelectedItem().toString();

        if (locationName.length() == 0 || latitude.length() == 0 || longitude.length() == 0) {
            Toast.makeText(getApplicationContext(), "Užpildykite visus langelius", Toast.LENGTH_LONG).show();
        } else {
            databaseReference.orderByChild("locationName").equalTo(locationName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(getApplicationContext(), "Tokie duomenys jau yra duomenų bazėje", Toast.LENGTH_LONG).show();
                    } else {
                        progresBar.setVisibility(View.VISIBLE);
                        ModelLocation locationModel = new ModelLocation(locationName, latitude, longitude, location);
                        Toast.makeText(getApplicationContext(), locationModel.locationName, Toast.LENGTH_LONG).show();
                        databaseReference.child(locationName).setValue(locationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Duomenys įsaugoti", Toast.LENGTH_LONG).show();
                                    showMainActivity();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Duomenys neįsaugoti", Toast.LENGTH_LONG).show();
                                    showMainActivity();
                                }
                            }
                        });

                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void turnOnHome(){
        Intent intent = new Intent(this, LogedActivity.class);
        startActivity(intent);
        finish();
    }
}