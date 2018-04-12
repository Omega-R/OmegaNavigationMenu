package com.omega_r.libs.navigationmenu.tools;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import com.omega_r.navigationmenu.R;

public class MenuAnimation extends Animation {

    private static final int MAX_DURATION = 3000;

    private View mContentView;
    private final float mScaleCoef;
    private final float mTranslationCoef;
    private float mScaleXDiff;
    private float mScaleYDiff;
    private float mTranslateXDiff;
    private float mScaleXStart;
    private float mScaleYStart;
    private float mTranslateXStart;
    private final float mMaxTranslationZ;

    private boolean mInit = true;
    private boolean mShowing;
    @Nullable
    private OnAnimationTimeChangedListener mOnAnimationTimeChangedListener;

    public MenuAnimation(View contentView, float scaleCoef, float translationCoef) {
        mContentView = contentView;
        mMaxTranslationZ = contentView.getResources().getDimension(R.dimen.menu_max_translaion_z);
        mScaleCoef = scaleCoef;
        mTranslationCoef = translationCoef;
        setInterpolator(new DecelerateInterpolator());
    }

    private void recountScale() {
        mScaleXStart = mContentView.getScaleX();
        mScaleYStart = mContentView.getScaleY();
        mTranslateXStart = mContentView.getX();
    }

    public MenuAnimation show() {
        recountScale();
        mScaleXDiff = mScaleCoef - mScaleXStart;
        mScaleYDiff = mScaleCoef - mScaleYStart;
        mTranslateXDiff = mContentView.getWidth() * mTranslationCoef - mTranslateXStart;
        setDuration(Math.abs((long) (MAX_DURATION * mScaleXDiff)));
        mShowing = true;
        return this;
    }

    public MenuAnimation hide() {
        recountScale();
        mScaleXDiff = 1 - mScaleXStart;
        mScaleYDiff = 1 - mScaleYStart;
        mTranslateXDiff = 0 - mTranslateXStart;
        setDuration(Math.abs((long) (MAX_DURATION * mScaleXDiff)));
        mShowing = false;
        return this;
    }

    public void applyTo(final float interpolatedTime) {
        apply(interpolatedTime);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        apply(interpolatedTime);
    }


    private void apply(float interpolatedTime) {
        if (mInit) {
            show();
            mInit = false;
        }

        mContentView.setX(mTranslateXStart + mTranslateXDiff * interpolatedTime);
        mContentView.setScaleX(mScaleXStart + mScaleXDiff * interpolatedTime);
        mContentView.setScaleY(mScaleYStart + mScaleYDiff * interpolatedTime);
        if (mShowing) {
            ViewCompat.setTranslationZ(mContentView, interpolatedTime * mMaxTranslationZ);
        } else {
            ViewCompat.setTranslationZ(mContentView, (1 - interpolatedTime) * mMaxTranslationZ);
        }

        if (mOnAnimationTimeChangedListener != null) {
            mOnAnimationTimeChangedListener.onAnimationTimeChanged(interpolatedTime);
        }
    }

    public void setOnAnimationTimeChangedListener(@Nullable OnAnimationTimeChangedListener listener) {
        mOnAnimationTimeChangedListener = listener;
    }

    public interface OnAnimationTimeChangedListener {
        void onAnimationTimeChanged(float time);
    }

}
