package com.rya.litmarket.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.nio.channels.Selector;

/**
 * Created by ryanyans32 on 2017/3/19.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class DrawableUtil {

    public static Drawable getGradientDrawable(int color, int radius) {

        GradientDrawable shape = new GradientDrawable();

        shape.setShape(GradientDrawable.RECTANGLE); // 矩形
        shape.setCornerRadius(radius);
        shape.setColor(color);

        return shape;
    }

    public static StateListDrawable getSelector(Drawable nomalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{}, nomalDrawable);

        return stateListDrawable;
    }
}
