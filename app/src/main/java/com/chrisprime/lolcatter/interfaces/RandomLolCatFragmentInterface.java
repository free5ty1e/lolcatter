package com.chrisprime.lolcatter.interfaces;

import android.graphics.Bitmap;

import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;

import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public interface RandomLolCatFragmentInterface {
    public void refreshFlickrFeed();

    public void updateRandomLolCat();

    public boolean isLolcatImageAnimating();

    public void onFlickrFeedDataReceived(List<FlickrFeedItem> flickrFeedItemList);

    public void onFlickrImageReceived(Bitmap flickrImageBitmap);
}
