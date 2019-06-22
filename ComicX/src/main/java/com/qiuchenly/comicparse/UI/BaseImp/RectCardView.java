package com.qiuchenly.comicparse.UI.BaseImp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

abstract class RectCardView extends CardView {

    public RectCardView(@NonNull Context context) {
        super(context);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取View的高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @return 返回高度
     */
    abstract int getViewHeight(int widthMeasureSpec, int heightMeasureSpec);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getViewHeight(widthMeasureSpec, heightMeasureSpec));
    }
}

