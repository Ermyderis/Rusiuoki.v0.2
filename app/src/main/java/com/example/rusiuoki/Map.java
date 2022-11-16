package com.example.rusiuoki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 101;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location currentLocationl;
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private ArrayList<LatLng> locationArrayList;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Map.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("RecyclePLaceLocation");

        client = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocationl = location;
                    //Toast.makeText(getApplicationContext(), currentLocationl.getLatitude() + " " + currentLocationl.getLongitude(), Toast.LENGTH_LONG).show();
                    supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
                    supportMapFragment.getMapAsync(Map.this);

                }
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        LatLng latLng = new LatLng(currentLocationl.getLatitude(), currentLocationl.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        locationArrayList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ModelLocation modelLocation = dataSnapshot.getValue(ModelLocation.class);
                    if (modelLocation != null && modelLocation.locationType.toString().equals("Atliekų atsikratymo aišktelė")) {
                        String latitude = modelLocation.locationLatitude.toString().replace(",",".");
                        String longitude = modelLocation.locationLongitude.toString().replace(",",".");
                        LatLng sss = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        locationArrayList.add(sss);
                    }
                }
                for (int i = 0; i < locationArrayList.size(); i++) {
                    // below line is use to add marker to each location of our array list.
                    googleMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}