package com.chrisprime.lolcatter.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.fragments.RandomLolCatFragment;
import com.chrisprime.lolcatter.fragments.SettingsFragment;
import com.chrisprime.lolcatter.listeners.RandomLolCatFragmentUpdateInterface;
import com.chrisprime.lolcatter.utilities.Log;
import com.chrisprime.lolcatter.utilities.PreferenceUtilities;
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setIcon(R.mipmap.ic_launcher);
        }
        else
        {
            Log.d(LOG_TAG, ".onCreate(): actionBar is null!");
        }
        shakeDetector = new ShakeDetector(this);
        shakeDetector.setShakeListener(this);

        if (savedInstanceState == null) {
            navigateToNewMainFragment(new RandomLolCatFragment(), false);
        }
    }

    private void navigateToNewMainFragment(Fragment fragment, boolean addToBackstack) {
        mainFragment = fragment;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, mainFragment, mainFragment.getClass().getSimpleName());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null).commit();
        }
        else {
            fragmentTransaction.commit();
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
                navigateToNewMainFragment(new SettingsFragment(), true);
                ret = true;
                break;
            case R.id.action_refresh:
                Log.v(LOG_TAG, ".onOptionsItemSelected(): Refresh Feed item selected.");
                if (mainFragment != null && mainFragment instanceof RandomLolCatFragmentUpdateInterface) {
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
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onShake() {
        if (PreferenceUtilities.isShakeDetectionEnabled()) {
            if (mainFragment != null && mainFragment instanceof RandomLolCatFragmentUpdateInterface) {
                SoundPlayer.getInstance().playShutterSound(this);
                ((RandomLolCatFragmentUpdateInterface) mainFragment).updateRandomLolCat();
            }
        }
    }

    @Override
    protected void onResume() {
        shakeDetector.resumeListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        shakeDetector.pauseListener();
        super.onPause();
    }
}
