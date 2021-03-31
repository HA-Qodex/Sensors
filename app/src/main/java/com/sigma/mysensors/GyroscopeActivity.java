package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GyroscopeActivity extends AppCompatActivity implements SensorEventListener {

    private TextView gyroText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;
    private float prePosX, prePosY, prePosZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        gyroText = findViewById(R.id.gyroText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            isSensorAvailable = true;
        } else {
            gyroText.setText("Sensor is not available");
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
        gyroText.setText("X: "+decimalFormat.format(prePosX)+" rad/s\n"+"X: "+decimalFormat.format(prePosY)+" rad/s\n"+"X: "+decimalFormat.format(prePosZ)+" rad/s");
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