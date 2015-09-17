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

import java.util.ArrayList;

import tp_tries.amilette.tptrycamera.R;
import tp_tries.amilette.tptrycamera.entite.Minion;

/**
 * Created by dtchyman on 2015-09-16.
 */
public class MinionsPrison extends View  {

    Context ctx;
    Resources res;
    Handler handler;
    ArrayList<Minion> minions;
    int xi, yi;
    boolean isAlive = true;
    Bitmap minionPrison;
    Rect rect;
    int wScreen, hScreen;
    static int nbMinions = 0;


    Paint p;


    public MinionsPrison(Context ctx, Handler handler, ArrayList<Minion> minions) {
        super(ctx);

        this.handler = handler;
        this.minions = minions;
        this.xi = 0;
        this.yi = 0;
        nbMinions ++;

        rect = new Rect(xi, yi, xi + 100, yi + 100);
        res = getResources();
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        wScreen = metrics.widthPixels;
        hScreen = metrics.heightPixels;

        minionPrison = BitmapFactory.decodeResource(res, R.drawable.miniongood);

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(wScreen, hScreen);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawRect(rect, p);
        //si le minions arrive jusqu au bout de la prison
        if(nbMinions*minionPrison.getWidth()> wScreen) {
            int y = yi;
            int x = nbMinions*minionPrison.getWidth();
            canvas.drawBitmap(minionPrison, x, y, p);
        }
        //le prochain minions decends
        else{
            int y = minionPrison.getHeight();
            int x = nbMinions*minionPrison.getWidth();
            canvas.drawBitmap(minionPrison, x, y, p);
        }
    }


}
