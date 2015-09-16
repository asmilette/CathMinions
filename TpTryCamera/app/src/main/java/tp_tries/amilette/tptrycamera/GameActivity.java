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
import java.util.Random;

import tp_tries.amilette.tptrycamera.Thread.CameraThread;
import tp_tries.amilette.tptrycamera.Thread.CatchThread;
import tp_tries.amilette.tptrycamera.Thread.HandThread;
import tp_tries.amilette.tptrycamera.Thread.MoveMinion;
import tp_tries.amilette.tptrycamera.entite.Minion;
import tp_tries.amilette.tptrycamera.entite.OnFinalDestination;

public class GameActivity extends Activity  {

    //**********ATTRIBUTS*****************

    Context ctx;
    Handler handler, handlerMoveMinion;

    CameraThread cameraThread;

    FrameLayout fl;
    FrameLayout ff;

    Button btn_bg;
    Button btn_quit;
    Button btn_menu;

    ImageView img_bg;

    //Utilisé poru les Minions
    ArrayList<Minion> minions;
    //utilisé pour la génération aléatoire de nombres
    //Exemple: Uitlisé pour la génération des coordonées de départ des minions
    Random rand;

    MoveMinion moveMinion;
    HandThread p;



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
        ff = (FrameLayout)findViewById(R.id.frame);

        //*******Thread Camera
        cameraThread = new CameraThread(ctx, fl, handler);
        handler.post(cameraThread);

        /***** Minion ******/
        initMinions();

        //*****Thread Main****
        p = new HandThread(this, handler);

        p.setDestFinale(new OnFinalDestination() {
            @Override
            public void ActionPerformed(int x, int y) {
                //parcour liste minioncollision
                //pour
                //System.out.println("Coucou");
                int i = 0;
                for(Minion minion : minions) {
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

                    if(minion.isAlive() && x >= minion.getLeft()
                            && x < minion.getWidth() + minion.getLeft()
                            && y >= minion.getTop()
                            && y < minion.getTop()+minion.getHeight()){
                        //Log.v("Minions", "Minions attrape");
                        minion.setIsAlive(false);

                        //Creation objet thread de la CatchThread (catch)
                        CatchThread c = new CatchThread(ctx, x, y, handler);
                        if(!c.getIsTerminer()){
                            //retirer HandThread p de la view
                            ff.removeView(p);
                            //ff.addView(catch);
                            ff.addView(c);
                        }
                        else{
                            // retirer catch du view
                            ff.removeView(c);
                            // remettre p dans view
                            ff.addView(p);
                        }
                    }

                }


                // if(catch.getIsTerminer(true)

            }
        });
        ff.addView(p);









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
        btn_bg = (Button)findViewById(R.id.bg);
        //*****img
        img_bg = (ImageView)findViewById(R.id.bg_autre);

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

    private void initMinions() {
        minions = new ArrayList<>();
        rand = new Random();

        minions.add(new Minion(ctx, rand, 1));
        minions.add(new Minion(ctx, rand, 2));
        minions.add(new Minion(ctx, rand, 5));

        for(Minion m : minions)
            ff.addView(m);

        handlerMoveMinion = new Handler();

        moveMinion = new MoveMinion(this, handler, minions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        moveMinion.setIsAlive(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        moveMinion.setIsAlive(true);
    }
}

