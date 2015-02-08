package com.chrisprime.lolcatter.listeners;

import android.graphics.Bitmap;

import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;

import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public interface OnFlickrDataReceivedListener {
    public void onFlickrFeedDataReceived(List<FlickrFeedItem> flickrFeedResponseString);

    public void onFlickrImageReceived(Bitmap flickrImageBitmap);
}
