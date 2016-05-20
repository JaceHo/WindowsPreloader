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
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by daimajia on 14-4-30.
 */
public class WindowsPreloaderView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener {


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

    private List<CircleView> dots;

    PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat(View.ALPHA, 1, 1, 1, 1, 1, 1, 0, 0);
    PropertyValuesHolder rotateHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 225, 345, 455, 690, 815, 945, 945, 945);
    Keyframe[] keyframes = new Keyframe[]{
            Keyframe.ofFloat(0.0f), Keyframe.ofFloat(0.07f),
            Keyframe.ofFloat(0.3f), Keyframe.ofFloat(0.39f),
            Keyframe.ofFloat(0.7f), Keyframe.ofFloat(0.75f),
            Keyframe.ofFloat(0.76f), Keyframe.ofFloat(1.0f)
    };

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


        getViewTreeObserver().addOnGlobalLayoutListener(this);
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

    @Override
    public void onGlobalLayout() {
        if (dots == null) {
            AnimatorSet set = new AnimatorSet();
            dots = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                CircleView circleView = new CircleView(getContext(), mDotColor, (int) (mDotHeight / 2), getMeasuredWidth() / 2 - (int) (mDotHeight / 2), getPaddingTop());
                circleView.setAlpha(1);// initial it is transparent
                dots.add(circleView);
                circleView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                circleView.setPivotY(getMeasuredHeight() / 2 - getPaddingTop() - mDotHeight / 2);
                circleView.setPivotX(mDotHeight / 2);
                addView(circleView);

                rotateHolder.setKeyframes(keyframes);
                alphaHolder.setKeyframes(keyframes);
                final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(circleView, rotateHolder, alphaHolder);
                animator.setInterpolator(windowsPreloaderTimeInterpolator);
                animator.setStartDelay(240 * i);
                animator.setDuration(ANIMATION_DURATION);
                animator.setRepeatCount(-1);
                set.playTogether(animator);
            }
            set.start();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    /**
     * Created by hippo on 5/15/16.
     */
    class WindowsPreloaderTimeInterpolator implements TimeInterpolator {

        // time -> animation progress
        @Override
        public float getInterpolation(float input) {
            float fraction = 0;
            if (input < 0.07f) {
                float domain = 0.07f;
                float duration = input * domain;
                fraction = domain * ((input = input / duration - 1) * input * input + 1) + 0.07f;
            } else if (input < 0.3f) {
                fraction = input;
            } else if (input < 0.39f) {
                float domain = 0.7f - 0.39f;
                float duration = input * domain;
                fraction = ((input /= duration / 2) < 1.0f) ?
                        (domain / 2 * input * input * input + 0.39f)
                        : (domain / 2 * ((input -= 2) * input * input + 2) + 0.39f);
            } else if (input < 0.7f) {
                fraction = input;
            } else {
                float domain = 1.0f - 0.7f;
                float duration = ANIMATION_DURATION * domain;
                fraction = domain * ((input = input / duration - 1) * input * input + 1) + 0.7f;
            }
            return fraction;
        }
    }
}

