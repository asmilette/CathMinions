package com.example.dpelleti.minionattack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.dpelleti.minionattack.entities.HighScore;
import com.example.dpelleti.minionattack.entities.HighScoreDialog;
import com.example.dpelleti.minionattack.entities.Minion;
import com.example.dpelleti.minionattack.managers.HighScoreManager;
import com.example.dpelleti.minionattack.threads.MoveMinion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Minion> minions = new ArrayList<>();
    FrameLayout main_layout;
    Handler handler;
    Context ctx;
    HighScore highScoreToRegister;
    List<HighScore> highscores;
    Random rand;
    private final int MAX_REGISTED_HIGHSCORE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        main_layout = (FrameLayout)findViewById(R.id.main_layout);
        handler = new Handler();

        rand = new Random();
        minions.add(new Minion(this, rand, 1));
        minions.add(new Minion(this, rand, 2));
        minions.add(new Minion(this, rand, 5));

        for(Minion m : minions)
            main_layout.addView(m);

        MoveMinion moveMinion = new MoveMinion(this, handler, minions);
        handler.post(moveMinion);
        gameOver();

    }

    private void gameOver() {
        //Gère l'ajout et la mise à jour de la BD pour les scores
        gestionHighScore();
    }

    private void gestionHighScore() {
        highscores = HighScoreManager.getAll(ctx);

        int score = rand.nextInt(100);

        int i = 0;
        //Si nous avons atteint le nombre Maximal d'enregistrement dans la BD, alors on fait la mise à jour
        if(highscores.size() == MAX_REGISTED_HIGHSCORE) {
            while(i < highscores.size() && highscores.get(i).getScore() > score)
                i++;

            if(i < highscores.size())
                highScoreToRegister = new HighScore(highscores.get(i).getId(), score, "");
        }
        //Sinon on l'ajoute
        else {
            highScoreToRegister = new HighScore(score, "");
        }

        //S'il le score est plus grand que Un déjà présent ou qu'il reste de la place dans la BD
        //Alors on propose le choix à l'utilisateur s'il veut le sauvegarder
        if(highScoreToRegister != null) {
            HighScoreDialog dialog = new HighScoreDialog(ctx, score);
            dialog.setPositiveListener(new HighScoreDialog.PositiveListener() {
                @Override
                public void onPositiveClick(String name) {
                    highScoreToRegister.setName(name);
                    addScoreToHighScore();
                }
            });

            dialog.setNegativeListener(new HighScoreDialog.NegativeListener() {
                @Override
                public void onNegativeClick() {
                    highScoreToRegister = null;
                }
            });

            dialog.show();
        }
    }

    private void addScoreToHighScore() {
        System.out.println(highScoreToRegister.toString());
        try {
            if(highScoreToRegister.getId() == -1)
                HighScoreManager.add(ctx, highScoreToRegister);
            else
                HighScoreManager.update(ctx, highScoreToRegister);
        }
        catch(NullPointerException e) {
            Log.e("Exception", "HighscoreToRegister est null (dans la méthode AddScoreToHighScore");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


}
