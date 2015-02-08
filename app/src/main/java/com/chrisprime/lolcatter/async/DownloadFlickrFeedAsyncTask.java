package com.chrisprime.lolcatter.async;

import android.os.AsyncTask;


import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;
import com.chrisprime.lolcatter.listeners.OnFlickrDataReceivedListener;
import com.chrisprime.lolcatter.utilities.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DownloadFlickrImageAsyncTask handles downloading an image from a URL asynchronously (NOT on the UI thread)
 * and loading it directly into the provided ImageView control
 * <p/>
 * Created by cpaian on 2/7/15.
 */
public class DownloadFlickrFeedAsyncTask extends AsyncTask<String, Void, List<FlickrFeedItem>> {
    private static final String LOG_TAG = DownloadFlickrFeedAsyncTask.class.getSimpleName();
    OnFlickrDataReceivedListener onFlickrDataReceivedListener;

    public DownloadFlickrFeedAsyncTask(OnFlickrDataReceivedListener onFlickrDataReceivedListener) {
        this.onFlickrDataReceivedListener = onFlickrDataReceivedListener;
    }

    protected List<FlickrFeedItem> doInBackground(String... urls) {
        String responseString = null;
        String urlOfFeed = urls[0];
        try {
            //Open connection and retrieve the response stream (hopefully JSON data!)
            InputStream inputStream = new java.net.URL(urlOfFeed).openStream();

            //Now buffer this stream and read it into a string that we can return and parse into JSON later
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder("");
            String line = "";
            String lineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(lineSeparator);
            }
            bufferedReader.close();
            responseString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, ".doInBackground(" + urlOfFeed + ") MalformedURLException: " + e.getMessage(), e);
        } catch (IOException e) {
            Log.e(LOG_TAG, ".doInBackground(" + urlOfFeed + ") IOException: " + e.getMessage(), e);
        }
        if (Log.isLogLevelAtLeast(Log.LogLevel.SUPER_ULTRA_UBER_VERBOSE)) {
            Log.suuv(LOG_TAG, ".doInBackground(" + urlOfFeed + ") complete, results:\n" + responseString);
        }
        List<FlickrFeedItem> flickrFeedItemList = extractFlickrFeedItemsFromResponseString(responseString);


        return flickrFeedItemList;
    }

    protected void onPostExecute(List<FlickrFeedItem> flickrFeedItemList) {
        onFlickrDataReceivedListener.onFlickrFeedDataReceived(flickrFeedItemList);
    }

    public static List<FlickrFeedItem> extractFlickrFeedItemsFromResponseString(String responseString) {
        //Parse and utilize the JSON object to initialize the pager adapters' fragments
        List<FlickrFeedItem> flickrFeedItemList = null;
        JSONObject jsonObject = null;
        try
        {
            //Trim the nonstandard JSON components from the beginning and ending of the response,
            // if any (with Flickr API, it is definitely not returning valid JSON until inside their nonstandard "jsonFlickrFeed(" tag)
            responseString = trimNonJsonComponentsFromString(responseString);
            jsonObject = new JSONObject(responseString);
            if (Log.isLogLevelAtLeast(Log.LogLevel.SUPER_ULTRA_UBER_VERBOSE)) //Don't even construct the string unless we're logging it, for performance reasons
            {
                Log.suuv(LOG_TAG, ".onCreate(): jsonObject =\n" + jsonObject);
            }

            JSONArray jsonItemsList = jsonObject.getJSONArray("items");
            if (Log.isLogLevelAtLeast(Log.LogLevel.SUPER_ULTRA_UBER_VERBOSE)) //Don't even construct the string unless we're logging it, for performance reasons
            {
                Log.suuv(LOG_TAG, ".onCreate(): jsonItemsList =\n" + jsonItemsList);
            }

            flickrFeedItemList = new ArrayList<>();
            int length = jsonItemsList.length();
            for (int i = 0; i < length; i++)
            {
                JSONObject row = jsonItemsList.getJSONObject(i);
                FlickrFeedItem flickrFeedItem = new FlickrFeedItem();
                flickrFeedItem.title = row.getString("title");
                flickrFeedItem.linkUrl = row.getString("link");
                flickrFeedItem.imageUrl = row.getJSONObject("media").getString("m");
                flickrFeedItemList.add(flickrFeedItem);
                Log.d(LOG_TAG, "jsonItem found: " + flickrFeedItem.title + ", image: " + flickrFeedItem.linkUrl + ", link: "
                        + flickrFeedItem.imageUrl);
            }
        }
        catch (JSONException e)
        {
            //Using SUUV log level as it handles strings longer than the logcat entry limit of 4k
            Log.suuv(LOG_TAG, ".onCreate JSONException from jsonObject creation: " + e.getMessage(), e);
        }
        return flickrFeedItemList;
    }

    public static String trimNonJsonComponentsFromString(String jsonString)
    {
        jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
        return jsonString;
    }
}
