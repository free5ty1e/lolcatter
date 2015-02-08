# lolcatter
LOLCatter: A useless, fun Android app (API 16 / v4.1 / JellyBean required) created for an interview tech challenge.  You will see LOLCats.  Because Internet.

![LauncherIcon](app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

### Basic usage:
Upon loading, the app will grab some of the most recent Flickr posts tagged "lolcat" and display one randomly with a title.

Tapping on the image will load another random lolcat from the current Flickr feed in memory.
Tapping on the title will launch the default web browser with the current lolcat Flickr post to view in all its original Flickr glory.

A loading spinner will be present when a feed or image is loading.  I have found performance to be so fast, the loading spinner is barely visible on Lollipop (since it starts small and grows).  However, it is there to indicate general network activity.

Landscape and portrait rotations are both supported, and switching between the two is non-destructive.


### TO DO:
  * Tapping on the Preferences icon will bring up a preferences screen, where the user can change the base Flickr feed URL and query parameters to their heart's desire.
  * Shake phone for random LOLcat
