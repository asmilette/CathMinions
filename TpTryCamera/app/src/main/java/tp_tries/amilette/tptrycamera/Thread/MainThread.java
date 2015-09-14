package tp_tries.amilette.tptrycamera.Thread;

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
import android.widget.Toast;

import tp_tries.amilette.tptrycamera.R;

/**
 * Created by Anne-Sophie on 2015-09-13.
 */
public class MainThread extends View implements Runnable {

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

    public MainThread(Context context, Handler handler) {
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

        x = y =0;

        stepX = x - xRect;
        stepY = y- yRect;



        res = getResources();
        main = BitmapFactory.decodeResource(res, R.drawable.hands);
        minion = BitmapFactory.decodeResource(res, R.drawable.min1);

        rect = new Rect(xRect, yRect, xRect + wR, yRect + hR);


    }

    // on Measure ///********************************************/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rect, p);
        int x = xRect- main.getWidth()/2;
        int y = yRect - main.getHeight()/2;
        canvas.drawBitmap(main, x, y, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int nbPointeur = event.getPointerCount();


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
             x = (int) event.getX();
            y = (int) event.getY();
            b = yRect - (((double)y-yRect)/((double)x - xRect))*xRect;
            a = (((double)y-yRect)/((double)x - xRect));

            handler.post(this);

        }
        return true;
    }


    public void movRec1(int x, int y) {

        stepX = x - xRect;
        stepY = y- yRect;



                if (x <= 0 || x + wR >= wScreen)
                    stepX *= -1;

                if (y <= 0 || y + hR >= hScreen)
                    stepY *= -1;

                xRect +=stepX;
                yRect +=stepY;

                rect.set(xRect, yRect, xRect + wR, yRect + hR);
                invalidate();

        }


    @Override
    public void run() {

        Boolean droite = xRect < x;

        int deltax= (x-xRect);
        int deltay = (y-yRect);

        if (deltax == 0){

            if (droite) {
                xRect += deltay / 20.0;
            } else {
                xRect -= deltay / 20.0 * -1;
            }

        }else {

            if (droite) {
                xRect += deltax / 20.0;
            } else {
                xRect -= deltax / 20.0 * -1;
            }
        }


        yRect = (int)(a*xRect + b) ;

        rect.set(xRect, yRect, xRect + wR, yRect + hR);
        invalidate();

        if (droite)
            if(xRect <= x )
                handler.postDelayed(this, 100);
        if(!droite)
            if(xRect >= x )
                handler.postDelayed(this, 100);



            }
}
