package com.chrisprime.lolcatter.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.chrisprime.lolcatter.interfaces.RandomLolCatFragmentInterface;
import com.chrisprime.lolcatter.utilities.Log;

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

    RandomLolCatFragmentInterface randomLolCatFragmentInterface;

    public DownloadFlickrImageAsyncTask(RandomLolCatFragmentInterface randomLolCatFragmentInterface) {
        this.randomLolCatFragmentInterface = randomLolCatFragmentInterface;
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
        while (randomLolCatFragmentInterface.isLolcatImageAnimating()); //Wait until any current animation on the lolcat image is complete before proceeding; waiting is only OK on a background thread!
        return bitmap;
    }

    protected void onPostExecute(Bitmap flickrImageBitmap) {
        Log.suuv(LOG_TAG, ".onPostExecute(): flickrImageBitmap object was: " + flickrImageBitmap);
        randomLolCatFragmentInterface.onFlickrImageReceived(flickrImageBitmap);
    }
}
