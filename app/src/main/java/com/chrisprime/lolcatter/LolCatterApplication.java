package com.chrisprime.lolcatter;

import android.app.Application;

public class LolCatterApplication extends Application {
    @SuppressWarnings("unused")
    private final static String LOG_TAG = LolCatterApplication.class.getSimpleName();

    private static LolCatterApplication instance;

    //This only gets started by the os so my singleton looks a little weird for this class
    public LolCatterApplication() {
        super();
        instance = this;
    }

    public static LolCatterApplication getInstance() {
        return instance;
    }

    public static void killApp(boolean killSafely) {
        if (killSafely) {
            System.runFinalization();
            System.exit(0);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
