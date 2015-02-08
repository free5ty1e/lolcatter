package com.chrisprime.lolcatter.netclasses;

/**
 * These are the fields we care about extracting from a Flickr feed item.  Other fields are simply ignored.
 */
public class FlickrFeedItem
{
  private String title = "";
  private String imageUrl = "";
  private String linkUrl = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
