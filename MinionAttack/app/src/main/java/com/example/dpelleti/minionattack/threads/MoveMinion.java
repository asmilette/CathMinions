package com.example.dpelleti.minionattack.threads;

import android.content.Context;
import android.os.Handler;

import com.example.dpelleti.minionattack.entities.Minion;

import java.util.ArrayList;

/**
 * Created by dpelleti on 2015-09-15.
 */
public class MoveMinion implements Runnable {
    Context ctx;
    Handler handler;
    ArrayList<Minion> minions;
    int screenWidth, screenHeight;

    public MoveMinion(Context ctx, Handler handler, ArrayList<Minion> minions) {
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

        handler.postDelayed(this, 15);
    }
}
