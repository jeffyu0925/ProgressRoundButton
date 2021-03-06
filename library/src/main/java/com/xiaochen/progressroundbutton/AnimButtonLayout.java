package com.xiaochen.progressroundbutton;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.RelativeLayout;

public class AnimButtonLayout extends RelativeLayout {

    private AnimProgressButton mProgressButton;
    private Drawable mShadowDrawable;
    private final int DEFAULT_COLOR = Color.GRAY;
    private TimeInterpolator mInterpolator;
    private ValueAnimator mLayoutDownAnimator;
    private ValueAnimator mLayoutUpAnimator;
    private float mDensity;
    private float mCenterX;
    private float mCenterY;
    private int mLayoutWidth;
    private int mLayoutHeight;
    private float mCanvasScale = 1f;
    private final String PROPERTY_CANVAS_SCALE = "canvasScale";
    private final long ANIM_DOWN_DURATION = 128;
    private final long ANIM_UP_DURATION = 352;
    private float mTargetScale = 1.0f;
    private float mMinScale = 0.95f;
    private boolean mIsDrawShadow = true;

    public AnimButtonLayout(Context context) {
        super(context);
        mProgressButton = new AnimProgressButton(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mProgressButton.setLayoutParams(lp);
        this.addView(mProgressButton);
        init(context, null);
    }

    public AnimButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        mProgressButton = new AnimProgressButton(context, attrs);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mProgressButton.setLayoutParams(lp);
        this.addView(mProgressButton);
    }


    private void init(Context context, AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= 21) {
            mInterpolator = new PathInterpolator(0.33f, 0f, 0.33f, 1);
        } else {
            mInterpolator = new AccelerateDecelerateInterpolator();
        }

        if (attributeSet != null) {
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.AnimButtonLayout);

            mIsDrawShadow = a.getBoolean(R.styleable.AnimButtonLayout_layout_draw_shadow, true);

            a.recycle();
        }

        mShadowDrawable = getResources().getDrawable(R.drawable.gradient_layout_shadow);
        mDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mCanvasScale, mCanvasScale, mCenterX, mCenterY);
        drawShadow(canvas);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    /**
     * 畫陰影
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {
        if (mIsDrawShadow == false || mShadowDrawable == null) {
            return;
        }
        // 繪制陰影,陰影也會根據觸摸事件進行旋轉
        canvas.save();
        float scale = 1 - (1 - mCanvasScale) * 6;// scale最小是為0.7f
        canvas.scale(scale, scale, mCenterX, mCenterY);
        canvas.translate(0, (mCanvasScale - 1) * mLayoutHeight * 6 + mLayoutHeight * 0.4f + mDensity);
        mShadowDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }

        if (!isClickable()) {
            return false;
        }

        setTouchAnimation(ev);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLayoutWidth = w;
        mLayoutHeight = h;
        mCenterX = mLayoutWidth / 2;
        mCenterY = mLayoutHeight / 2;
        if (mShadowDrawable == null) {
            return;
        }
        mShadowDrawable.setColorFilter(DEFAULT_COLOR, PorterDuff.Mode.SRC_IN);
        mShadowDrawable.setBounds(0, 0, mLayoutWidth, mLayoutHeight);

        if (getParent() instanceof ViewGroup) {
            ((ViewGroup) getParent()).setClipChildren(false);
        }
    }

    public void setTouchAnimation(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(ev);
                break;
        }
    }

    /**
     * 處理ActionDown事件
     *
     * @param ev
     */
    private void handleActionDown(MotionEvent ev) {
        setupLayoutDownAnimator();
        mLayoutDownAnimator.start();
    }

    /**
     * 處理handleActionUp事件
     *
     * @param ev
     */
    private void handleActionUp(MotionEvent ev) {
        setupLayoutUpAnimator();
        mLayoutUpAnimator.start();
    }

    /**
     * Action down動畫
     */
    private void setupLayoutDownAnimator() {

        mLayoutDownAnimator = ValueAnimator.ofFloat(1f, 0.95f);
        mLayoutDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasScale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mLayoutDownAnimator.setInterpolator(mInterpolator);
        mLayoutDownAnimator.setDuration(ANIM_DOWN_DURATION);
    }

    /**
     * Action up動畫
     */
    private void setupLayoutUpAnimator() {

        mLayoutUpAnimator = ValueAnimator.ofFloat(0.95f, 1f);
        mLayoutUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCanvasScale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mLayoutUpAnimator.setInterpolator(mInterpolator);
        mLayoutUpAnimator.setDuration(ANIM_UP_DURATION);
    }


    @Override
    public void invalidate() {
        super.invalidate();
        mProgressButton.invalidate();
    }

    public int getState() {
        return mProgressButton.getState();
    }

    public void setState(int state) {
        mProgressButton.setState(state);
    }

    /**
     * 設置按鈕文字
     */
    public void setCurrentText(CharSequence charSequence) {
        mProgressButton.setCurrentText(charSequence);
    }

    public float getProgress() {
        return mProgressButton.getProgress();
    }

    public void setProgress(float progress, boolean needInvalidate) {
        mProgressButton.setProgress(progress, needInvalidate);
    }

    /**
     * Sometimes you should use the method to avoid memory leak
     */
    public void removeAllAnim() {
        mProgressButton.removeAllAnim();
    }

    public float getButtonRadius() {
        return mProgressButton.getButtonRadius();
    }

    public void setButtonRadius(float buttonRadius) {
        mProgressButton.setButtonRadius(buttonRadius);
    }

    public int getTextColor() {
        return mProgressButton.getTextColor();
    }

    public void setTextColor(int textColor) {
        mProgressButton.setTextColor(textColor);
    }

    public int getTextCoverColor() {
        return mProgressButton.getTextCoverColor();
    }

    public void setTextCoverColor(int textCoverColor) {
        mProgressButton.setTextCoverColor(textCoverColor);
    }

    public int getMinProgress() {
        return mProgressButton.getMinProgress();
    }

    public void setMinProgress(int minProgress) {

        mProgressButton.setMinProgress(minProgress);
    }

    public int getMaxProgress() {
        return mProgressButton.getMaxProgress();
    }

    public void setMaxProgress(int maxProgress) {
        mProgressButton.setMaxProgress(maxProgress);
    }

    public void enableDefaultPress(boolean enable) {
        mProgressButton.enableDefaultPress(enable);
    }

    public void enableDefaultGradient(boolean enable) {
        mProgressButton.enableDefaultGradient(enable);
    }

    public void setTextSize(float size) {
        mProgressButton.setTextSize(size);
    }

    public float getTextSize() {
        return mProgressButton.getTextSize();
    }

    public AnimProgressButton getProgressButton() {
        return mProgressButton;
    }

    public AnimProgressButton setCustomerController(ButtonController customerController) {
        return mProgressButton.setCustomerController(customerController);
    }
}
