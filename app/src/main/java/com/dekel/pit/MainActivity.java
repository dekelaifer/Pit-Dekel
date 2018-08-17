package com.dekel.pit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dekel.pit.utils.PixTool;
import com.dekel.pit.view.DashArrow;
import com.dekel.pit.view.DragViewGroup;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by dekel laifer on 17-08-2018.
 */

public class MainActivity extends AppCompatActivity {

    private static DragViewGroup dragViewGroup;
    private  static ArrayList<DashArrow> dashArrowArrayList = new ArrayList<DashArrow>();
    private static Context context;
    private static String cmd = "abcdefghijklmnopqrstuvwxyz";
    private  static int MaxPoint=cmd.length();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        context = MainActivity.this;
        dragViewGroup = findViewById(R.id.dragViewGroup);
        final ImageView circle = findViewById(R.id.ivCircle);
        final ImageView ivAddCircle = findViewById(R.id.ivAddCircle);
        final ImageView btnClear = findViewById(R.id.btnClear);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle.setVisibility(View.INVISIBLE);
                btnClear.setVisibility(View.VISIBLE);
                ivAddCircle.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    createView(view, R.drawable.circle, i,true);

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawLine();
                    }
                }, 5);
            }
        });

        ivAddCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createView(view, R.drawable.circle, 0,false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawLine();
                    }
                }, 5);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle.setVisibility(View.VISIBLE);
                btnClear.setVisibility(View.INVISIBLE);
                ivAddCircle.setVisibility(View.INVISIBLE);
                dragViewGroup.removeAllViews();
                dragViewGroup.mMoveLayoutList.clear();
            }
        });

    }

    public static void drawLine() {

        for (DashArrow dashArrow : dashArrowArrayList) {
            dragViewGroup.removeView(dashArrow);
        }
        dashArrowArrayList.clear();
        int size = dragViewGroup.mMoveLayoutList.size();

        if (size < 2) {
            showShortToast(context.getResources().getString(R.string.less_than_two));
            return;
        }

        char[] chars = cmd.toCharArray();
        DashArrow dashArrow;
        float statusBarHeight = PixTool.getStatusBarHeight(context);
        for (int i = 0; i < Math.min(chars.length, size) - 1; i++) {
            int index1 = chars[i] - 'a';
            int index2 = chars[i + 1] - 'a';
            View childAt1 = dragViewGroup.mMoveLayoutList.get(index1);
            View childAt2 = dragViewGroup.mMoveLayoutList.get(index2);
            dashArrow = new DashArrow(context,
                    childAt1.getLeft() + childAt1.getWidth() / 2,
                    childAt1.getBottom() - statusBarHeight,
                    childAt2.getLeft() + childAt1.getWidth() / 2, childAt2.getBottom() - statusBarHeight);
            dragViewGroup.addView(dashArrow);
            dashArrowArrayList.add(dashArrow);
        }
    }
    private void createView(View view, int ResId, int num, boolean start) {
        int size = dragViewGroup.mMoveLayoutList.size();

        if (size==MaxPoint){
            showShortToast(context.getResources().getString(R.string.cant_add_more_point));
            return;
        }
        ImageView imageView = new ImageView(view.getContext());
        imageView.setImageResource(ResId);
        //DragView
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
        imageView.setLayoutParams(p);
        if (start) {
            dragViewGroup.addDragView(imageView, view.getLeft() + (view.getWidth() * num), view.getTop(), view.getRight(), view.getBottom()
                    , true, false);
        } else {
            dragViewGroup.addDragView(imageView, dragViewGroup.getWidth() / 2, view.getTop(),  view.getRight(), view.getBottom() , true, false);
        }
    }
    private static void showShortToast(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}