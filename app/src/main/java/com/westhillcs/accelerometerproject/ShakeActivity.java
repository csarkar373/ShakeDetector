package com.westhillcs.accelerometerproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ShakeActivity extends AppCompatActivity implements SensorEventListener{

    private Sensor accelerometer;
    private SensorManager sm;

    private long curTime, lastUpdate;
    private float x, y, z, last_x, last_y, last_z;
    // only allow one update every 300ms.
    private final static long UPDATEPERIOD = 300;
    private static final int SHAKE_THRESHOLD = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        this.initialize();
    }


    private void initialize() {
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        curTime = lastUpdate = (long)0.0;
        x = y = z = last_x = last_y = last_z = (float)0.0;

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > UPDATEPERIOD) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }



}
