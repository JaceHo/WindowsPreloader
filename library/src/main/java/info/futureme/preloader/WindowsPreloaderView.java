package info.futureme.preloader;

/**
 * Created by hippo on 5/15/16.
 */

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import info.futureme.preloader.util.EasingUtil;


/**
 * Created by jeffrey on 14-4-30.
 */
public class WindowsPreloaderView extends RelativeLayout {


    /**
     * defines windowspreloader animation duration
     */
    private static final long ANIMATION_DURATION = 5500;//ms

    /**
     * The dot area color.
     */
    private int mDotColor;

    private final int default_dot_color = Color.rgb(255, 255, 255);

    /**
     * The dot height
     */
    private float mDotHeight;
    /**
     * the default dot height
     */
    private int default_dot_height = 38;

    private WindowsPreloaderTimeInterpolator windowsPreloaderTimeInterpolator;


    /**
     * circleview dots to be animated
     */
    private List<CircleView> dots;

    /**
     * animation set
     */
    AnimatorSet set = new AnimatorSet();

    /**
     * keyframe alpha holder
     */
    PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofKeyframe(View.ALPHA,
            Keyframe.ofFloat(0.0f, 1), Keyframe.ofFloat(0.07f, 1),
            Keyframe.ofFloat(0.3f, 1), Keyframe.ofFloat(0.39f, 1),
            Keyframe.ofFloat(0.7f, 1), Keyframe.ofFloat(0.75f, 1),
            Keyframe.ofFloat(0.76f, 0), Keyframe.ofFloat(1.0f, 0)
    );

    /**
     * keyframe rotateholder
     */
    PropertyValuesHolder rotateHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
            Keyframe.ofFloat(0.0f, 180), Keyframe.ofFloat(0.07f, 300),
            Keyframe.ofFloat(0.3f, 410), Keyframe.ofFloat(0.39f, 645),
            Keyframe.ofFloat(0.7f, 770), Keyframe.ofFloat(0.75f, 900),
            Keyframe.ofFloat(0.76f, 900), Keyframe.ofFloat(1.0f, 900)
    );

    public WindowsPreloaderView(Context context) {
        this(context, null);
    }

    public WindowsPreloaderView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.WindowsPreloaderStyle);
    }

    public WindowsPreloaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        windowsPreloaderTimeInterpolator = new WindowsPreloaderTimeInterpolator();

        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WindowsPreloader,
                defStyleAttr, 0);

        mDotColor = attributes.getColor(R.styleable.WindowsPreloader_dot_color, default_dot_color);
        mDotHeight = attributes.getDimension(R.styleable.WindowsPreloader_dot_height, default_dot_height);

        attributes.recycle();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mDotHeight * 5;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) mDotHeight * 5;
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (dots == null) {
            dots = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                final CircleView circleView = new CircleView(getContext(), mDotColor, (int) (mDotHeight / 2), getMeasuredWidth() / 2 - (int) (mDotHeight / 2), getPaddingTop());
                circleView.setAlpha(0);// initial it is transparent
                dots.add(circleView);
                circleView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                circleView.setPivotY(getMeasuredHeight() / 2 - getPaddingTop() - mDotHeight / 2);
                circleView.setPivotX(mDotHeight / 2);
                addView(circleView);

                final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(circleView, rotateHolder, alphaHolder);
                animator.setInterpolator(windowsPreloaderTimeInterpolator);
                animator.setStartDelay(240 * i);
                animator.setDuration(ANIMATION_DURATION);
                animator.setRepeatCount(-1);

                set.playTogether(animator);
            }
        }

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //start animation
        set.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }


    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }


    /**
     * Created by hippo on 5/15/16.
     */
    class WindowsPreloaderTimeInterpolator implements TimeInterpolator {

        // time -> animation progress
        @Override
        public float getInterpolation(float input) {
            //DOMAIN*f((x-pre)/DURATION)+START
            float fraction;
            if (input <= 0.07f) {
                EasingUtil.START = 0.0f;
                EasingUtil.DURATION = 0.07f;
                EasingUtil.DOMAIN = EasingUtil.DURATION;
                fraction = EasingUtil.Cubic.easeOut.getInterpolation(input);
            } else if (input <= 0.3f) {
                EasingUtil.START = getInterpolation(0.07f);
                EasingUtil.DURATION = 0.3f - 0.07f;
                EasingUtil.DOMAIN = EasingUtil.DURATION;
                fraction = EasingUtil.Linear.easeNone.getInterpolation(input - 0.07f);
            } else if (input <= 0.39f) {
                EasingUtil.START = getInterpolation(0.3f);
                EasingUtil.DURATION = 0.39f - 0.3f;
                EasingUtil.DOMAIN = EasingUtil.DURATION;
                fraction = EasingUtil.Cubic.easeInOut.getInterpolation(input -0.3f);
            } else if (input <= 0.7f) {
                EasingUtil.START = getInterpolation(0.39f);
                EasingUtil.DURATION = 0.7f - 0.39f;
                EasingUtil.DOMAIN = EasingUtil.DURATION;
                fraction = EasingUtil.Linear.easeNone.getInterpolation(input - 0.39f);
            } else {
                EasingUtil.START = getInterpolation(0.7f);
                EasingUtil.DURATION = 1.0f - 0.7f;
                EasingUtil.DOMAIN = EasingUtil.DURATION;
                fraction = EasingUtil.Cubic.easeOut.getInterpolation(input - 0.7f);
            }
            return fraction;
        }
    }
}

