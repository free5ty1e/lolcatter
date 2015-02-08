package com.chrisprime.lolcatter.utilities;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created with IntelliJ IDEA.
 * User: cpaian
 * Date: 6/6/14
 * Time: 11:31 PM
 */
public class SoundPlayer {
    @SuppressWarnings("unused")
    private static final String LOG_TAG = SoundPlayer.class.getSimpleName();

    private MediaPlayer mediaPlayerForShutterSound;
    private static volatile SoundPlayer instance = null;

    private SoundPlayer() {
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            synchronized (SoundPlayer.class) {
                if (instance == null) {
                    instance = new SoundPlayer();
                }
            }
        }
        return instance;
    }

    public void playShutterSound(Context context) {
        AudioManager meng = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volume = meng.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        if (volume != 0) {
            if (mediaPlayerForShutterSound == null) {
                mediaPlayerForShutterSound = MediaPlayer.create(context, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            }
            if (mediaPlayerForShutterSound != null) {
                mediaPlayerForShutterSound.start();
            }
        }
    }
}
