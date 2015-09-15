package tp_tries.amilette.tptrycamera.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import tp_tries.amilette.tptrycamera.R;


public class MyService extends Service {

    MediaPlayer player;
    Intent intentStart;
    Context ctx;
    boolean isStart = false;

    public MyService() {
        System.out.println("test");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        System.out.println("je suis dans onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("je suis dans onStatCommand");


        if (!isStart) {
            player = MediaPlayer.create(ctx, R.raw.minionwater);
            player.start();
        } else {
            player.stop();
        }
        isStart = !isStart;

        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("je suis dans onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
