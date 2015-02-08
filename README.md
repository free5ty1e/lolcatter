# lolcatter
LOLCatter: A useless, fun Android app (API 16 / v4.1 / JellyBean required) created for an interview tech challenge.  You will see LOLCats.  Because Internet.

![LauncherIcon](app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

### Basic usage:
* Upon loading, the app will grab some of the most recent Flickr posts tagged "lolcat" and display one randomly with a title.
 
* Tapping on the image will load another random lolcat from the current Flickr feed in memory.

* Tapping on the title will launch the default web browser with the current lolcat Flickr post to view in all its original Flickr glory.

* Shaking the phone will now load another random lolcat from the current Flickr feed in memory, and also will play the default camera shutter sound for user feedback.  The shake detector will ignore input for 750ms after user shake is detected to prevent continual shake detections.

* A loading spinner will be present when a feed or image is loading.  I have found performance to be so fast, the loading spinner is barely visible on Lollipop (since it starts small and grows).  However, it is there to indicate general network activity.

* Landscape and portrait rotations are both supported, and switching between the two is non-destructive.

* Tapping on the Preferences icon will bring up a preferences screen, where the user can change the base Flickr feed URL and search tag to their heart's desire.  Can also enable / disable shake detection here.

### TO DO:
* Long-pressing the image will result in toggling visibility of the title textbox, for an unobscured view
* Add animations in from bottom and out from top for random lolcat images and titles
* Current in-memory Flickr feed count and current image index are displayed to the left of the title, (02/15) for example for lolcat #2 out of 15 in the current feed
* Swipe gestures up and down will actually navigate to next and previous lolcat items in the feed (NOT random, in order)
* Basic Android unit tests to validate operation of utility methods

