package com.chrisprime.lolcatter.async;

import android.os.AsyncTask;


import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;
import com.chrisprime.lolcatter.listeners.OnFlickrDataReceivedListener;
import com.chrisprime.lolcatter.utilities.FlickrDataUtilities;
import com.chrisprime.lolcatter.utilities.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
        List<FlickrFeedItem> flickrFeedItemList = FlickrDataUtilities.extractFlickrFeedItemsFromResponseString(responseString);


        return flickrFeedItemList;
    }

    protected void onPostExecute(List<FlickrFeedItem> flickrFeedItemList) {
        onFlickrDataReceivedListener.onFlickrFeedDataReceived(flickrFeedItemList);
    }

}
