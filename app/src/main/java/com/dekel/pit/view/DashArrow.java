package com.dekel.pit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.dekel.pit.R;
import com.dekel.pit.utils.PixTool;


/**
 * Created by dekel laifer on 17-08-2018.
 */

public class DashArrow extends View {

    private Context context;

    private float x1 = 0;
    private float y1 = 0;

    private float x2 = 0;
    private float y2 = 0;
    private Paint paint;
    private Path path;
    private boolean isNeedBezier = false;
    private float dx = PixTool.dip2px(100);

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(PixTool.dip2px(1));
        paint.setAntiAlias(true);
        path = new Path();
    }

    public DashArrow(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        this.context = context;

        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
        init();
    }


    public DashArrow(Context context, View startView, View endView) {
        super(context);
        this.context = context;

        int[] location = new int[2];
        startView.getLocationInWindow(location);

        x1 = location[0];
        y1 = location[1] - PixTool.getStatusBarHeight(context) + startView.getHeight() / 2;

        endView.getLocationInWindow(location);

        x2 = location[0] + endView.getWidth() / 2;
//        y2 = location[1] - PixTool.getStatusBarHeight(context) - 53;
        y2 = location[1] - PixTool.getStatusBarHeight(context) + endView.getHeight() / 2;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(x1, y1);

        if (isNeedBezier) {
            path.lineTo(x1 + dx, y1);
            path.lineTo(x1 + dx, y2);
            path.lineTo(x2, y2);

        } else {
            path.lineTo(x2, y2);
        }

        canvas.drawPath(path, paint);

        float length = 32;
        paint.setStyle(Paint.Style.FILL);
        path.reset();

        if (isNeedBezier) {
            float y = y2 - length / 2;
            path.moveTo(x2, y);
            path.lineTo(x2, y + length);
            path.lineTo(x2 - 28, (y + y + length) / 2);
        } else {
            float x = x2 - length / 2;
            path.moveTo(x, y2);
            path.lineTo(x + length, y2);
            path.lineTo(x2, y2 + 28);
        }
        path.close();

        canvas.drawPath(path, paint);
    }

    public void setNeedBezier(boolean needBezier) {
        isNeedBezier = needBezier;
    }
}

