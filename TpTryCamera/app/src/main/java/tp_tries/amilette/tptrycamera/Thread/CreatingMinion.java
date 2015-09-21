package tp_tries.amilette.tptrycamera.Thread;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;

import java.util.List;
import java.util.Random;

import tp_tries.amilette.tptrycamera.entite.Minion;

/**
 * Created by dpelleti on 2015-09-16.
 */
public class CreatingMinion implements Runnable {
    public interface OnTooMuchMinionsListener {
        void onTooMuchMinions();
    }

    public interface OnCreatedMinionListener {
        void onCreatedMinion();
    }

    private Context ctx;
    private Handler handler;
    private ViewGroup viewGroup;
    private List<Minion> minions;
    private Random rand;

    private int creationDelay = 3500;
    private OnTooMuchMinionsListener onTooMuchMinionsListener;
    private OnCreatedMinionListener onCreatedMinionListener;

    private final int MAX_SCORE_FOR_MINION = 5;
    private final int MAX_ALLOWED_MINIONS_IN_GAME = 15;

    private boolean isAlive;

    public CreatingMinion(Context ctx, Handler handler, ViewGroup viewGroup, List<Minion> minions, Random rand) {
        this.ctx = ctx;
        this.handler = handler;
        this.viewGroup = viewGroup;
        this.minions = minions;
        this.rand = rand;
        isAlive = true;
        handler.post(this);
    }

    @Override
    public void run() {
        if(isAlive) {
            Minion minion = new Minion(ctx, rand, rand.nextInt(MAX_SCORE_FOR_MINION));
            //On ajoute le minions dans la liste
            minions.add(minion);
            //On ajoute le minions sur l'écran
            viewGroup.addView(minion);
            if (onCreatedMinionListener != null)
                onCreatedMinionListener.onCreatedMinion();

            if (minions.size() < MAX_ALLOWED_MINIONS_IN_GAME)
                handler.postDelayed(this, creationDelay);
            else if (onTooMuchMinionsListener != null)
                onTooMuchMinionsListener.onTooMuchMinions();
        }
    }
    //Permet de modifier la vitesse de création des minions
    //Peut-être utilisé si on on attrape des bonus qui ralentit ou des malus qui augmente la vitesse
    public void setCreationDelay(int creationDelay) {
        this.creationDelay = creationDelay;
    }

    public void setOnTooMuchMinionsListener(OnTooMuchMinionsListener onTooMuchMinionsListener) {
        this.onTooMuchMinionsListener = onTooMuchMinionsListener;
    }

    public void setOnCreatedMinionListener(OnCreatedMinionListener onCreatedMinionListener) {
        this.onCreatedMinionListener = onCreatedMinionListener;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        if(isAlive)
            handler.post(this);
    }
}
