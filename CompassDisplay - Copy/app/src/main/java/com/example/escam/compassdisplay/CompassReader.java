package com.example.escam.compassdisplay;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class CompassReader extends Activity {

    // define the display assembly compass picture
    //private ImageView arrowView;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    private Compass compass;
    private float currentAzimuth;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_reader);

        setUpCompass();
        //
        //arrowView = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
        // for the system's orientation sensor registered listeners
    }

    @Override
    protected void onPause() {
        super.onPause();
        //compass.stop();
        // to stop the listener and save battery
    }

    @Override
    protected void onStart(){
        super.onStart();
        compass.start();
    }


    public void setUpCompass(){
        compass = new Compass(this);
        Compass.CompassListener cl = new Compass.CompassListener() {

            @Override
            public void onNewAzimuth(float azimuth) {
                adjustArrow(azimuth);
            }
        };
        compass.setListener(cl);
    }
    private void adjustArrow(float azimuth) {
        if(Math.abs(currentAzimuth - azimuth) > 1) {
            Log.d(TAG, "will set rotation from " + currentAzimuth + " to "
                    + azimuth);
            currentAzimuth = azimuth;
            tvHeading.setText("Heading: " + Float.toString(currentAzimuth));
        }
        else{
            Log.d(TAG, "no movement for change of less than one degree");
        }

    }
}
