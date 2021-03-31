package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    private TextView lightText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        lightText = findViewById(R.id.lightText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isSensorAvailable = true;
        } else {
            lightText.setText("Sensor is not available");
            isSensorAvailable = false;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setMaximumFractionDigits(2);

        lightText.setText("Value: "+decimalFormat.format(event.values[0])+" lux");
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
        if (isSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}