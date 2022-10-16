package com.example.rusiuoki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        BarCode barcode = barcodeArrayList.get(position);

        holder.barCode.setText(barcode.barCode);
        holder.packageName.setText(barcode.packageName);
        holder.packageType.setText(barcode.packageType);
        holder.recyclePlace.setText(barcode.packageRecyclePlace);
        holder.status.setText(barcode.activityType);
    }

    @Override
    public int getItemCount() {
        return barcodeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView barCode, packageName, packageType, recyclePlace, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barCode = itemView.findViewById(R.id.barCode);
            packageName = itemView.findViewById(R.id.packageName);
            packageType = itemView.findViewById(R.id.packageType);
            recyclePlace =itemView.findViewById(R.id.recyclePlace);
            status = itemView.findViewById(R.id.status);
        }
    }
}
