package com.clj.blesample.XYview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class XYmap extends View {
    int centerX;
    int centerY;
    Point po = new Point();

    public XYmap(Context context) {
        super(context, (AttributeSet)null);
    }

    public XYmap(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.centerX = w / 2;
        this.centerY = h / 2;
        this.po.set(this.centerX, this.centerY);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setStrokeWidth(2.0F);
        paint.setTextSize(18.0F);
        paint.setColor(Color.parseColor("#303030"));
        paint.setAntiAlias(true);
        if (canvas != null) {
            canvas.drawLine(5.0F, (float)(2 * this.centerY - 40)/2, (float)(2 * this.centerX), (float)(2 * this.centerY - 40)/2, paint);
            canvas.drawLine((float)(this.centerX), 10.0F, (float)(this.centerX), (float)(2 * this.centerY - 40), paint);
            int x = 2 * this.centerX;
            int y = 2 * this.centerY - 40;
            this.drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5), new Point(x - 10, y + 5));
            canvas.drawText("10m", (float)(x - 35),(float)(2 * this.centerY - 40)/2, paint);
            x = 5;
            y = 0;
//            this.drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10), new Point(x + 5, y + 10));
            canvas.drawText("10m", (float)(this.centerX), (float)(y + 15), paint);
            String centerString = "(" + (this.centerX - this.po.x) / 2 + "," + (this.po.y - this.centerY) / 2 + ")";
            System.out.println("****************po.x*******************" + this.po.x);
            System.out.println("****************po.x中心*******************" + this.centerX);
            System.out.println("****************po.y*******************" + this.po.y);
            System.out.println("****************po.y中心*******************" + this.centerY);
            canvas.drawText(centerString, (float)(this.centerX), (float)(2 * this.centerY - 40)/2, paint);
        }

        if (canvas != null) {
            Point p_1 = new Point(10, 20);
            Point p_2 = new Point(20, 170);
            Point p_3 = new Point(30, 40);
            int size = 2 * this.centerX - 15;
            float n = (float)(size / 40);
//            canvas.drawLine(5.0F + n * (float)p_1.x, (float)(2 * this.po.y - 40 - p_1.y), 5.0F + n * (float)p_2.x, (float)(2 * this.po.y - 40 - p_2.y), paint);
//            canvas.drawLine(5.0F + n * (float)p_2.x, (float)(2 * this.po.y - 40 - p_2.y), 5.0F + n * (float)p_3.x, (float)(2 * this.po.y - 40 - p_3.y), paint);
//            canvas.drawCircle(5.0F + n * (float)p_1.x, (float)(2 * this.po.y - 40 - p_1.y), 2.0F, paint);
//            canvas.drawCircle(5.0F + n * (float)p_2.x, (float)(2 * this.po.y - 40 - p_2.y), 2.0F, paint);
//            canvas.drawCircle(5.0F + n * (float)p_3.x, (float)(2 * this.po.y - 40 - p_3.y), 2.0F, paint);
        }

    }

    private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
        Path path = new Path();
        path.moveTo((float)p1.x, (float)p1.y);
        path.lineTo((float)p2.x, (float)p2.y);
        path.lineTo((float)p3.x, (float)p3.y);
        path.close();
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setStyle(Style.FILL);
        canvas.drawPath(path, paint);
    }
}

