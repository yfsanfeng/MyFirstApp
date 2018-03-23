package com.lxw.bouncingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * description... //TODO
 *
 * @author lsw
 * @version 1.0, 2017/4/16
 * @see //TODO
 * @since JDK 1.8
 */

public class BouncingView extends View {
    public static final String TAG = "BouncingView";
    private Paint mPaint;
    private float mArcHeight;
    private static final float MAX_ARC_HEIGHT = 100.0f;
    private Path mPath;
    //状态
    private Status mStatus = Status.NONE;

    public enum Status {
        NONE,
        SMOOTH_UP,
        SMOOTH_DOWN;
    }

    public BouncingView(Context context) {
        this(context, null);
    }

    public BouncingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BouncingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int currentPointY = 0;
        switch (mStatus) {
            case SMOOTH_DOWN:
                currentPointY = (int) MAX_ARC_HEIGHT;
                break;
            case SMOOTH_UP:
                int height = (int) (getHeight() * (1 - mArcHeight / MAX_ARC_HEIGHT));
                currentPointY = (int) (height+ MAX_ARC_HEIGHT);
                Log.d(TAG, "onDraw() returned: currentPointY: " +currentPointY +" height: "+height);
                break;
            case NONE:
                currentPointY = 0;
                Log.d(TAG, "onDraw() returned: NONE"  );
                break;
        }
        mPath.reset();
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, currentPointY - mArcHeight, getWidth(), currentPointY);
        mPath.lineTo(getWidth() , getHeight() );
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }


    public void show() {
       postDelayed(new Runnable() {
           @Override
           public void run() {
              if(mListener!=null){
                  mListener.onFinish();
              }
           }
       },700);
        mStatus = Status.SMOOTH_UP;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, MAX_ARC_HEIGHT);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (float) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate() returned: " + mArcHeight);
                invalidate();
                if(mArcHeight==MAX_ARC_HEIGHT){
                    onHide();
                }
            }
        });
        valueAnimator.start();
    }

    private void onHide() {
        mStatus = Status.SMOOTH_DOWN;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(MAX_ARC_HEIGHT,0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (float) animation.getAnimatedValue();

                invalidate();
            }
        });
        valueAnimator.start();
    }

    private onAnimatorEndListener mListener;

    public void setListener(onAnimatorEndListener listener) {
        mListener = listener;
    }

    public interface  onAnimatorEndListener{
        void onFinish();
    }
}

