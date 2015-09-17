package tp_tries.amilette.tptrycamera.Thread;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

import tp_tries.amilette.tptrycamera.entite.Minion;

/**
 * Created by dpelleti on 2015-09-15.
 */
public class MovingMinion implements Runnable {
    Context ctx;
    Handler handler;
    ArrayList<Minion> minions;
    int screenWidth, screenHeight;
    boolean isAlive = true;

    public MovingMinion(Context ctx, Handler handler, ArrayList<Minion> minions) {
        this.ctx = ctx;
        this.handler = handler;
        this.minions = minions;

        screenWidth = ctx.getResources().getDisplayMetrics().widthPixels;
        screenHeight = ctx.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void run() {
        for(Minion minion : minions) {
            if(minion.isAlive())
                minion.move();
        }

        if(isAlive)
            handler.postDelayed(this, 15);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        if(isAlive)
            handler.post(this);
    }
}
