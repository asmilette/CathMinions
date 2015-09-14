package tp_tries.amilette.tptrycamera;

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
import tp_tries.amilette.tptrycamera.Vue.CameraView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

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

    //*****************OnCreate**************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        gd = new GestureDetector(ctx, this);
        //THREAD

        handler = new Handler();
<<<<<<< HEAD
        fl = (FrameLayout) findViewById(R.id.camera_view);
        cameraThread = new CameraThread(ctx, fl, handler);
        handler.post(cameraThread);
=======
        //fl = (FrameLayout)findViewById(R.id.camera_view);
       // cameraThread = new CameraThread(ctx, fl, handler);
       // handler.post(cameraThread);
>>>>>>> 0e8500f043efe33d70ecdf9feb99a9a04d5b5925


        //*************Min qui bouge********************
        min = (ImageView) findViewById(R.id.min);
        min.setBackgroundResource(R.drawable.anim_img_multi);
        multi = (AnimationDrawable) min.getBackground();
        multi.start(); // gif

        //******Lancement main
        main = (ImageView) findViewById(R.id.img_vac);
        anim = AnimationUtils.loadAnimation(this, R.anim.anim_fire_fox);


        //****cLICK SUR LA MAIN
        vib = (Vibrator) this.ctx.getSystemService(Context.VIBRATOR_SERVICE);
<<<<<<< HEAD
        ImageButton imgVac = (ImageButton) findViewById(R.id.img_vac);
        imgVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(2000);

=======
        ImageButton main = (ImageButton)findViewById(R.id.img_vac);
>>>>>>> 0e8500f043efe33d70ecdf9feb99a9a04d5b5925




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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        gd.onTouchEvent(ev);
        int x1 = (int)ev.getX(0);
        int y1 = (int)ev.getY(0);
        vib.vibrate(2000);
        main.layout(x1, y1, x1 + main.getWidth(), y1 + main.getWidth());

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
