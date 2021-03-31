package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private TextView proxyText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proxyText = findViewById(R.id.proxyText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isSensorAvailable = true;
        } else {
            proxyText.setText("Sensor is not available");
            isSensorAvailable = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proxyText.setText("Value: "+event.values[0]+" cm");

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