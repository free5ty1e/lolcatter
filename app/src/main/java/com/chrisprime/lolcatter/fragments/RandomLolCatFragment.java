package com.chrisprime.lolcatter.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.async.DownloadFlickrFeedAsyncTask;
import com.chrisprime.lolcatter.listeners.OnFlickrDataReceivedListener;
import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;
import com.chrisprime.lolcatter.utilities.Log;

import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public class RandomLolCatFragment extends Fragment
        implements OnFlickrDataReceivedListener {
    private static final String LOG_TAG = RandomLolCatFragment.class.getSimpleName();

    private ImageView randomLolCatImageView;
    private TextView randomLolCatTitleTextView;

    public RandomLolCatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_lolcat, container, false);
        randomLolCatImageView = (ImageView) rootView.findViewById(R.id.random_lolcat_imageview);
        randomLolCatTitleTextView = (TextView) rootView.findViewById(R.id.random_lolcat_title);

        randomLolCatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, ".onClick() for randomLolCatImageView");
            }
        });

        randomLolCatTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, ".onClick() for randomLolCatTitleTextView");
            }
        });

        DownloadFlickrFeedAsyncTask downloadFlickrFeedAsyncTask = new DownloadFlickrFeedAsyncTask(this);
        downloadFlickrFeedAsyncTask.execute("http://api.flickr.com/services/feeds/photos_public.gne?tags=lolcat&format=json");


        return rootView;
    }

    @Override
    public void onFlickrFeedDataReceived(List<FlickrFeedItem> flickrFeedItemList) {

    }

    @Override
    public void onFlickrImageReceived(Bitmap flickrImageBitmap) {

    }

}