package com.chrisprime.lolcatter.utilities;

import java.util.Random;

/**
 * Created by cpaian on 2/7/15.
 */
public class RandomUtilities {
    private static final String LOG_TAG = RandomUtilities.class.getSimpleName();
    private static final Random random = new Random();

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randomIntBetween(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomInt = random.nextInt((max - min) + 1) + min;
        Log.suuv(LOG_TAG, ".randomIntBetween(" + min + ", " + max + ") = " + randomInt);
        return randomInt;
    }
}
