package tp.android.catchMinion;

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

import tp.android.catchMinion.Thread.CatchThread;
import tp.android.catchMinion.Thread.CreatingMinion;
import tp.android.catchMinion.Thread.HandThread;
import tp.android.catchMinion.Thread.MinionsPrison;
import tp.android.catchMinion.Thread.MovingMinion;
import tp.android.catchMinion.Vue.CameraView;
import tp.android.catchMinion.entite.HighScore;
import tp.android.catchMinion.entite.HighScoreDialog;
import tp.android.catchMinion.entite.Minion;
import tp.android.catchMinion.entite.OnFinalDestination;
import tp.android.catchMinion.manager.HighScoreManager;

public class GameActivity extends Activity
        implements CreatingMinion.OnTooMuchMinionsListener
                    , CreatingMinion.OnCreatedMinionListener {

    //**********ATTRIBUTS*****************

    private Context ctx;
    private Handler handler;

    private FrameLayout fl;
    private FrameLayout ff;
    private FrameLayout prison;//FrameLayout pour la prison

    private Button btn_bg, btn_menu, btn_help;

    private ImageView img_bg;

    //Utilisé poru les Minions
    private ArrayList<Minion> minions;
    private HighScore highScoreToRegister;
    private List<HighScore> highscores;
    private Random rand;
    private final int MAX_REGISTED_HIGHSCORE = 10;
    private final int MAX_ALLOWED_MINIONS_IN_GAME = 15;
    private final int DELAY1_CREATING_MINION = 3500;
    private final int DELAY2_CREATING_MINION = 7000;
    private int score = 0;

    //Thread
    private CatchThread c;
    private MovingMinion movingMinion;
    private CreatingMinion creatingMinion;
    private HandThread p;

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
        prison = (FrameLayout) findViewById(R.id.prison);

        //*******Thread Camera
        fl.addView(new CameraView(ctx));
        //*****Minion*****
        initMinions();
        //*****Thread Main****
        p = new HandThread(this, handler);
        //Creation objet thread de la CatchThread (catch)
        c = new CatchThread(ctx, handler);
        /***** Minion ******/
        ff.addView(p);
        ff.addView(c);

        p.setDestFinale(new OnFinalDestination() {
            @Override
            public void ActionPerformed() {
            }

            @Override
            public void ActionPerformed(int x, int y) {
                int i = 0;
                while (i < minions.size()) {
                    Minion minion = minions.get(i);
                    if (minion.isAlive() && x >= minion.getLeft()
                            && x < minion.getWidth() + minion.getLeft()
                            && y >= minion.getTop()
                            && y < minion.getTop() + minion.getHeight()) {

                        minion.setIsAlive(false);
                        score += minion.getPoints();
                        ff.removeView(minion);
                        minions.remove(minion);
                        creatingMinion.setCreationDelay(DELAY2_CREATING_MINION);
                        p.setVisibility(View.INVISIBLE);
                        p.setEnabled(false);
                        c.setVisibility(View.VISIBLE);
                        c.startCatch(x, y);

                        c.setDestFinale(new OnFinalDestination() {
                            @Override
                            public void ActionPerformed() {
                                prison.addView(new MinionsPrison(ctx));
                                creatingMinion.setCreationDelay(DELAY1_CREATING_MINION);
                                c.setVisibility(View.INVISIBLE);
                                p.setVisibility(View.VISIBLE);
                                p.setEnabled(true);
                            }

                            @Override
                            public void ActionPerformed(int x, int y) {
                            }
                        });
                    }
                    i++;
                }
            }
        });

        //***************MENU******************
        btn_menu = (Button) findViewById(R.id.btn_menu);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //*****************BACKGROUND CHANGE**********
        btn_bg = (Button) findViewById(R.id.bg);
        btn_help = (Button) findViewById(R.id.help);
        //*****img
        img_bg = (ImageView) findViewById(R.id.bg_autre);

        btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREER BUILDER
                AlertDialog.Builder build = new AlertDialog.Builder(ctx);

                build.setTitle(R.string.title_dialog_background);
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

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    /*--------------------- David -------------------*/

    private void initMinions() {
        minions = new ArrayList<>();
        rand = new Random();
        //La création des minions
        creatingMinion = new CreatingMinion(ctx, handler, ff, minions, MAX_ALLOWED_MINIONS_IN_GAME, rand);
        creatingMinion.setCreationDelay(DELAY1_CREATING_MINION);
        //On ajout l'écouteur pour gérer l'évènement lorsqu'il y a trop de minions
        creatingMinion.setOnTooMuchMinionsListener(this);
        //On ajout l'écouteur pour gérer l'évènement lorsqu'il y a un minion de créer
        creatingMinion.setOnCreatedMinionListener(this);
        //le déplacements des minions
        movingMinion = new MovingMinion(this, handler, minions);
    }

    private void gameOver(boolean isSuccess) {
        //Gère l'ajout et la mise à jour de la BD pour les scores
        stopThread();
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

    private void stopThread() {
        movingMinion.setIsAlive(false);
        creatingMinion.setIsAlive(false);
        p.setIsAlive(false);
        c.setIsAlive(false);
    }

    private void startThread() {
        movingMinion.setIsAlive(true);
        creatingMinion.setIsAlive(true);
        p.setIsAlive(true);
        c.setIsAlive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Désactivation des threads
        stopThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Ré-activation des threads
        startThread();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("Restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Start");
    }

    @Override
    public void onTooMuchMinions() {
        gameOver(false);
    }

    @Override
    public void onCreatedMinion() {
        if(p != null) {
            p.bringToFront();
            p.invalidate();
        }
    }
}

