package tp.android.catchminion.entite;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

import tp.android.catchminion.R;


/**
 * Created by dpelleti on 2015-09-14.
 */
public class Minion extends ImageView {
    private int points;
    private boolean isAlive = true;
    private boolean isForward = true;

    private int step = 50;
    private int freq = 10;
    private int amplitude = 0;

    private final float facteur_max_amplitude = 15.45f/68;
    private final float facteur_min_amplitude = 4.53f/68;

    private final int minFreq = 5;
    private final int maxFreq = 10;

    private final int maxStep = 50;
    private final int minStep = 25;

    private double i;
    private int offsetX;
    private int offsetY;

    private int screenWidth, screenHeight;
    private Random rand;

    AnimationDrawable anim;


    public Minion(Context context, Random rand, int points) {
        super(context);
        this.rand = rand;
        this.points = points;


       // this.setImageResource(R.drawable.minion);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //test anim
        setBackgroundResource(R.drawable.anim_img_multi);
        anim = (AnimationDrawable) getBackground();
        anim.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        init();
    }

    private void init() {
        offsetX = rand.nextInt(screenWidth);
        offsetY = rand.nextInt(screenHeight/2);
        freq = rand.nextInt(maxFreq-minFreq)+minFreq;
        step = rand.nextInt((maxStep-minStep))+minStep;

        amplitude = (int)(screenHeight * (rand.nextFloat()*(facteur_max_amplitude-facteur_min_amplitude)) + facteur_min_amplitude);

        i = (offsetX/step)/freq;
    }

    public void move() {
        if(isForward)
            i += 0.02;
        else
            i-= 0.02;

        int x = (int)(i*(step*freq));
        int y = (int)(-1 * Math.cos(i*freq) * amplitude)+offsetY;

        if(x+getWidth() > screenWidth)
            isForward = false;
        if(x < 0)
            isForward = true;

        if(y < 0)
            y = 0;

        if(y+getHeight() > screenHeight)
            y = screenHeight-getHeight();

        layout(x, y, x + getWidth(), y + getHeight());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getPoints() {
        return points;
    }
}
