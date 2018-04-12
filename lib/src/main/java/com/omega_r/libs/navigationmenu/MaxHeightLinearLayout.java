package com.omega_r.libs.navigationmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;

import com.omega_r.navigationmenu.R;

public class MaxHeightLinearLayout extends LinearLayoutCompat {

    private int mMaxHeight;

    public MaxHeightLinearLayout(Context context) {
        this(context, null);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attributeset) {
        this(context, attributeset, 0);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        init(attributeset);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaxHeightLinearLayout, 0, 0);
            mMaxHeight = array.getDimensionPixelSize(R.styleable.MaxHeightLinearLayout_android_maxHeight, 0);
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getSize(heightMeasureSpec) > mMaxHeight) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.EXACTLY));
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
