package com.sangeeth.gpslocation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private Button button;
    private Geocoder geocoder;

    private TextView area, pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn1);
        geocoder = new Geocoder(this, Locale.getDefault());
        area = findViewById(R.id.locality);
        pin = findViewById(R.id.pincode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, MainActivity.this);

            }
        });


    }

    public void onLocationChanged(Location location){
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String message = "Latitude: " + latitude + ", Longitude: " + longitude;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
                if (addresses.size()>0 && addresses!=null){
                    Address address = addresses.get(0);
                    String locality = address.getLocality();
                    String pincode = address.getPostalCode();
                    area.setText("Locality : "+locality);
                    pin.setText("Pincode : "+pincode);
                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error when getting location", Toast.LENGTH_SHORT).show();
            }

        }
    }


}