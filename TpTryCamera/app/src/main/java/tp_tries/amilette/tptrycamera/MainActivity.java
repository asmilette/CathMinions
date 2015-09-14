package tp_tries.amilette.tptrycamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import tp_tries.amilette.tptrycamera.Thread.CameraThread;
import tp_tries.amilette.tptrycamera.Thread.MainThread;
import tp_tries.amilette.tptrycamera.Vue.CameraView;

public class MainActivity extends Activity  {

    //**********ATTRIBUTS*****************

    Context ctx;
    Handler handler;

    CameraThread cameraThread;

    FrameLayout fl;
    FrameLayout ff;

   Button btn_bg;
    Button btn_quit;

    ImageView img_bg;



    //*****************OnCreate**************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        //THREAD

        handler = new Handler();

        //*****Layout Call
        fl = (FrameLayout) findViewById(R.id.camera_view);
        ff=(FrameLayout)findViewById(R.id.frame);

        //*******Thread Call
        cameraThread = new CameraThread(ctx, fl, handler);
        MainThread p = new MainThread(this, handler);
        ff.addView(p);

        handler.post(cameraThread);

        //********btn
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


        }

