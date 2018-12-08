package sebastiangames.clopscolors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class NivelesActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int efecto, intents;
    private FrameLayout tutorial, niveles1, niveles2, niveles3, home, info, ventanaInfo, atrasInfo,
            explo, mitad, tiempo, vidas;
    private VideoView videoView;
    private MediaController mediaController;
    private Boolean aBoolean, sonidosSi, musicaSi, salir;
    private Handler handler;
    private TextView explicacion;
    private Animation terceraAnimacion, cuartaAnimacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles);
        Random random;
        FrameLayout iconoInicio;
        Typeface normalita, negrita;
        Animation primeraAnimacion, segundaAnimacion, sextaAnimacion, septimaAnimacion, octavaAnimacion;
        TextView textoFacil, textoNormal, textoDificil, textoExperto, textoInfo;
        int[] colores, seleccionados;
        AdView adView;
        SharedPreferences datos;

        adView = findViewById(R.id.adViewNiveles);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);
        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
                .build();
        efecto = soundPool.load(this, R.raw.efecto, 1);
        intents = soundPool.load(this, R.raw.intents, 1);

        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();

        normalita = Typeface.createFromAsset(getAssets(), "fuentes/normal.otf");
        negrita = Typeface.createFromAsset(getAssets(), "fuentes/negrita.otf");
        sonidosSi = datos.getBoolean("SONIDOS", true);
        musicaSi = datos.getBoolean("MUSICA", true);

        random = new Random(System.currentTimeMillis());
        aBoolean = false;
        handler = new Handler();
        colores = new int[5];
        seleccionados = new int[2];

        videoView = findViewById(R.id.explicacionVideo);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        colores[0] = R.drawable.rosado;
        colores[1] = R.drawable.verde;
        colores[2] = R.drawable.amarillo;
        colores[3] = R.drawable.morado;
        colores[4] = R.drawable.cyan;

        iconoInicio = findViewById(R.id.iconoNiveles);
        home = findViewById(R.id.homiNiveles);
        info = findViewById(R.id.infoNiveles);
        tutorial = findViewById(R.id.nivel1);
        niveles1 = findViewById(R.id.nivel2);
        niveles2 = findViewById(R.id.nivel3);
        niveles3 = findViewById(R.id.nivel4);
        ventanaInfo = findViewById(R.id.informacionNiveles);
        atrasInfo = findViewById(R.id.atrasInfoNiveles);

        explo = findViewById(R.id.explo);
        mitad = findViewById(R.id.mitad);
        tiempo = findViewById(R.id.tiempo);
        vidas = findViewById(R.id.vidas);

        explo.setEnabled(false);
        mitad.setEnabled(false);
        vidas.setEnabled(false);
        tiempo.setEnabled(false);

        explicacion = findViewById(R.id.explicacion);

        textoInfo = findViewById(R.id.textoNivelesInfo);
        textoFacil = findViewById(R.id.textoFacil);
        textoNormal = findViewById(R.id.textoNormal);
        textoDificil = findViewById(R.id.textoDificil);
        textoExperto = findViewById(R.id.textoExperto);

        textoInfo.setTypeface(negrita);
        explicacion.setTypeface(normalita);
        textoFacil.setTypeface(normalita);
        textoNormal.setTypeface(normalita);
        textoDificil.setTypeface(normalita);
        textoExperto.setTypeface(normalita);

        primeraAnimacion = AnimationUtils.loadAnimation(this, R.anim.icononiveles);
        segundaAnimacion = AnimationUtils.loadAnimation(this, R.anim.mover2);
        terceraAnimacion = AnimationUtils.loadAnimation(this, R.anim.info);
        cuartaAnimacion = AnimationUtils.loadAnimation(this, R.anim.info2);
        sextaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar3);
        septimaAnimacion = AnimationUtils.loadAnimation(this, R.anim.mover);
        octavaAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion3);

        iconoInicio.startAnimation(primeraAnimacion);
        tutorial.startAnimation(octavaAnimacion);
        niveles1.startAnimation(septimaAnimacion);
        niveles2.startAnimation(sextaAnimacion);
        niveles3.startAnimation(segundaAnimacion);

        seleccionados[0] = colores[random.nextInt(5)];
        seleccionados[1] = colores[random.nextInt(5)];

        while (seleccionados[0] == seleccionados[1]){
            seleccionados[0] = colores[random.nextInt(5)];
        }


        explo.setBackgroundResource(R.drawable.morado);
        mitad.setBackgroundResource(R.drawable.verde);
        tiempo.setBackgroundResource(R.drawable.rosado);
        vidas.setBackgroundResource(R.drawable.cyan);


        home.setBackgroundResource(seleccionados[0]);
        info.setBackgroundResource(seleccionados[1]);

        atrasInfo.setEnabled(false);


        iconoInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir = false;
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                Intent intent = new Intent(NivelesActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                explicacion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                explicacion.setText(getString(R.string.expliMejoras));
                explicacion.setTextColor(getResources().getColor(R.color.blanco));

                home.setEnabled(false);
                info.setEnabled(false);
                tutorial.setEnabled(false);
                niveles1.setEnabled(false);
                niveles2.setEnabled(false);
                niveles3.setEnabled(false);

                explo.setEnabled(true);
                mitad.setEnabled(true);
                vidas.setEnabled(true);
                tiempo.setEnabled(true);


                ventanaInfo.startAnimation(terceraAnimacion);

                videoView.setEnabled(false);
                videoView.setVisibility(View.INVISIBLE);
                mediaController.setVisibility(View.INVISIBLE);

                atrasInfo.setEnabled(true);
                atrasInfo.setVisibility(View.VISIBLE);
            }
        });
        atrasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                home.setEnabled(true);
                info.setEnabled(true);
                tutorial.setEnabled(true);
                niveles1.setEnabled(true);
                niveles2.setEnabled(true);
                niveles3.setEnabled(true);

                explo.setEnabled(false);
                mitad.setEnabled(false);
                vidas.setEnabled(false);
                tiempo.setEnabled(false);

                ventanaInfo.startAnimation(cuartaAnimacion);
                videoView.setEnabled(false);
                mediaController.setVisibility(View.INVISIBLE);
                atrasInfo.setEnabled(false);
                atrasInfo.setVisibility(View.INVISIBLE);
            }
        });

        explo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explicacion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.2));
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                videoView.setEnabled(true);
                videoView.setVisibility(View.VISIBLE);
                mediaController.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/" +R.raw.explo));
                videoView.start();
                explicacion.setText(getString(R.string.infoExplicacionExplo));
                explicacion.setTextColor(getResources().getColor(R.color.colorQuintaryClick));
            }
        });
        mitad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explicacion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.2));
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                videoView.setEnabled(true);
                videoView.setVisibility(View.VISIBLE);
                mediaController.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/" +R.raw.mitad));
                videoView.start();
                explicacion.setText(getString(R.string.infoExplicacionMitad));
                explicacion.setTextColor(getResources().getColor(R.color.colorTercearyClick));
            }
        });
        vidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explicacion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.2));
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                videoView.setEnabled(true);
                videoView.setVisibility(View.VISIBLE);
                mediaController.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/" +R.raw.vidas));
                videoView.start();
                explicacion.setText(getString(R.string.infoExplicacionVidas));
                explicacion.setTextColor(getResources().getColor(R.color.azulFinal));
            }
        });
        tiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explicacion.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.2));
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                videoView.setEnabled(true);
                videoView.setVisibility(View.VISIBLE);
                mediaController.setVisibility(View.VISIBLE);
                videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/" +R.raw.tiempo));
                videoView.start();
                explicacion.setText(getString(R.string.infoExplicacionTiempo));
                explicacion.setTextColor(getResources().getColor(R.color.colorSecundaryClick));
            }
        });
    }

    public void goMain(View view) {
        salir = false;
        if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
        int nivelTotal = 0;
        if(niveles1 == view) nivelTotal = 1;
        if(niveles2 == view) nivelTotal = 2;
        if(niveles3 == view) nivelTotal = 3;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NIVEL", nivelTotal);
        intent.putExtra("COMPETENCIA", false);
        startActivity(intent);
    }

    public void tutorial(View view) {
        if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
        Intent intent = new Intent(NivelesActivity.this, TutoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (musicaSi) startService(new Intent(this, Musica.class));
        salir = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (salir) stopService(new Intent(this, Musica.class));

    }

    public void onBackPressed(){
        if(aBoolean){
            soundPool.release();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(NivelesActivity.this, "Presiona de nuevo para salir", Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aBoolean = false;
                }
            }, 3000);
            aBoolean = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}