package com.sigma.mysensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tempText;
    private Sensor sensor;
    private SensorManager sensorManager;
    private Boolean isSensorAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tempText = findViewById(R.id.tempText_Id);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorAvailable = true;
        } else {
            tempText.setText("Sensor is not available");
            isSensorAvailable = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tempText.setText("Value: "+event.values[0]+" ◦C");
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