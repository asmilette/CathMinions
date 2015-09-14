package com.example.dpelleti.minionattack;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dpelleti on 2015-09-14.
 */
public class Minion extends ImageView implements Runnable {
    private int points;
    private Handler handler;
    private ViewGroup parent;
    private boolean isValueInitialize = false;

    private boolean isAlive = true;

    private boolean isForward = true;

    private final int step = 50;
    private final int freq = 10;
    private int amplitude = 0;
    private final int delay = 15;

    private final int facteur_max_amplitude = 3;
    private final int facteur_min_amplitude = 10;



    private double i= -1;
    private int offsetY = 300;
    private int  parentLeft, parentTop, parentRight, parentBottom;

    public Minion(Context context, Handler handler, ViewGroup parent, int points) {
        super(context);
        this.handler = handler;
        this.parent = parent;
        this.points = points;
        this.setImageResource(R.drawable.minion);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        parent.addView(this);
        handler.post(this);
    }

    @Override
    public void run() {
        /*String message = (String)handler.obtainMessage().obj;
        if(message.equals("KILL"))
            isAlive = false;*/



       /* if(i < 0)
            i = getLeft() / step;
        if(offsetY < 0)
            offsetY = getTop();

        if(isAlive) {
            if(isForward)
                i += 0.02;
            else
                i-= 0.02;

            int x = (int)(i*(step*freq));
            int y = (int) (-1 * Math.cos(i*freq) * amplitude)+parentTop+offsetY;

            if(x+getWidth() > parentRight)
                isForward = false;
            if(x < parentLeft)
                isForward = true;

            if(y < 0)
                y = parentTop;

            if(y+getHeight() > parentBottom)
                y = parentBottom-getHeight();

            layout(x, y, x + getWidth(), y + getHeight());

            handler.postDelayed(this, delay);
        }*/
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        if(isAlive)
            handler.post(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(!isValueInitialize) {
            parentLeft = parent.getLeft();
            parentTop = parent.getTop();
            parentRight = parent.getRight();
            parentBottom = parent.getBottom();

            int facteur_amplitude = (int)((Math.random()*10)+3);
            amplitude = parent.getHeight()/facteur_amplitude;
            Log.v("Calcul Amplitude", "Left"+String.valueOf(parentLeft)
                +"Top: "+String.valueOf(parentTop)
                +"Right: "+String.valueOf(parentRight)
                +"Bottom: "+String.valueOf());
            Log.v("Calcul Amplitude", "Facteur: "+String.valueOf(facteur_amplitude));
            Log.v("Calcul Amplitude", "Amplitude:"+String.valueOf(amplitude));
        }
    }
}
