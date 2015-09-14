package tp_tries.amilette.tptrycamera;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import tp_tries.amilette.tptrycamera.Thread.MainThread;

/**
 * Created by Anne-Sophie on 2015-09-13.
 */

public class HandActivity extends Activity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MainThread p = new MainThread(this, handler);
        setContentView(p);

        handler.post(p);

    }


}