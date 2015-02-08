package com.chrisprime.lolcatter.listeners;

import android.graphics.Bitmap;

/**
 * Created by cpaian on 2/7/15.
 */
public interface OnFlickrDataReceivedListener {
    public void onFlickrFeedDataReceived(String flickrFeedResponseString);

    public void onFlickrImageReceived(Bitmap flickrImageBitmap);
}
