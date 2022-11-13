package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotLogedFindedTrashesByWord extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MaterialToolbar topBar;
    private TextView textViewTrashName, textViewTrashRecyclePlace;
    private Button buttonMaps;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotLogedFindedTrashesByWord.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_loged_finded_trashes_by_word);


        buttonMaps = findViewById(R.id.buttonMaps);
        buttonMaps.setVisibility(View.GONE);

        textViewTrashName = findViewById(R.id.textViewTrashName);
        String trashWord = getIntent().getStringExtra("trashWord");
        textViewTrashName.setText(trashWord);

        textViewTrashRecyclePlace = findViewById(R.id.textViewTrashRecyclePlace);
        String trashRecyclePlace = getIntent().getStringExtra("recyclePlace");
        textViewTrashRecyclePlace.setText(trashRecyclePlace);

        if(trashRecyclePlace.equals("Atliekų atsikratymo aišktelė")){
            buttonMaps.setVisibility(View.VISIBLE);
        }

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotLogedFindedTrashesByWord.this, Map.class);
                startActivity(intent);
                finish();
            }
        });
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