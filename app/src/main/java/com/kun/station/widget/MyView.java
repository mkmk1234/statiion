package com.kun.station.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.kun.station.util.DisplayUtils;

/**
 * Created by kun on 16/5/26.
 */
public class MyView extends View {
    Paint linePaint1;
    Paint areaPaint1;
    Paint linePaint2;
    int lineWidth = 1;
    int radius = 6;
    int padding;
    int width;
    int left;
    int right;
    int top;
    int bottom;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        width = DisplayUtils.dip2px(getContext(), radius);
        padding = DisplayUtils.dip2px(getContext(), 5);
        lineWidth = DisplayUtils.dip2px(getContext(), 1);
        linePaint1 = new Paint();
        linePaint1.setAntiAlias(true);
        linePaint1.setColor(Color.parseColor("#97979d"));
        linePaint1.setStrokeWidth(lineWidth);
        areaPaint1 = new Paint();
        areaPaint1.setAntiAlias(true);
        areaPaint1.setColor(Color.parseColor("#e6e6ed"));
        areaPaint1.setStyle(Paint.Style.FILL);
        linePaint2 = new Paint();
        linePaint2.setAntiAlias(true);
        linePaint2.setColor(Color.parseColor("#a8a8af"));
        linePaint2.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigAcre(canvas);
        drawSamll(canvas);
        drawArea(canvas);
        drawLeft(canvas);
    }

    private void drawBigAcre(Canvas canvas) {
        canvas.drawLine(right + width / 2, 0, getWidth() - width / 2, 0, linePaint1);
        canvas.drawLine(right + width / 2, getHeight(), getWidth() - width / 2, getHeight(), linePaint1);
        canvas.drawLine(getWidth(), 0 + width / 2, getWidth(), getHeight() - width / 2, linePaint1);
        RectF oval1 = new RectF();
        oval1.left = right;
        oval1.top = 0;
        oval1.right = right + width;
        oval1.bottom = width;
        canvas.drawArc(oval1, 180, 90, false, linePaint1);
        RectF oval2 = new RectF();
        oval2.left = getWidth() - width;
        oval2.top = 0;
        oval2.right = getWidth();
        oval2.bottom = width;
        canvas.drawArc(oval2, 270, 90, false, linePaint1);
        RectF oval3 = new RectF();
        oval3.left = getWidth() - width;
        oval3.top = getHeight() - width;
        oval3.right = getWidth();
        oval3.bottom = getHeight();
        canvas.drawArc(oval3, 360, 90, false, linePaint1);
        RectF oval4 = new RectF();
        oval4.left = right;
        oval4.top = getHeight() - width;
        oval4.right = right + width;
        oval4.bottom = getHeight();
        canvas.drawArc(oval4, 90, 90, false, linePaint1);
    }

    private void drawSamll(Canvas canvas) {
        canvas.drawLine(left + width / 2, bottom, right, bottom, linePaint1);
        canvas.drawLine(left + width / 2, top, right, top, linePaint1);
        canvas.drawLine(right, top, right, width / 2, linePaint1);
        canvas.drawLine(right, bottom, right, getHeight() - width / 2, linePaint1);
        canvas.drawLine(left, bottom - width / 2, left, top + width / 2, linePaint1);
        RectF oval1 = new RectF();
        oval1.left = left;
        oval1.top = bottom - width;
        oval1.right = left + width;
        oval1.bottom = bottom;
        canvas.drawArc(oval1, 90, 90, false, linePaint1);
        RectF oval2 = new RectF();
        oval2.left = left;
        oval2.top = top;
        oval2.right = left + width;
        oval2.bottom = top + width;
        canvas.drawArc(oval2, 180, 90, false, linePaint1);
    }

    private void drawLeft(Canvas canvas) {
        int realLeft = left - padding;
        int realRight = right + padding;
        canvas.drawLine(realLeft + lineWidth * 2, getHeight() - width / 2, realLeft + lineWidth * 2, 0 + width / 2, linePaint2);
        canvas.drawLine(realLeft + width / 2, getHeight() - lineWidth / 2, realRight - width / 2, getHeight() - lineWidth / 2, linePaint2);
        canvas.drawLine(realLeft + width / 2, 0 + lineWidth / 2, realRight - width / 2, 0 + lineWidth / 2, linePaint2);
        RectF oval1 = new RectF();
        oval1.left = realLeft;
        oval1.top = 0;
        oval1.right = realLeft + width;
        oval1.bottom = width;
        canvas.drawArc(oval1, 90, 90, false, linePaint1);
        RectF oval2 = new RectF();
        oval2.left = left;
        oval2.top = top;
        oval2.right = left + width;
        oval2.bottom = top + width;
        canvas.drawArc(oval2, 180, 90, false, linePaint1);
    }

    private void drawArea(Canvas canvas) {
        RectF oval1 = new RectF();
        oval1.left = right + 3;
        oval1.top = 0 + 3;
        oval1.right = right + width + 3;
        oval1.bottom = width + 3;
        RectF oval2 = new RectF();
        oval2.left = getWidth() - width - 3;
        oval2.top = 0 + 3;
        oval2.right = getWidth() - 3;
        oval2.bottom = width + 3;
        RectF oval3 = new RectF();
        oval3.left = getWidth() - width - 3;
        oval3.top = getHeight() - width - 3;
        oval3.right = getWidth() - 3;
        oval3.bottom = getHeight() - 3;
        RectF oval4 = new RectF();
        oval4.left = right + 3;
        oval4.top = getHeight() - width - 3;
        oval4.right = right + width + 3;
        oval4.bottom = getHeight() - 3;
        RectF oval5 = new RectF();
        oval5.left = left + 3;
        oval5.top = bottom - width - 3;
        oval5.right = left + width + 3;
        oval5.bottom = bottom - 3;
        RectF oval6 = new RectF();
        oval6.left = left + 3;
        oval6.top = top + 3;
        oval6.right = left + width - 3;
        oval6.bottom = top + width - 3;
        Path path = new Path();
        path.moveTo(right + 3, 0 + 3);
        path.lineTo(getWidth() - 3, 0 + 3);
        path.arcTo(oval2, 270, 90);
        path.lineTo(getWidth() - 3, getHeight() - width - 3);
        path.arcTo(oval3, 360, 90);
        path.lineTo(right + 3, getHeight() - 3);
        path.arcTo(oval4, 90, 90);
        path.lineTo(right + 3, bottom - 3);
        path.lineTo(left + 3, bottom - 3);
        path.arcTo(oval5, 90, 90);
        path.lineTo(left + 3, top - 3);
        path.arcTo(oval6, 180, 90);
        path.lineTo(right + 3, top + 3);
        path.lineTo(right + 3, width + 3);
        path.arcTo(oval1, 180, 90);
        canvas.drawPath(path, areaPaint1);
    }

    public void setData(int left, int top, int bottom, int right) {
        this.left = left + padding;
        this.top = top + padding;
        this.bottom = bottom - padding;
        this.right = right - padding;
        invalidate();
    }
}
