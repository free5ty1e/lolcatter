package com.chrisprime.lolcatter.utilities;

import com.chrisprime.lolcatter.LolCatterApplication;
import com.chrisprime.lolcatter.R;
import com.chrisprime.lolcatter.netclasses.FlickrFeedItem;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by cpaian on 2/7/15.
 */
public class FlickrDataUtilitiesTest extends TestCase {
    public FlickrDataUtilitiesTest() {
        super();
    }

    public void testFlickrAlmostJsonResponseParsing() {
        String testFlickrFeedJsonResponse = FlickrDataUtilities.readStringFromResource(
                LolCatterApplication.getInstance().getApplicationContext(),
                R.raw.test_flickr_feed_json_response);
        assertNotNull(testFlickrFeedJsonResponse);
        assertTrue(testFlickrFeedJsonResponse.length() > 0);
        List<FlickrFeedItem> flickrFeedItemList = FlickrDataUtilities.extractFlickrFeedItemsFromResponseString(testFlickrFeedJsonResponse);
        assertNotNull(flickrFeedItemList);
        assertTrue(flickrFeedItemList.size() > 0);
        System.out.println("Flickr Feed Items parsed for test: " + flickrFeedItemList);
    }
}