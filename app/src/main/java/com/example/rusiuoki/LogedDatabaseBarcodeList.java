package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class LogedDatabaseBarcodeList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<BarCode> barcodeArrayList;
    private BarcodeItemsAdapter myAdapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private TextView textViewEmptyList;
    private MaterialToolbar topBar;

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


        textViewEmptyList = findViewById(R.id.textViewEmptyList);
        textViewEmptyList.setVisibility(View.VISIBLE);
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
                    if (barCode != null && barCode.activityType.toString().equals("notAproved")) {
                        textViewEmptyList.setVisibility(View.GONE);
                        barcodeArrayList.add(barCode);
                    }


                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(getIntent().getBundleExtra("data")!=null){
            Bundle bundle=getIntent().getBundleExtra("data");
            FirebaseDatabase.getInstance().getReference().child("BarcodeDataByUsers").child(bundle.getString("barcodeNumber")).removeValue();
            Toast.makeText(LogedDatabaseBarcodeList.this, "Ištrinta", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LogedDatabaseBarcodeList.class);
            startActivity(intent);
            finish();
        }

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
                        Toast.makeText(LogedDatabaseBarcodeList.this, "Atsijungta nuo paskyros", Toast.LENGTH_LONG).show();
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
