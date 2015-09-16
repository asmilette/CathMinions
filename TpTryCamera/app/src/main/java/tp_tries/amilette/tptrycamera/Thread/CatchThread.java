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

/**
 * Created by amilette on 2015-09-15.
 */
public class CatchThread extends View implements  Runnable {

    Context ctx;
    Handler handler;

    Resources res;
    Bitmap mainCatch;

    int wScreen, hScreen;

    int xi, yi;

    int xf, yf;

    Rect rect;

    Paint p;

    double a, b;

    Boolean terminer;






    public CatchThread (Context context, int xCatch, int yCatch, Handler handler ){
        super(context);
        ctx = context;
        this.handler = handler;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        wScreen = metrics.widthPixels;
        hScreen = metrics.heightPixels;

        xi = xCatch;
        yi = yCatch;

        rect = new Rect(xi, yi, xi + 100, yi + 100);
        res = getResources();
        mainCatch = BitmapFactory.decodeResource(res, R.drawable.handcatch);

        xf = wScreen/2;
        yf= -hScreen ;

        b = yi - (((double)yf-yi)/((double)xf - xi))*xi;
        a = (((double)yf-yi)/((double)xf - xi));

        terminer = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawRect(rect, p);
        int x = xi- mainCatch.getWidth()/2;
        int y = yi - mainCatch.getHeight()/2;
        canvas.drawBitmap(mainCatch, x, y, p);
    }


    @Override
    public void run() {

        Boolean droite = xi < xf;


        int deltax= (xf-xi);
        int deltay = (yi-yf);


        if (droite) {
            xi -= deltax / 20.0;
        } else {
            xi += deltax / 20.0 * -1;
        }

        yi = (int)(a*xi + b) ;

        rect.set(xi, yi, xi + 100, yi + 100);
        invalidate();


        if (droite) {
            if (xi <= xf) {
                handler.postDelayed(this, 300);
            } else {
                terminer = true;
            }
        }

        if(!droite) {
            if (xi >= xf) {
                handler.postDelayed(this, 300);
            } else {
                terminer = true;
            }
        }

    }


    public boolean getIsTerminer() {
        return terminer;
    }

    public void setIsTerminer(boolean isTerminer) {
        this.terminer = isTerminer;
    }
}
