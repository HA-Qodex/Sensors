package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private TextView acceText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;
    private Boolean isFirstTime = false;
    private float prePosX, prePosY, prePosZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        acceText = findViewById(R.id.acceText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorAvailable = true;
        } else {
            acceText.setText("Sensor is not available");
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

        acceText.setText("X: "+decimalFormat.format(prePosX)+" m/s²\nY: "+decimalFormat.format(prePosY)+" m/s²\nZ: "+decimalFormat.format(prePosZ)+" m/s²");

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