package com.omega_r.omeganavigationmenu.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;

import com.omega_r.omeganavigationmenu.R;

public class StyledToolbar extends Toolbar {

    public StyledToolbar(Context context) {
        this(context, null);
    }

    public StyledToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public StyledToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(attrs == null ? context
                :  new ContextThemeWrapper(context, getResourceId(context, attrs, defStyleAttr,
                R.styleable.StyledToolbar_toolbarTheme)), attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    private static int getResourceId(Context context, @NonNull AttributeSet attrs, int defStyleAttr,
                                     int index ) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StyledToolbar,
                defStyleAttr, 0);
        int result = array.getResourceId(index, 0);
        array.recycle();
        return result;
    }

}
