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
    private Context ctx;
    private Handler handler;
    private ViewGroup viewGroup;
    private List<Minion> minions;
    private Random rand;

    private int creationDelay = 1500;
    private OnTooMuchMinionsListener onTooMuchMinionsListener;

    private final int MAX_SCORE_FOR_MINION = 5;
    private final int MAX_ALLOWED_MINIONS_IN_GAME = 10;

    public CreatingMinion(Context ctx, Handler handler, ViewGroup viewGroup, List<Minion> minions, Random rand) {
        this.ctx = ctx;
        this.handler = handler;
        this.viewGroup = viewGroup;
        this.minions = minions;
        this.rand = rand;
        handler.post(this);
    }

    @Override
    public void run() {
        Minion minion = new Minion(ctx, rand, rand.nextInt(MAX_SCORE_FOR_MINION));
        //On ajoute le minions dans la liste
        minions.add(minion);
        //On ajoute le minions sur l'écran
        viewGroup.addView(minion);

        if(minions.size() < MAX_ALLOWED_MINIONS_IN_GAME)
            handler.postDelayed(this, creationDelay);
        else if(onTooMuchMinionsListener != null)
            onTooMuchMinionsListener.onTooMuchMinions();
    }

    public void setCreationDelay(int creationDelay) {
        this.creationDelay = creationDelay;
    }

    public void setOnTooMuchMinionsListener(OnTooMuchMinionsListener onTooMuchMinionsListener) {
        this.onTooMuchMinionsListener = onTooMuchMinionsListener;
    }
}
