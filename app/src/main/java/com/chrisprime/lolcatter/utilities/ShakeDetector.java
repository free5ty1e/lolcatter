package com.chrisprime.lolcatter.utilities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: cpaian
 * Date: 6/6/14
 * Time: 6:54 PM
 */
public class ShakeDetector {
    private static final String LOG_TAG = ShakeDetector.class.getSimpleName();

    public interface ShakeDetectorListener {
        void onShake();
    }

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private float shakeDetectionThreshold = 12.0f;
    private long requiredTimeoutBeforeNextAllowedShakeInMillis = 750;
    private ShakeDetectorListener shakeListener = null;

    private boolean shakeDetectionDisabledForTimeout = false;

    public ShakeDetector(Activity activity) {
        super();
        mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            //Shake detection processing is now complete
            if (mAccel > shakeDetectionThreshold) {
                if (!shakeDetectionDisabledForTimeout) {
                    shakeDetectionDisabledForTimeout = true;
                    onShake();
                    restoreShakeDetectionAfterDelay();
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void onShake() {
        Log.v(LOG_TAG, "Device has shaken.");
        if (shakeListener != null) {
            shakeListener.onShake();
        }
    }

    public void resumeListener() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pauseListener() {

        mSensorManager.unregisterListener(mSensorListener);
    }

    private void restoreShakeDetectionAfterDelay() {
        //Restore shake detection after delay to prevent accidentally taking multiple screenshots:
        ShakeSettlingTimeoutResetAsyncTask shakeSettlingTimeoutResetAsyncTask = new ShakeSettlingTimeoutResetAsyncTask();
        shakeSettlingTimeoutResetAsyncTask.execute();
    }

    private class ShakeSettlingTimeoutResetAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(requiredTimeoutBeforeNextAllowedShakeInMillis);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            shakeDetectionDisabledForTimeout = false; //Now reset and reenable the shake detection
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public long getRequiredTimeoutBeforeNextAllowedShakeInMillis() {
        return requiredTimeoutBeforeNextAllowedShakeInMillis;
    }

    public void setRequiredTimeoutBeforeNextAllowedShakeInMillis(long requiredTimeoutBeforeNextAllowedShakeInMillis) {
        this.requiredTimeoutBeforeNextAllowedShakeInMillis = requiredTimeoutBeforeNextAllowedShakeInMillis;
    }

    public float getShakeDetectionThreshold() {
        return shakeDetectionThreshold;
    }

    public void setShakeDetectionThreshold(float shakeDetectionThreshold) {
        this.shakeDetectionThreshold = shakeDetectionThreshold;
    }

    public ShakeDetectorListener getShakeListener() {
        return shakeListener;
    }

    public void setShakeListener(ShakeDetectorListener shakeListener) {
        this.shakeListener = shakeListener;
    }

    public boolean isShakeDetectionDisabledForTimeout() {
        return shakeDetectionDisabledForTimeout;
    }

    public void setShakeDetectionDisabledForTimeout(boolean shakeDetectionDisabledForTimeout) {
        this.shakeDetectionDisabledForTimeout = shakeDetectionDisabledForTimeout;
    }
}
