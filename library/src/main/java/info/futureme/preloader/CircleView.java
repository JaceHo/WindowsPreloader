package info.futureme.preloader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by hippo on 5/15/16.
 */
public class CircleView extends View{
    private final int radis;
    private final int paddingLeft;
    private static Paint paint = new Paint(Paint.DITHER_FLAG);
    private final int paddingTop;

    public CircleView(Context context, int color, int radis, int paddingLeft, int paddingTop) {
        super(context);
        this.radis = radis;
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(radis * 2, radis * 2);
    }

    @Override
    public void layout(int l, int t, int r, int b){
        l += paddingLeft;
        t += paddingTop;
        super.layout(l , t + radis, l + 2 * radis, t + radis * 3);
    }

    public void onDraw(Canvas canvas){
        canvas.drawCircle((float)(getLeft() - paddingLeft + radis), (float)(getTop() - paddingTop ), radis, paint);
    }
}
