package com.xswq.consumer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义圆角ImageView
 */
public class WeiQiStoryImageView extends AppCompatImageView {
    float width, height;
    private int degrees = 100;

    public WeiQiStoryImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public WeiQiStoryImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public WeiQiStoryImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= degrees && height > degrees) {
            Path path = new Path();
            //四个圆角
            path.moveTo(degrees, 0);
            path.lineTo(width - degrees, 0);
            path.quadTo(width, 0, width, degrees);
            path.lineTo(width, height - degrees);
            path.quadTo(width, height, width - degrees, height);
            path.lineTo(degrees, height);
            path.quadTo(0, height, 0, height - degrees);
            path.lineTo(0, degrees);
            path.quadTo(0, 0, degrees, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
