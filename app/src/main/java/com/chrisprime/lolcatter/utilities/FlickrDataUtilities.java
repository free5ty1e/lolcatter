package com.chrisprime.lolcatter.utilities;

import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public class FlickrDataUtilities {
    private static final String LOG_TAG = FlickrDataUtilities.class.getSimpleName();

    public static List<FlickrFeedItem> extractFlickrFeedItemsFromResponseString(String responseString) {
        //Parse and utilize the JSON object to initialize the pager adapters' fragments
        List<FlickrFeedItem> flickrFeedItemList = null;
        JSONObject jsonObject = null;
        try {
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
            for (int i = 0; i < length; i++) {
                JSONObject row = jsonItemsList.getJSONObject(i);
                FlickrFeedItem flickrFeedItem = new FlickrFeedItem();
                flickrFeedItem.title = row.getString("title");
                flickrFeedItem.linkUrl = row.getString("link");
                flickrFeedItem.imageUrl = row.getJSONObject("media").getString("m");
                flickrFeedItemList.add(flickrFeedItem);
                Log.d(LOG_TAG, "jsonItem found: " + flickrFeedItem.title + ", image: " + flickrFeedItem.linkUrl + ", link: "
                        + flickrFeedItem.imageUrl);
            }
        } catch (JSONException e) {
            //Using SUUV log level as it handles strings longer than the logcat entry limit of 4k
            Log.suuv(LOG_TAG, ".onCreate JSONException from jsonObject creation: " + e.getMessage(), e);
        }
        return flickrFeedItemList;
    }

    public static String trimNonJsonComponentsFromString(String jsonString) {
        jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
        return jsonString;
    }
}
