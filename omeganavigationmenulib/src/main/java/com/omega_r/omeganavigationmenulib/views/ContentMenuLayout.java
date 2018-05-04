package com.omega_r.omeganavigationmenulib.views;


import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.omega_r.omeganavigationmenulib.R;
import com.omega_r.omeganavigationmenulib.R2;
import com.omega_r.omeganavigationmenulib.tools.MenuAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentMenuLayout extends FrameLayout implements GestureDetector.OnGestureListener,
        MenuAnimation.OnAnimationTimeChangedListener {

    public static final float COEF_MENU_CONTENT_SCALE = 0.85f;
    public static final float COEF_MENU_CONTENT_TRANSLATION = 0.75f;

    private static final int SWIPE_MIN_DISTANCE = 0;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final float START_ANIMATION = 0.1f;

    @BindView(R2.id.layout_hook)
    View mHookLayout;

    private boolean mShowFlag;

    private float mStartX;
    private boolean mMotion;

    private MenuAnimation mAnimation;
    private float mMinRawX;
    private float mPercent;
    @Nullable
    private OnProgressMenuChangedListener mListener;


    public ContentMenuLayout(Context context) {
        super(context);
        init();
    }

    public ContentMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_content_menu, this);
        ButterKnife.bind(this);
        mAnimation = new MenuAnimation(this, COEF_MENU_CONTENT_SCALE, COEF_MENU_CONTENT_TRANSLATION);
        mAnimation.setOnAnimationTimeChangedListener(this);
        mMinRawX = getResources().getDimension(R.dimen.menu_min_raw_x);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onDispatchTouchEvent(ev);
        return true;
    }

    public void onDispatchTouchEvent(MotionEvent ev) {
        if (!mShowFlag) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (ev.getRawX() <= mMinRawX) {
                        mAnimation.show();
                        mStartX = ev.getRawX();
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, START_ANIMATION);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                if (mPercent < START_ANIMATION && mMotion) {
                                    mAnimation.applyTo((Float) animation.getAnimatedValue());
                                }
                            }
                        });
                        valueAnimator.start();

                        mMotion = true;
                        return;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMotion) {
                        mPercent = (ev.getRawX() - mStartX) / (mHookLayout.getWidth() * COEF_MENU_CONTENT_TRANSLATION);
                        if (mPercent > 1) {
                            mPercent = 1;
                        } else if (mPercent < 0) {
                            mPercent = 0;
                        }

                        if (mPercent > START_ANIMATION) {
                            mAnimation.applyTo(mPercent);
                        }

                        return;

                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mMotion) {
                        if (mPercent > 0.25f) {
                            showMenu();
                        } else {
                            hideMenu();
                        }
                        mMotion = false;
                        return;
                    }
                    break;
            }

            super.dispatchTouchEvent(ev);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mMotion = true;
                    mStartX = ev.getRawX();
                    mAnimation.hide();
                    if (mAnimation.hasStarted() && !mAnimation.hasEnded()) {
                        clearAnimation();
                        mStartX = mHookLayout.getWidth() * COEF_MENU_CONTENT_TRANSLATION;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMotion) {
                        mPercent = 1 + (ev.getRawX() - mStartX) / (mHookLayout.getWidth() * COEF_MENU_CONTENT_TRANSLATION);
                        if (mPercent > 1) {
                            mPercent = 1;
                        } else if (mPercent < 0) {
                            mPercent = 0;
                        }
                        mAnimation.applyTo(1 - mPercent);

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mMotion) {
                        if (mPercent < 0.75f || mPercent > 0.95f) {
                            hideMenu();
                        } else {
                            showMenu();
                        }
                        mMotion = false;
                    }
                    break;
            }
        }
    }


    public void showMenu() {
        if (!mShowFlag || mMotion) {
            mShowFlag = true;
            startAnimation(mAnimation.show());
        }
    }

    public void hideMenu() {
        if (mShowFlag || mMotion) {
            mShowFlag = false;
            startAnimation(mAnimation.hide());
        }
    }

    public boolean isMenuShown() {
        return mShowFlag;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
                return false;
            }
            // right to left swipe
            if (e1.getRawX() - e2.getRawX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                onLeftSwipe();
            }
            // left to right swipe
            else if (e2.getRawX() - e1.getRawX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (e1.getRawX() <= mMinRawX) {
                    onRightSwipe();
                }
            }
        } catch (Exception ignored) {

        }
        return false;
    }

    private void onRightSwipe() {
        if (!mShowFlag) {
            showMenu();
        }

    }

    private void onLeftSwipe() {

    }

    public void setOnProgressMenuChangedListener(@Nullable OnProgressMenuChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationTimeChanged(float time) {
        if (mListener != null) {
            mListener.onProgressMenuChanged(1 - (getScaleX() - COEF_MENU_CONTENT_SCALE) / (1 - COEF_MENU_CONTENT_SCALE));
        }
    }

    public interface OnProgressMenuChangedListener {
        void onProgressMenuChanged(float progress);
    }

}
