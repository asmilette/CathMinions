package tp_tries.amilette.tptrycamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tp_tries.amilette.tptrycamera.Thread.CameraThread;
import tp_tries.amilette.tptrycamera.Thread.CatchThread;
import tp_tries.amilette.tptrycamera.Thread.CreatingMinion;
import tp_tries.amilette.tptrycamera.Thread.HandThread;
import tp_tries.amilette.tptrycamera.Thread.MovingMinion;
import tp_tries.amilette.tptrycamera.entite.HighScore;
import tp_tries.amilette.tptrycamera.entite.HighScoreDialog;
import tp_tries.amilette.tptrycamera.entite.Minion;
import tp_tries.amilette.tptrycamera.entite.OnFinalDestination;
import tp_tries.amilette.tptrycamera.manager.HighScoreManager;

public class GameActivity extends Activity implements CreatingMinion.OnTooMuchMinionsListener {

    //**********ATTRIBUTS*****************

    private Context ctx;
    private Handler handler;//, handlerMoveMinion;

    private FrameLayout fl;
    private FrameLayout ff;

    private Button btn_bg;
    private Button btn_quit;
    private Button btn_menu;

    private ImageView img_bg;

    //Utilisé poru les Minions
    private ArrayList<Minion> minions;
    //utilisé pour la génération aléatoire de nombres
    //Exemple: Uitlisé pour la génération des coordonées de départ des minions
    private HighScore highScoreToRegister;
    private List<HighScore> highscores;
    private Random rand;
    private final int MAX_REGISTED_HIGHSCORE = 10;
    private int score = 0;
    private boolean isGameOver = false;

    private Handler handlerCatch;
    private CatchThread c;
    private Boolean catchActiver;
    private Boolean catchTerminier;
    //Thread
    private MovingMinion movingMinion;
    private CreatingMinion creatingMinion;
    private HandThread p;
    private CameraThread cameraThread;

    //*****************OnCreate**************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);
        ctx = this;
        //THREAD
        handler = new Handler();

        //*****Layout Call
        fl = (FrameLayout) findViewById(R.id.camera_view);
        ff = (FrameLayout) findViewById(R.id.frame);

        //*******Thread Camera
        cameraThread = new CameraThread(ctx, fl, handler);
        handler.post(cameraThread);

        /***** Minion ******/
        initMinions();

        //*****Thread Main****
        p = new HandThread(this, handler);
        ff.addView(p);

        //TODO Il faut désactivé la main, lorsque le jeu est terminé
        p.setDestFinale(new OnFinalDestination() {
            @Override
            public void ActionPerformed() {

            }

            @Override
            public void ActionPerformed(int x, int y) {
                int i = 0;
                for (Minion minion : minions) {
                    i++;
                    //Log.v("Minions", "--------------------------------------------");
                    /*String str = String.format("No. %1$d,  X: %2$d, Y: %3$d, Width: %4$d, Height: %5$d"
                            ,i
                            ,minion.getLeft()
                            ,minion.getTop()
                            ,minion.getWidth()
                            ,minion.getHeight());
                    Log.v("Minions", "minion"+str);
                    String str2 = String.format("X: %1$d, Y: %2$d", x, y);
                    Log.v("Minions", "Main"+str2);*/

                    if (minion.isAlive() && x >= minion.getLeft()
                            && x < minion.getWidth() + minion.getLeft()
                            && y >= minion.getTop()
                            && y < minion.getTop() + minion.getHeight()) {


                        //Log.v("Minions", "Minions attrape");
                        minion.setIsAlive(false);
                        score += minion.getPoints();

                        //Creation objet thread de la CatchThread (catch)
                        c = new CatchThread(ctx, x, y, handler);
                        handlerCatch = handler;
                        p.setVisibility(View.INVISIBLE);
                        //ff.addView(catch);
                        ff.addView(c);
                        handler.post(c);

                        c.setDestFinale(new OnFinalDestination() {
                            @Override
                            public void ActionPerformed() {
                                p.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void ActionPerformed(int x, int y) {
                            }

                        });


                    }


                }
            }
        });

        //***************MENU******************
        btn_menu = (Button) findViewById(R.id.btn_menu);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        //*****************BACKGROUND CHANGE**********
        btn_bg = (Button) findViewById(R.id.bg);
        //*****img
        img_bg = (ImageView) findViewById(R.id.bg_autre);

        btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREER BUILDER
                AlertDialog.Builder build = new AlertDialog.Builder(ctx);

                build.setTitle("Background");
                build.setItems(R.array.bg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Resources res = getResources();
                        String[] bgs = res.getStringArray(R.array.bg);
                        switch (i) {
                            case (0):

                                img_bg.setVisibility(View.VISIBLE);
                                img_bg.setBackgroundResource(R.drawable.bg_simpson);
                                break;
                            case (1):
                                img_bg.setVisibility(View.VISIBLE);
                                img_bg.setBackgroundResource(R.drawable.bg_et);
                                break;
                            case (2):
                                img_bg.setVisibility(View.VISIBLE);
                                img_bg.setBackgroundResource(R.drawable.bg_shrek);

                                break;
                            case (3):
                                ff.setVisibility(View.VISIBLE);
                                img_bg.setVisibility(View.INVISIBLE);
                                break;

                        }
                    }
                });
                AlertDialog alert = build.create();
                alert.show();
            }
        });
    }

    /*--------------------- David -------------------*/

    private void initMinions() {
        minions = new ArrayList<>();
        rand = new Random();
        //La création des minions
        creatingMinion = new CreatingMinion(ctx, handler, ff, minions, rand);
        //On ajout l'écouteur pour gérer l'évènement lorsqu'il y a trop de minions
        creatingMinion.setOnTooMuchMinionsListener(this);
        //le déplacements des minions
        movingMinion = new MovingMinion(this, handler, minions);
    }

    private void gameOver(boolean isSuccess) {
        //Gère l'ajout et la mise à jour de la BD pour les scores
        gestionHighScore();
    }

    private void gestionHighScore() {
        highscores = HighScoreManager.getAll(ctx);

        int i = 0;
        //Si nous avons atteint le nombre Maximal d'enregistrement dans la BD, alors on fait la mise à jour
        if (highscores.size() == MAX_REGISTED_HIGHSCORE) {
            while (i < highscores.size() && highscores.get(i).getScore() > score)
                i++;

            if (i < highscores.size())
                highScoreToRegister = new HighScore(highscores.get(i).getId(), score, "");
        }
        //Sinon on l'ajoute
        else {
            highScoreToRegister = new HighScore(score, "");
        }

        //S'il le score est plus grand que Un déjà présent ou qu'il reste de la place dans la BD
        //Alors on propose le choix à l'utilisateur s'il veut le sauvegarder
        if (highScoreToRegister != null) {
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
        //System.out.println(highScoreToRegister.toString());
        if (highScoreToRegister.getId() == -1)
            HighScoreManager.add(ctx, highScoreToRegister);
        else
            HighScoreManager.update(ctx, highScoreToRegister);
    }

    @Override
    protected void onPause() {
        super.onPause();
        movingMinion.setIsAlive(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        movingMinion.setIsAlive(true);
    }

    @Override
    public void onTooMuchMinions() {
        gameOver(false);
        p.setIsAlive(false);
    }
}

