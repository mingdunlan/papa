package com.tcmsoso.webapplication.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class BottomBar extends LinearLayout {

    public BottomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBar(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e)  {
        return true;
    }
}
