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
import android.view.View;

import tp_tries.amilette.tptrycamera.R;
import tp_tries.amilette.tptrycamera.entite.OnFinalDestination;

/**
 * Created by amilette on 2015-09-15.
 */
public class CatchThread extends View implements  Runnable {

    Context ctx;
    Handler handler;

    Resources res;
    Bitmap mainCatch;

    int wScreen, hScreen;

    int xi, yi, xdroit, ydroit;

    int xf, yf;

    Rect rect;

    Paint p;

    double a, b;

    Boolean terminer, isAlive;
    OnFinalDestination destFinale;

    public CatchThread (Context context, Handler handler ){
        super(context);
        ctx = context;
        this.handler = handler;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

        res = getResources();
        mainCatch = BitmapFactory.decodeResource(res, R.drawable.handcatch);

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        wScreen = metrics.widthPixels;
        hScreen = metrics.heightPixels;
        setVisibility(INVISIBLE);
        isAlive = false;
        terminer = true;
    }

    public void startCatch(int x, int y) {
        xi = xdroit= x;
        yi =  ydroit=  y;

        rect = new Rect(xi, yi, xi + 100, yi + 100);

        xf = wScreen/2;
        yf= hScreen - (int)(hScreen*0.25);

        b = yi - (((double)yf-yi)/((double)xf - xi))*xi;
        a = (((double)yf-yi)/((double)xf - xi));

        terminer = false;
        if(handler != null)
            handler.post(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //if(getVisibility() == VISIBLE) {
            int x = xi - mainCatch.getWidth() / 2;
            int y = yi - mainCatch.getHeight() / 2;
            canvas.drawBitmap(mainCatch, x, y, p);
       // }
    }

    @Override
    public void run() {
        if(isAlive) {

            Boolean droite = xdroit > xf;


            int deltax = (xf - xdroit);
            int deltay = (yf - yi);


            if (droite) {
                xi += Math.ceil(deltax / 20.0);
                yi += Math.ceil(deltay / 20.0);
            } else {
                xi -= Math.ceil(deltax / 20.0) * -1;
                yi += Math.ceil(deltay / 20.0);
            }

            rect.set(xi, yi, xi + 100, yi + 100);
            invalidate();

            if (yi < yf) {
                handler.postDelayed(this, 30);
            } else {
                terminer = true;
            }

            if (terminer)
                destFinale.ActionPerformed();
        }
    }


    public void setDestFinale(OnFinalDestination dest) {
        destFinale = dest;
    }

    public void setIsAlive(Boolean isAlive) {
        this.isAlive = isAlive;
        if(isAlive && !terminer)
            handler.post(this);
    }
}
