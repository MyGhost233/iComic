package com.qiuchenly.comicparse.ViewCreator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

class RectCardView2 extends RectCardView {

    public RectCardView2(@NonNull Context context) {
        super(context);
    }

    public RectCardView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectCardView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getViewHeight(int widthMeasureSpec, int heightMeasureSpec) {
        return widthMeasureSpec / 2;
    }
}