package com.chrisprime.lolcatter.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.utilities.Log;

/**
 * Created with IntelliJ IDEA.
 * User: CPAIAN
 * Date: 11/15/13
 * Time: 4:18 PM
 */
public class SettingsFragment extends PreferenceFragment {
    private final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, ".onCreate()");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    //Convenience methods for getting preference by keyResourceId or string key
    private Object getPref(int prefKeyResourceId) {
        return getPref(getString(prefKeyResourceId));

    }

    private Object getPref(String prefKey) {
        return getPreferenceManager().findPreference(prefKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
