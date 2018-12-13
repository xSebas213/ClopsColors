package sebastiangames.clopscolors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.IBinder;

public class Musica extends Service {
    protected MediaPlayer fondo;

    @Override
    public void onCreate() {
        super.onCreate();
        fondo = MediaPlayer.create(this, R.raw.fondo);
        fondo.setLooping(true);
        fondo.setVolume(0.4f, 0.4f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fondo.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fondo.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
