package tp.android.catchminion.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import tp.android.catchminion.R;


public class MyService extends Service {

    MediaPlayer player;
    Intent intentStart;
    Context ctx;
    boolean isStart = false;

        @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(player == null)
            player = MediaPlayer.create(ctx, R.raw.minionwater);
        if(!player.isPlaying())
            player.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void stopPlayer() {
        if(player != null) {
            player.stop();
            player.release();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
