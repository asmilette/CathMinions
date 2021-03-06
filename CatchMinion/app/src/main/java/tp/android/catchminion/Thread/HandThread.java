package tp.android.catchminion.Thread;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import tp.android.catchminion.R;
import tp.android.catchminion.entite.OnFinalDestination;

/**
 * Created by Anne-Sophie on 2015-09-13.
 */
public class HandThread extends View implements Runnable {

    Context ctx;
    Resources res;
    Bitmap main;
    Bitmap minion;

    Paint p;
    int xMain, yMain;
    int xRect, yRect, wR, hR;
    int xMinion, yMinion;
    int wScreen, hScreen;
    int stepX, stepY;
    Handler handler;
    Rect rect;
    int x, y;
    double a, b;
    boolean isAlive = true, isCompleted = false;

    OnFinalDestination destFinale;

    public HandThread(Context context, Handler handler) {
        super(context);
        ctx = context;
        this.handler = handler;
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        wScreen = metrics.widthPixels;
        hScreen = metrics.heightPixels;

        xMain = xRect = wScreen / 2;
        yMain = yRect = hScreen / 2;
        wR = 100;
        hR = 100;

        x = y = 0;

        stepX = x - xRect;
        stepY = y - yRect;

        res = getResources();
        main = BitmapFactory.decodeResource(res, R.drawable.hands);
        minion = BitmapFactory.decodeResource(res, R.drawable.min1);

        rect = new Rect(xRect, yRect, xRect + wR, yRect + hR);
        isAlive = true;
    }

    // on Measure ///********************************************/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawRect(rect, p);
        int x = xRect - main.getWidth() / 2;
        int y = yRect - main.getHeight() / 2;
        canvas.drawBitmap(main, x, y, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isEnabled()) {
            x = (int) event.getX();
            y = (int) event.getY();
            b = yRect - (((double) y - yRect) / ((double) x - xRect)) * xRect;
            a = (((double) y - yRect) / ((double) x - xRect));

            isCompleted = false;
            handler.post(this);

        }
        return true;
    }

    @Override
    public void run() {
        if (isAlive) {
            boolean droite = xRect <= x;

            int deltax = (x - xRect);
            int deltay = (y - yRect);

            if (deltax == 0) {

                if (droite) {
                    xRect += Math.ceil(deltay / 5.0);
                } else {
                    xRect -= Math.ceil(deltay / 5.0) * -1;
                }

            } else {

                if (droite) {
                    xRect += Math.ceil(deltax / 5.0);
                } else {
                    xRect -= Math.ceil(deltax / 5.0) * -1;
                }
            }


            yRect = (int) (a * xRect + b);

            rect.set(xRect, yRect, xRect + wR, yRect + hR);
            invalidate();

            if (droite) {
                if (xRect < x) {
                    handler.postDelayed(this, 40);
                } else {
                    isCompleted = true;
                }
            } else {
                if (xRect > x) {
                    handler.postDelayed(this, 40);
                } else {
                    isCompleted = true;
                }
            }

            if (isCompleted)
                destFinale.ActionPerformed(x, y);
        }
    }

    public void setDestFinale(OnFinalDestination dest) {
        destFinale = dest;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
        if (isAlive)
            handler.post(this);
    }
}
