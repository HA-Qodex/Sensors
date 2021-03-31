package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView accelerometer, proximity, temperature, light, gyro, magneto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometer = findViewById(R.id.accelerometer_Id);
        proximity = findViewById(R.id.proximity_Id);
        temperature = findViewById(R.id.temperature_Id);
        light = findViewById(R.id.lightSensor_Id);
        gyro = findViewById(R.id.gyro_Id);
        magneto = findViewById(R.id.magnetoSensor_Id);

        userPermission();


        accelerometer.setOnClickListener(this);
        proximity.setOnClickListener(this);
        temperature.setOnClickListener(this);
        light.setOnClickListener(this);
        gyro.setOnClickListener(this);
        magneto.setOnClickListener(this);

    }

    private void userPermission(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACTIVITY_RECOGNITION
                       )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (!report.areAllPermissionsGranted()) {
                        } else {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.accelerometer_Id:
                startActivity(new Intent(getApplicationContext(), AccelerometerActivity.class));
                break;
            case R.id.proximity_Id:
                startActivity(new Intent(getApplicationContext(), ProximityActivity.class));
                break;
            case R.id.temperature_Id:
                startActivity(new Intent(getApplicationContext(), TemperatureActivity.class));
                break;
            case R.id.lightSensor_Id:
                startActivity(new Intent(getApplicationContext(), LightActivity.class));
                break;

            case R.id.gyro_Id:
                startActivity(new Intent(getApplicationContext(), GyroscopeActivity.class));
                break;

            case R.id.magnetoSensor_Id:
                startActivity(new Intent(getApplicationContext(), MagnetometerActivity.class));
                break;
        }
    }
}