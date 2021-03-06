package com.dekel.pit.utils;

import android.content.Context;
import android.content.res.Resources;
/**
 * Created by dekel laifer on 17-08-2018.
 */
public class PixTool {
    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
    public static float getStatusBarHeight(Context context) {
        return (float) Math.ceil(25 * context.getResources().getDisplayMetrics().density);
    }
}