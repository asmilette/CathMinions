package tp_tries.amilette.tptrycamera.Thread;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.app.Activity;

import tp_tries.amilette.tptrycamera.R;
import tp_tries.amilette.tptrycamera.Vue.CameraView;

/**
 * Created by amilette on 2015-09-11.
 */
public class CameraThread   implements Runnable {
    Camera mCamera = null;
    CameraView mCameraView = null;
    Handler handler;
    Context ctx;
    FrameLayout camera_view;

    public CameraThread(Context context, FrameLayout fl, Handler handler){
        this.handler = handler;
        this.ctx = context;
        camera_view = fl;

    }




    @Override
    public void run() {
        try{
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new CameraView(ctx, mCamera);//create a SurfaceView to show camera data
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

    }
}
