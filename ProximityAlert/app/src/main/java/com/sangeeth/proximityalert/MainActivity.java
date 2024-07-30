package com.sangeeth.proximityalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private LocationManager locationManager;
    private Button btn1;
    private EditText lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    return;
                }
                createPendingIntent();
                try {
                    addProximityAlert(Double.parseDouble(lat.getText().toString()),
                            Double.parseDouble(lon.getText().toString()),
                            100,
                            0,
                            pendingIntent);
                    Toast.makeText(MainActivity.this, "Proximity Alert Set SuccessFully!!",
                            Toast.LENGTH_LONG).show();
                    lat.setText("");
                    lon.setText("");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid Latitude or Longitude", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void createPendingIntent() {
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("message", "You are ");

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);
    }

    public void addProximityAlert(double lattitude, double longitude, float radius, long expiration, PendingIntent pendingIntent) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.addProximityAlert(lattitude, longitude, radius, expiration, pendingIntent);
    }

}