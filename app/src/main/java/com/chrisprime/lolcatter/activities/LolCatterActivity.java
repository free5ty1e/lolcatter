package com.chrisprime.lolcatter.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.Menu;
import android.view.MenuItem;

import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.fragments.RandomLolCatFragment;
import com.chrisprime.lolcatter.listeners.RandomLolCatFragmentUpdateInterface;
import com.chrisprime.lolcatter.utilities.Log;
import com.chrisprime.lolcatter.utilities.ShakeDetector;
import com.chrisprime.lolcatter.utilities.SoundPlayer;

/**
 * Created by cpaian on 2/7/15.
 */
public class LolCatterActivity extends ActionBarActivity
    implements ShakeDetector.ShakeDetectorListener {
private static final String LOG_TAG = LolCatterActivity.class.getSimpleName();
    private Fragment mainFragment;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol_catter);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.setShakeListener(this);

        if (savedInstanceState == null) {
            mainFragment = new RandomLolCatFragment();
            getFragmentManager().beginTransaction().add(R.id.container, mainFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lol_catter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean ret;
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Log.v(LOG_TAG, ".onOptionsItemSelected(): Settings item selected.");
                ret = true;
                break;
            case R.id.action_refresh:
                Log.v(LOG_TAG, ".onOptionsItemSelected(): Refresh Feed item selected.");
                if (mainFragment != null && mainFragment instanceof RandomLolCatFragmentUpdateInterface)
                {
                    ((RandomLolCatFragmentUpdateInterface) mainFragment).refreshFlickrFeed();
                }
                ret = true;
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }

    @Override
    public void onShake() {
        if (mainFragment != null && mainFragment instanceof RandomLolCatFragmentUpdateInterface)
        {
            SoundPlayer.getInstance().playShutterSound(this);
            ((RandomLolCatFragmentUpdateInterface) mainFragment).updateRandomLolCat();
        }
    }

    @Override
    protected void onResume()
    {
        shakeDetector.resumeListener();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        shakeDetector.pauseListener();
        super.onPause();
    }
}
