package com.kun.station;

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

/**
 * Created by kun on 16/5/26.
 */
public class MyView extends View {
    Paint linePaint;
    Paint areaPaint;
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
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#aaaaaa"));
        linePaint.setStrokeWidth(3);
        areaPaint = new Paint();
        areaPaint.setAntiAlias(true);
        areaPaint.setColor(Color.parseColor("#cccccc"));
        areaPaint.setStyle(Paint.Style.FILL);
        width = 30;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBigAcre(canvas);
        drawSamll(canvas);
        drawArea(canvas);
    }

    private void drawBigAcre(Canvas canvas) {
        canvas.drawLine(right + width / 2, 0, getWidth() - width / 2, 0, linePaint);
        canvas.drawLine(right + width / 2, getHeight(), getWidth() - width / 2, getHeight(), linePaint);
        canvas.drawLine(getWidth(), 0 + width / 2, getWidth(), getHeight() - width / 2, linePaint);
        RectF oval1 = new RectF();
        oval1.left = right;
        oval1.top = 0;
        oval1.right = right + width;
        oval1.bottom = width;
        canvas.drawArc(oval1, 180, 90, false, linePaint);
        RectF oval2 = new RectF();
        oval2.left = getWidth() - width;
        oval2.top = 0;
        oval2.right = getWidth();
        oval2.bottom = width;
        canvas.drawArc(oval2, 270, 90, false, linePaint);
        RectF oval3 = new RectF();
        oval3.left = getWidth() - width;
        oval3.top = getHeight() - width;
        oval3.right = getWidth();
        oval3.bottom = getHeight();
        canvas.drawArc(oval3, 360, 90, false, linePaint);
        RectF oval4 = new RectF();
        oval4.left = right;
        oval4.top = getHeight() - width;
        oval4.right = right + width;
        oval4.bottom = getHeight();
        canvas.drawArc(oval4, 90, 90, false, linePaint);
    }

    private void drawSamll(Canvas canvas) {
        canvas.drawLine(left + width / 2, bottom, right, bottom, linePaint);
        canvas.drawLine(left + width / 2, top, right, top, linePaint);
        canvas.drawLine(right, top, right, width / 2, linePaint);
        canvas.drawLine(right, bottom, right, getHeight() - width / 2, linePaint);
        canvas.drawLine(left, bottom - width / 2, left, top + width / 2, linePaint);
        RectF oval1 = new RectF();
        oval1.left = left;
        oval1.top = bottom - width;
        oval1.right = left + width;
        oval1.bottom = bottom;
        canvas.drawArc(oval1, 90, 90, false, linePaint);
        RectF oval2 = new RectF();
        oval2.left = left;
        oval2.top = top;
        oval2.right = left + width;
        oval2.bottom = top + width;
        canvas.drawArc(oval2, 180, 90, false, linePaint);
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
        canvas.drawPath(path, areaPaint);
    }

    public void setData(int left, int top, int bottom, int right) {
        this.left = left + 10;
        this.top = top + 10;
        this.bottom = bottom - 10;
        this.right = right - 10;
        invalidate();
    }
}
