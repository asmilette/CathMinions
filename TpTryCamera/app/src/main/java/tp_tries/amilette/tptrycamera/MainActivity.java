package tp_tries.amilette.tptrycamera;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import tp_tries.amilette.tptrycamera.Vue.CameraView;

public class MainActivity extends AppCompatActivity {

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

    //*****************OnCreate**************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THREAD
        ctx = this;
        handler = new Handler();
        //fl = (FrameLayout)findViewById(R.id.camera_view);
       // cameraThread = new CameraThread(ctx, fl, handler);
       // handler.post(cameraThread);


        //*************Min qui bouge********************
        min = (ImageView) findViewById(R.id.min);
        min.setBackgroundResource(R.drawable.anim_img_multi);
        multi = (AnimationDrawable) min.getBackground();
        multi.start(); // gif


        main = (ImageView)findViewById(R.id.img_vac);


        //****cLICK SUR LA MAIN
        vib = (Vibrator) this.ctx.getSystemService(Context.VIBRATOR_SERVICE);
        ImageButton main = (ImageButton)findViewById(R.id.img_vac);




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String msg = null;
        int x;
        int y ;

        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();

                Toast.makeText(ctx,  "x: " +x + "y: " + y, Toast.LENGTH_LONG).show();
                AnimationSet animationSet = new AnimationSet(true);

                TranslateAnimation a = new TranslateAnimation(0,-x,0, -y);
                a.setDuration(4000);
                animationSet.addAnimation(a);
                main.startAnimation(a);
                main.setVisibility(View.VISIBLE);
                // main.layout(x, y, x+main.getWidth(), y + main.getHeight());
                }


        return true;
    }


}
