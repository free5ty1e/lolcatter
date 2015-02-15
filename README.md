# lolcatter
LOLCatter: A useless, fun Android app (API 16 / v4.1 / JellyBean required) created for an interview tech challenge.  You will see LOLCats.  Because Internet.

#### Built with Android Studio v1.1b4

![LauncherIcon](app/src/main/res/mipmap-xxhdpi/ic_launcher.png)

### Download prebuilt APK
https://www.dropbox.com/s/k4hch8z5g0vwsfj/lolcatter-debug.apk.zip?dl=0

### Given app requirements
Here is the list of requirements I was given for this tech challenge.  This project is what I ended up with.
* Given that I am a user that has an Android phone or tablet. 
* When I touch the screen in the area that looks like a rectangle or picture frame. 
* Then the rectangle or picture frame loads with an image of a random LoLcat loaded from Flickr.
 
* Button / touch area on screen can be a simple rectangle (minimum 200x400)
* Asynchronous loading of the image over the network
* Provides user with loading status indicator
* Supports rotation
* Loads LoLcats from Flickr
* This application is meant to demonstrate a familiarity with core design patterns for both Android and Java. Due to this goal, the application should be written without the use of external libraries. (this is why I am not using Robolectric for the unit testing, by the way, which is the only external dependency I'd like to add)


### Basic usage:
* Upon loading, the app will grab some (20) of the most recent Flickr posts tagged "lolcat" and display one randomly with a title.
 
* Tapping on the image will load another random lolcat from the current Flickr feed in memory.

* When a new lolcat is requested, the old lolcat image will animate down to the bottom and off the screen, and the new one will animate down from the top and onto the screen.

* Tapping on the title will launch the default web browser with the current lolcat Flickr post to view in all its original Flickr glory.

* Shaking the phone will now load another random lolcat from the current Flickr feed in memory, and also will play the default camera shutter sound for user feedback.  The shake detector will ignore input for 750ms after user shake is detected to prevent continual shake detections.

* A loading spinner will be present when a feed or image is loading.  I have found performance to be so fast, the loading spinner is barely visible on Lollipop (since it starts small and grows).  However, it is there to indicate general network activity.

* Landscape and portrait rotations are both supported, and switching between the two is non-destructive.

* Tapping on the Preferences icon will bring up a preferences screen, where the user can change the base Flickr feed URL and search tag to their heart's desire.  Can also enable / disable shake detection here.

* Long-pressing the image will result in toggling visibility of the title textbox, for an unobscured view.  This sticks until the user long-presses a lolcat image again.

* Current in-memory Flickr feed count and current image index are displayed to the left of the title, (02/15) for example for lolcat #2 out of 15 in the current feed

* Includes Basic Android unit tests to validate operation of utility methods


### TO DO:
* Swipe gestures up and down will actually navigate to next and previous lolcat items in the feed (NOT random, in order)
* Add user setting to control shake detection settling timeout (750ms default)
* Look into other Flickr REST API calls that could potentially result in a more diverse, larger pool for the random lolcat (currently we retrieve a feed of the most recent 20 lolcat-tagged Flickr posts)
* Support direct launching of the Flickr app (if installed), or provide link to Play Store to download Flickr app so that tapping post title can also open post in Flickr app directly (demonstrates content URI usage)

