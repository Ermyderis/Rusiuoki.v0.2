package com.example.rusiuoki;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BarcodeItemsAdapter extends RecyclerView.Adapter<BarcodeItemsAdapter.MyViewHolder>{

    Context context;
    ArrayList<BarCode> barcodeArrayList;

    public BarcodeItemsAdapter(Context context, ArrayList<BarCode> barcodeArrayList) {
        this.context = context;
        this.barcodeArrayList = barcodeArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        final BarCode barcode = barcodeArrayList.get(position);

        holder.barCode.setText(barcode.barCode);
        holder.packageName.setText(barcode.packageName);
        holder.packageType.setText(barcode.packageType);
        holder.recyclePlace.setText(barcode.packageRecyclePlace);
        holder.status.setText(barcode.activityType);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("barcodeNumber", barcode.getBarCode());
                bundle.putString("packageName", barcode.getPackageName());
                bundle.putString("trashType", barcode.getPackageType());
                bundle.putString("trashPlace", barcode.getPackageRecyclePlace());
                bundle.putString("activityTipe", barcode.getActivityType());
                Intent intent = new Intent(context, LogedEditableViewHolder.class);
                intent.putExtra("data", bundle);
                context.startActivity(intent);

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(holder.packageName.getContext());
                builder.setTitle("Ar tikrai norite ištrinti?");
                builder.setPositiveButton("Ištrinti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("BarcodeDataByUsers").child(barcode.getBarCode()).removeValue();
                        Toast.makeText(holder.packageName.getContext(), "Ištrinta", Toast.LENGTH_LONG).show();
                        barcodeArrayList.remove(barcode);
                        notifyDataSetChanged();

                        Bundle bundle = new Bundle();

                        bundle.putString("barcodeNumber", barcode.getBarCode());
                        bundle.putString("packageName", barcode.getPackageName());
                        bundle.putString("trashType", barcode.getPackageType());
                        bundle.putString("trashPlace", barcode.getPackageRecyclePlace());
                        bundle.putString("activityTipe", barcode.getActivityType());
                        Intent intent = new Intent(context, LogedEditableViewHolder.class);
                        intent.putExtra("data", bundle);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Atšaukti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.packageName.getContext(), "Atšaukta", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return barcodeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView barCode, packageName, packageType, recyclePlace, status;
        Button btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barCode = itemView.findViewById(R.id.barCode);
            packageName = itemView.findViewById(R.id.packageName);
            packageType = itemView.findViewById(R.id.packageType);
            recyclePlace =itemView.findViewById(R.id.recyclePlace);
            status = itemView.findViewById(R.id.status);

            btnEdit = itemView.findViewById(R.id.buttonWrapperEdit);
            btnDelete = itemView.findViewById(R.id.buttonWrapperDelete);

        }
    }

}

