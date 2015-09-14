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
public class MainThread extends View implements Runnable{

    Context ctx;
    Resources res;
    Bitmap main ;
    Bitmap minion;

    Paint p;
    int xMain, yMain;
    int xRect, yRect, wR, hR;
    int xMinion, yMinion;
    int wScreen, hScreen;
    int stepXMinion, stepYMinion;
    Handler handler;
    Rect rect;

    public MainThread(Context context, Handler handler){
        super(context);
        ctx = context;
        this.handler = handler;
        p=new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        wScreen = metrics.widthPixels;
        hScreen = metrics.heightPixels;

        xMain =xRect= wScreen /2;
        yMain = yRect = hScreen;
        wR = 100;
        hR = 100;


        res = getResources();
        main = BitmapFactory.decodeResource(res, R.drawable.hands);
        minion = BitmapFactory.decodeResource(res, R.drawable.min1);

        rect = new Rect(xRect, yRect, xRect + wR, yRect + hR);
        stepYMinion = stepXMinion= 10;

    }

    // on Measure ///********************************************/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rect, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int nbPointeur = event.getPointerCount();

        if (nbPointeur == 1) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (x > xRect && x < xRect + wR)
                movRec1(x, y);

        }


        return true;
    }

    public void movRec1(int x, int y) {

        if (x >= xRect && x <= xRect + wR) {

            if (y < hR / 2)
                y = hR / 2;

            if (y > hScreen - hR / 2)
                y = hScreen - hR / 2;

            rect.set(xRect, yRect, xRect + wR, yRect + hR);
            invalidate();
        }

    }
    @Override
    public void run() {
        Toast.makeText(ctx,"" + xRect + " "+ yRect,Toast.LENGTH_LONG );
        handler.post(this);

    }
}
