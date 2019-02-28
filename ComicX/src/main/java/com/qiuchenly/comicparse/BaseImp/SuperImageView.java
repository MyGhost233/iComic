package com.qiuchenly.comicparse.BaseImp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class SuperImageView extends android.support.v7.widget.AppCompatImageView {
    public SuperImageView(Context context) {
        super(context);
    }

    public SuperImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
    }
}
