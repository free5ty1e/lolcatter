package com.chrisprime.lolcatter.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.async.DownloadFlickrFeedAsyncTask;
import com.chrisprime.lolcatter.async.DownloadFlickrImageAsyncTask;
import com.chrisprime.lolcatter.listeners.OnFlickrDataReceivedListener;
import com.chrisprime.lolcatter.listeners.RandomLolCatFragmentUpdateInterface;
import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;
import com.chrisprime.lolcatter.utilities.Log;
import com.chrisprime.lolcatter.utilities.PreferenceUtilities;
import com.chrisprime.lolcatter.utilities.RandomUtilities;

import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public class RandomLolCatFragment extends Fragment
        implements OnFlickrDataReceivedListener,
        RandomLolCatFragmentUpdateInterface {
    private static final String LOG_TAG = RandomLolCatFragment.class.getSimpleName();

    private ImageView randomLolCatImageView;
    private TextView randomLolCatTitleTextView;
    private ProgressBar randomLolCatProgressBar;
    
    private List<FlickrFeedItem> flickrFeedItemList;
    private FlickrFeedItem currentFlickrFeedItem;
    private int randomlySelectedFlickrFeedItemIndex;

    private boolean randomLolCatTitleVisible = false;

    public RandomLolCatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_random_lolcat, container, false);
        randomLolCatImageView = (ImageView) rootView.findViewById(R.id.random_lolcat_imageview);
        randomLolCatTitleTextView = (TextView) rootView.findViewById(R.id.random_lolcat_title);
        randomLolCatProgressBar = (ProgressBar) rootView.findViewById(R.id.random_lolcat_progressbar);

        randomLolCatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, ".onClick() for randomLolCatImageView");
                updateRandomLolCat();
            }
        });

        randomLolCatImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(LOG_TAG, ".onLongClick() for randomLolCatImageView");
                toggleTitleVisibility();
                return true;
            }
        });

        randomLolCatTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, ".onClick() for randomLolCatTitleTextView");
                if (currentFlickrFeedItem != null)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentFlickrFeedItem.getLinkUrl()));
                    startActivity(browserIntent);
                }
            }
        });

        refreshFlickrFeed();
        return rootView;
    }

    @Override
    public void refreshFlickrFeed() {
        randomLolCatProgressBar.setVisibility(View.VISIBLE);
        DownloadFlickrFeedAsyncTask downloadFlickrFeedAsyncTask = new DownloadFlickrFeedAsyncTask(this);
        downloadFlickrFeedAsyncTask.execute(PreferenceUtilities.getFlickrFeedBaseUrl() + PreferenceUtilities.getFlickrFeedSearchTag());
    }

    @Override
    public void updateRandomLolCat() {
        if (flickrFeedItemList != null && flickrFeedItemList.size() > 0)
        {
            //show loading spinner to indicate activity
            randomLolCatProgressBar.setVisibility(View.VISIBLE);

            nextDifferentRandomFlickrFeedIndex();

            currentFlickrFeedItem = flickrFeedItemList.get(randomlySelectedFlickrFeedItemIndex);

            //Update title immediately so the user can see what is being loaded
            randomLolCatTitleTextView.setText(currentFlickrFeedItem.getTitle());

            //Start background download of image
            DownloadFlickrImageAsyncTask downloadFlickrImageAsyncTask = new DownloadFlickrImageAsyncTask(this, randomLolCatImageView);
            downloadFlickrImageAsyncTask.execute(currentFlickrFeedItem.getImageUrl());
        }
    }

    private void nextDifferentRandomFlickrFeedIndex() {
        int previousRandomIndex = randomlySelectedFlickrFeedItemIndex;
        setNextRandomFlickrFeedIndex();
        if (flickrFeedItemList.size() > 1)  //If more than one image was returned, we should ensure we don't end up with the same random image as last time
        {
            while(randomlySelectedFlickrFeedItemIndex == previousRandomIndex)
            {
                setNextRandomFlickrFeedIndex();
            }
        }
    }

    private void setNextRandomFlickrFeedIndex() {
        randomlySelectedFlickrFeedItemIndex = RandomUtilities.randomIntBetween(0, flickrFeedItemList.size() - 1);
    }

    @Override
    public void onFlickrFeedDataReceived(List<FlickrFeedItem> flickrFeedItemList) {
        this.flickrFeedItemList = flickrFeedItemList;
        updateRandomLolCat();
    }

    @Override
    public void onFlickrImageReceived(Bitmap flickrImageBitmap) {
        randomLolCatProgressBar.setVisibility(View.GONE);
        lolcatTitleVisible(true);
    }

    private void lolcatTitleVisible(boolean visible) {
        randomLolCatTitleTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        randomLolCatTitleVisible = visible;
    }

    private void toggleTitleVisibility() {
        lolcatTitleVisible(!randomLolCatTitleVisible);
    }
}