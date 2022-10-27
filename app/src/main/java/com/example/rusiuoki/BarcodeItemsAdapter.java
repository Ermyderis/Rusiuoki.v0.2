package com.example.rusiuoki;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
                //EditText packageName = view.findViewById(R.id.edittextPopupPackageName);
                //Spinner spinnerTrashType = view.findViewById(R.id.spinnerPopupTrashCategory);
                //Spinner spinnerTrashPlace = view.findViewById(R.id.spinnerPopupTrashPlac);
                //Switch activityTipe = view.findViewById(R.id.switchPopupActive);

                //Button btnSvaeChanges = view.findViewById(R.id.buttonPopupSave);

                //packageName.setText(barcode.getPackageName());

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
