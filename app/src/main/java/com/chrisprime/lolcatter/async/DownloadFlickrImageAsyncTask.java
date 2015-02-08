package com.chrisprime.lolcatter.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.chrisprime.lolcatter.listeners.OnFlickrDataReceivedListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * DownloadFlickrImageAsyncTask handles downloading an image from a URL asynchronously (NOT on the UI thread)
 * and loading it directly into the provided ImageView control
 * <p/>
 * Created by cpaian on 2/7/15.
 */
public class DownloadFlickrImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private static final String LOG_TAG = DownloadFlickrImageAsyncTask.class.getSimpleName();

    ImageView bmImage;
    OnFlickrDataReceivedListener onFlickrDataReceivedListener;

    public DownloadFlickrImageAsyncTask(ImageView bmImage, OnFlickrDataReceivedListener onFlickrDataReceivedListener) {
        this.bmImage = bmImage;
        this.onFlickrDataReceivedListener = onFlickrDataReceivedListener;
    }

    protected Bitmap doInBackground(String... urls) {
        String imageUrl = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, ".doInBackground(" + imageUrl + ") IOException: " + e.getMessage(), e);
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap flickrImageBitmap) {
        Log.v(LOG_TAG, ".onPostExecute(): flickrImageBitmap object was: " + flickrImageBitmap);
        bmImage.setImageBitmap(flickrImageBitmap);
        onFlickrDataReceivedListener.onFlickrImageReceived(flickrImageBitmap);
    }
}
