package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MagnetometerActivity extends AppCompatActivity implements SensorEventListener {

    private TextView magnetoText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;
    private float prePosX, prePosY, prePosZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);

        magnetoText = findViewById(R.id.magnetoText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            isSensorAvailable = true;
        } else {
            magnetoText.setText("Sensor is not available");
            isSensorAvailable = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setMaximumFractionDigits(2);

        prePosX = event.values[0];
        prePosY = event.values[1];
        prePosZ = event.values[2];

        magnetoText.setText("X: "+decimalFormat.format(prePosX)+" μT\n"+"Y: "+decimalFormat.format(prePosY)+" μT\n"+"Z: "+decimalFormat.format(prePosZ)+" μT\n");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorAvailable){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}