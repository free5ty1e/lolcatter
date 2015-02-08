package com.chrisprime.lolcatter.utilities;

import android.content.Context;

import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                flickrFeedItem.setTitle(row.getString("title"));
                flickrFeedItem.setLinkUrl(row.getString("link"));
                flickrFeedItem.setImageUrl(row.getJSONObject("media").getString("m"));
                flickrFeedItemList.add(flickrFeedItem);
                Log.d(LOG_TAG, "jsonItem found: " + flickrFeedItem.getTitle()+ ", image: " + flickrFeedItem.getImageUrl() + ", link: "
                        + flickrFeedItem.getLinkUrl());
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

    static public String readStringFromResource(Context context, int resourceID)
    {
        StringBuilder contents = new StringBuilder();
        String sep = System.getProperty("line.separator");

        try
        {
            InputStream is = context.getResources().openRawResource(resourceID);

            BufferedReader input = new BufferedReader(new InputStreamReader(is), 1024 * 8);
            try
            {
                String line = null;
                while ((line = input.readLine()) != null)
                {
                    contents.append(line);
                    contents.append(sep);
                }
            }
            finally
            {
                input.close();
            }
        }
        catch (FileNotFoundException ex)
        {
            Log.e(LOG_TAG, "Couldn't find the file");
            return null;
        }
        catch (IOException ex)
        {
            Log.e(LOG_TAG, "Error reading file");
            return null;
        }

        return contents.toString();
    }

}
