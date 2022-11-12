package com.example.rusiuoki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TrashTypeCutContentShow extends AppCompatActivity {

    private TextView textViewContentTrashTypeCut, textViewContentTrashTypeRecyclePlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_type_cut_content_show);

        textViewContentTrashTypeCut = findViewById(R.id.textViewContentTrashTypeCut);
        String contentTrashTypeCut = getIntent().getStringExtra("cut");
        textViewContentTrashTypeCut.setText(contentTrashTypeCut);

        textViewContentTrashTypeRecyclePlace = findViewById(R.id.textViewContentTrashTypeRecyclePlace);
        String contentTrashTypeRecyclePlace = getIntent().getStringExtra("recyclePlace");
        textViewContentTrashTypeRecyclePlace.setText(contentTrashTypeRecyclePlace);
    }
}