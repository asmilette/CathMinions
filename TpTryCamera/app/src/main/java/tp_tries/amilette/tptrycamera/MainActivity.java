package tp_tries.amilette.tptrycamera;

import android.app.Activity;
import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import tp_tries.amilette.tptrycamera.Thread.CameraThread;
import tp_tries.amilette.tptrycamera.Thread.MainThread;
import tp_tries.amilette.tptrycamera.Vue.CameraView;

public class MainActivity extends Activity  {

    //**********ATTRIBUTS*****************
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private Vibrator vib;
    Context ctx;
    Handler handler;
    Animation anim;
    ImageView min;
    AnimationDrawable multi;
    ImageView lance;
    AnimationDrawable multi_lancement;
    ImageView main;
    CameraThread cameraThread;
    FrameLayout fl;
    GestureDetector gd;
    FrameLayout ff;

    //*****************OnCreate**************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        //THREAD

        handler = new Handler();

        fl = (FrameLayout) findViewById(R.id.camera_view);
        ff=(FrameLayout)findViewById(R.id.frame);
        cameraThread = new CameraThread(ctx, fl, handler);
        MainThread p = new MainThread(this, handler);
        ff.addView(p);
        handler.post(cameraThread);





        //*************Min qui bouge********************
        min = (ImageView) findViewById(R.id.min);
        min.setBackgroundResource(R.drawable.anim_img_multi);
        multi = (AnimationDrawable) min.getBackground();
        multi.start(); // gif

        //******Lancement main
        anim = AnimationUtils.loadAnimation(this, R.anim.anim_fire_fox);




    }
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                String msg = null;
                int x;
                int y;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getX();
                        y = (int) event.getY();

                        Toast.makeText(ctx, "x: " + x + "y: " + y, Toast.LENGTH_LONG).show();
                        AnimationSet animationSet = new AnimationSet(true);

                        TranslateAnimation a = new TranslateAnimation(0, -x, 0, -y);
                        a.setDuration(4000);
                        animationSet.addAnimation(a);
                        main.startAnimation(a);
                        main.setVisibility(View.VISIBLE);
                        // main.layout(x, y, x+main.getWidth(), y + main.getHeight());
                }


                return true;
            }


        }

