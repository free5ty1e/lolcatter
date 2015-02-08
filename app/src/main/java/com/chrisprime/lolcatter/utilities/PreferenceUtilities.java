package com.chrisprime.lolcatter.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chrisprime.lolcatter.LolCatterApplication;
import com.chrisprime.lolcatter.R;

/**
 * Created with IntelliJ IDEA.
 * User: CPAIAN
 * Date: 10/11/13
 * Time: 1:05 PM
 */
public class PreferenceUtilities {
    private static final String TAG = PreferenceUtilities.class.getSimpleName();
    static String password = "";

    public static Context getContext() {
        return LolCatterApplication.getInstance().getApplicationContext();
    }

    /**
     * @return usable (SharedPreferences) SharedPreferences object that contains preferences relevant to our application
     * @should Return a valid SharedPreferences object
     */
    public static SharedPreferences getSharedPreferences() {
        //Use the global application context here whenever retrieving the preferences object, as activity-specific context becomes invalid during navigation and is not guaranteed!
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    /**
     * Overload for passing a hard-coded or empty string to avoid an unnecessary second resource lookup
     *
     * @param resourceId
     * @param defaultValueString
     * @return
     */
    public static String getPreference(int resourceId, String defaultValueString) {
        return getSharedPreferences().getString(getContext().getString(resourceId), defaultValueString);
    }

    /**
     * @param resourceId                   (int) Resource ID of preference (example: R.string.pref_key_updates_interval)
     * @param defaultValueStringResourceId (int) Resource ID of string: Default value if preference does not yet exist
     * @return (String) Preference value
     * @should Return the requested preference value
     */
    public static String getPreference(int resourceId, int defaultValueStringResourceId) {
        return getSharedPreferences()
                .getString(getContext().getString(resourceId), getContext().getString(defaultValueStringResourceId));
    }

    /**
     * @param resourceId                   The resource id that represents the key for the shared preference.
     * @param defaultValueStringResourceId The resource id the reprsents the default value to return if not set
     * @param keyArrayResourceId           the array of keys
     * @param valueArrayResourceId         the array of values
     * @return
     */
    public static String getListPreferenceItemText(int resourceId, int defaultValueStringResourceId,
                                                   int keyArrayResourceId, int valueArrayResourceId) {
        SharedPreferences defaultSharedPreferences = getSharedPreferences();
        String prefValue = defaultSharedPreferences
                .getString(getContext().getString(resourceId), getContext().getString(defaultValueStringResourceId));
        // the arrays used by the ListPreference
        CharSequence[] keys = getContext().getResources().getTextArray(keyArrayResourceId);
        CharSequence[] values = getContext().getResources().getTextArray(valueArrayResourceId);
        // loop and find index...
        int len = values.length;
        for (int i = 0; i < len; i++) {
            if (values[i].equals(prefValue)) {
                return (String) keys[i];
            }
        }
        return null;
    }

    public static void setPreference(int resourceIdForPrefKey, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceUtilities.getSharedPreferences();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(getContext().getString(resourceIdForPrefKey), preferenceValue);
        prefsEditor.apply();
    }

    public static void setPreference(int resourceIdForPrefKey, Long preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceUtilities.getSharedPreferences();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putLong(getContext().getString(resourceIdForPrefKey), preferenceValue);
        prefsEditor.apply();
    }

    /**
     * @param resourceId   Resource ID of preference (example: R.string.pref_key_updates_interval)
     * @param defaultValue default value to return if it isn't set.
     * @return the value for the preference
     */
    public static boolean getCheckPreference(int resourceId, boolean defaultValue) {
        return getSharedPreferences().getBoolean(getContext().getString(resourceId), defaultValue);
    }

    /**
     * @param resourceId             (int) Resource ID of preference (example: R.string.pref_key_updates_interval)
     * @param defaultValueResourceId (int) Resource ID of string: Default value if preference does not yet exist
     * @return (boolean) Preference value
     * @should Return the requested preference value
     */
    public static boolean getCheckPreference(int resourceId, int defaultValueResourceId) {
        return getCheckPreference(resourceId, getContext().getResources().getBoolean(defaultValueResourceId));
    }

    public static Boolean getBooleanFromXml(int defaultValueStringResourceId) {
        return Boolean.valueOf(getContext().getString(defaultValueStringResourceId));
    }

    public static float getFloatPreference(int resourceId, int defaultValueFloatStringResourceId) {
        return getFloatPreference(resourceId, getContext().getString(defaultValueFloatStringResourceId));
    }

    public static float getFloatPreference(int resourceId, String defaultValue) {
        return getFloatPreference(resourceId, floatFromString(defaultValue, null));
    }

    public static float getFloatPreference(int resourceId, float defaultValue) {
        String asString = getSharedPreferences()
                .getString(getContext().getString(resourceId), String.valueOf(defaultValue));
        return floatFromString(asString, defaultValue);
    }

    public static Float floatFromString(String floatString, Float defaultReturnFloatValue) {
        Float results = defaultReturnFloatValue;
        try {
            results = Float.valueOf(floatString);
        } catch (Exception e) {
            Log.e(PreferenceUtilities.TAG, floatString + " is not a float.  Returning default: " + defaultReturnFloatValue);
        }
        return results;
    }

    public static int getIntPreference(int resourceId, int defaultValue) {
        int results = defaultValue;
        String asString = getSharedPreferences()
                .getString(getContext().getString(resourceId), String.valueOf(defaultValue));
        try {
            results = Integer.valueOf(asString);
        } catch (Exception e) {
            Log.d(PreferenceUtilities.TAG, asString + " is not a int");
        }
        return results;
    }

    public static void setCheckPreference(int resourceIdForPrefKey, boolean preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceUtilities.getSharedPreferences();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(getContext().getString(resourceIdForPrefKey), preferenceValue);
        prefsEditor.apply();
    }

    public static long getLongPreference(int prefKeyResourceId, long defaultValue) {
        return getSharedPreferences()
                .getLong(getContext().getResources().getString(prefKeyResourceId), defaultValue);
    }

    public static void setLongPreference(int prefKeyResourceId, long value) {
        Context context = getContext();
        SharedPreferences sharedPreferences = PreferenceUtilities.getSharedPreferences();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putLong(context.getString(prefKeyResourceId), value);
        prefsEditor.apply();
    }

    public static boolean isShakeDetectionEnabled() {
        return getCheckPreference(R.string.pref_key_shake_detection_enabled,
                R.bool.pref_default_value_shake_detection_enabled);
    }

    public static String getFlickrFeedBaseUrl() {
        return getPreference(R.string.pref_key_flickr_feed_base_url, R.string.pref_default_value_flickr_feed_base_url);
    }

    public static String getFlickrFeedSearchTag() {
        return getPreference(R.string.pref_key_flickr_feed_search_tag, R.string.pref_default_value_flickr_feed_search_tag);
    }
}
