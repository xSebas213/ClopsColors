package sebastiangames.clopscolors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class TutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        VideoView videoView;
        ImageView cerrar;
        SharedPreferences datos;
        final boolean sonidosSi;
        final int efecto, intents;
        final SoundPool soundPool;
        MediaController mediaController;
        videoView = findViewById(R.id.videoTuto);
        cerrar = findViewById(R.id.cerrarTuto);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/" +R.raw.video));
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);
        sonidosSi = datos.getBoolean("SONIDOS", true);
        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
                .build();
        efecto = soundPool.load(this, R.raw.efecto, 1);
        intents = soundPool.load(this, R.raw.intents, 1);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                Intent intent = new Intent(TutoActivity.this, NivelesActivity.class);
                startActivity(intent);
                Runtime.getRuntime().gc();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (sonidosSi) soundPool.play(intents, 1,1,1, 0, 1);
                Intent intent = new Intent(TutoActivity.this, NivelesActivity.class);
                startActivity(intent);
                Runtime.getRuntime().gc();
            }
        });
    }
}
