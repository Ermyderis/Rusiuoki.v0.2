package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class LogedDatabaseBarcodeList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BarCode> barcodeArrayList;
    BarcodeItemsAdapter myAdapter;
    DatabaseReference databaseReference;
    FirebaseDatabase db;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogedDatabaseBarcodeList.this, LogedActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_database_barcode_list);

        recyclerView = findViewById(R.id.dataByBarcodeLoged);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("BarcodeDataByUsers");
        barcodeArrayList = new ArrayList<BarCode>();
        myAdapter = new BarcodeItemsAdapter(LogedDatabaseBarcodeList.this, barcodeArrayList);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BarCode barCode = dataSnapshot.getValue(BarCode.class);
                    if(barCode.activityType.toString().equals("notAproved")) {
                        barcodeArrayList.add(barCode);
                    }


                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    }
