package com.chrisprime.lolcatter.customcontrols;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by cpaian on 2/8/15.
 */
public class FractionallyAnimatableImageView extends ImageView {
    public FractionallyAnimatableImageView(Context context) {
        super(context);
    }

    public FractionallyAnimatableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FractionallyAnimatableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getXFraction() {
        final int width = getWidth();
        if (width != 0) return getX() / getWidth();
        else return getX();
    }

    public void setXFraction(float xFraction) {
        final int width = getWidth();
        float newWidth = (width > 0) ? (xFraction * width) : -9999;
        setX(newWidth);
    }

    public float getYFraction() {
        final int height = getHeight();
        if (height != 0) return getY() / getHeight();
        else return getY();
    }

    public void setYFraction(float yFraction) {
        final int height = getHeight();
        float newHeight = (height > 0) ? (yFraction * height) : -9999;
        setY(newHeight);
    }

}
