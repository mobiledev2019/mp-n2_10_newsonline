package com.garenalnews.common;

/**
 * Created by ducth on 3/15/2018.
 */

public interface DrawableClickListener {
    public static enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}

    ;

    public void onClick(DrawablePosition target);
}
