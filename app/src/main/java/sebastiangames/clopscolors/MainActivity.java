package sebastiangames.clopscolors;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Vibrator vibrator;
    private SharedPreferences datos;
    private LinearLayout miLayout;
    private Toast toast;
    private int cuentica;
    private Dialog dialog;
    private CardView cardIntentos;
    private ObjectAnimator objectAnimator2;
    private View pulsadin;
    private InterstitialAd interstitialAd;
    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private int toque, efecto, intents, fallo, fin, cuentaSonido;
    private ImageView iconoPuntos, imagenToast;
    private int contador, vidas, nivel, colorPlay, segundos, cuenta, vueltas, seguidasMitad, seguidasVidas, vueltasGeneral, mejoraView, mejora;
    private int opcionRandom1, opcionRandom2, opcionRandom3, opcionRandom4, opcionRandom5, colorPuto1, opcionRandom6;
    private int color, color2, color6, numeroBotones, cuentaTiempo, cuentaFaltaExpo, intentos;
    private Random random, randomColor;
    private FrameLayout boton, boton2, boton3, boton4, boton5, boton6, lasVidas, botonAlerta, botonAlertaNo, botonAlertaSi, botonIntentos;
    private int[] colores, plays;
    private FrameLayout[] botonesPulsados;
    private FrameLayout play, siguiente, home;
    private CountDownTimer countDownTimer;
    private int pulsado, pulsado2, pulsado3, pulsado4, pulsado5, pulsado6;
    private Boolean[] pulsadoBoton, restarBoton;
    private Boolean aBoolean, enJuego, enTiempo, enExplo, sonidosSi, enMitad, enVida, enCompetencia, musicaSi, restar, salir, entraMejora;
    private TextView puntaje, vida, cronometro, textoToast, tituloAlerta, mensajeAlerta, textoBotonAlertaNo,
            textoBotonAlertaSi, cuentaRegre;
    private Handler handler;
    private Typeface negrita, normalita;
    private Animation primeraAnimacion, segundaAnimacion, terceraAnimacion, cuartaAnimacion, quintaAnimacion, sextaAnimacion, septimaAnimacion,
            octavaAnimacion, novenaAnimacion, decimaAnimacion, cuentaAnimacion;
    private int velocidadSalida, velocidadEntrada;
    private ImageView[] imageViews;

    private Runnable hilo1 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            competencia();
        }
    };
    private Runnable hilo2 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            primerNivel();
        }
    };
    private Runnable hilo3 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            segundoNivel();
        }
    };
    private Runnable hilo4 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            tercerNivel();
        }
    };
    private Runnable hilo5 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            cambiarSiguiente(random.nextInt(5)+1); cuenta();
        }
    };
    private Runnable hilo6 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() { randomColor1(random.nextInt(numeroBotones), randomColor.nextInt(5)); }
    };
    private Runnable hilo7 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() { randomColor2(random.nextInt(numeroBotones), colorPlay);
        }
    };
    private Runnable hilo72 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() { randomColor6(random.nextInt(numeroBotones), colorPlay);
        }
    };
    private Runnable hilo8 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor3(random.nextInt(numeroBotones));
        }
    };
    private Runnable hilo9 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor4(random.nextInt(numeroBotones));
        }
    };
    private Runnable hilo10 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor5(random.nextInt(numeroBotones));
        }
    };
    private Runnable hilo11 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor1(opcionRandom1,colorPuto1);
        }
    };
    private Runnable hilo12 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor2(opcionRandom2, color2);
        }
    };
    private Runnable hilo122 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor6(opcionRandom6, color6);
        }
    };
    private Runnable hilo13 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor3(opcionRandom3);
        }
    };
    private Runnable hilo14 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor4(opcionRandom4);
        }
    };
    private Runnable hilo15 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            randomColor5(opcionRandom5);
        }
    };
    private Runnable hilo16 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton.startAnimation(quintaAnimacion);
            boton.setAlpha((float) 0.99999);
            boton.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo6, velocidadSalida);
        }
    };
    private Runnable hilo17 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton2.startAnimation(quintaAnimacion);
            boton2.setAlpha((float) 0.99999);
            boton2.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo7, velocidadSalida);
        }
    };
    private Runnable hilo172 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton6.startAnimation(quintaAnimacion);
            boton6.setAlpha((float) 0.99999);
            boton6.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo72, velocidadSalida);
        }
    };
    private Runnable hilo18 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton3.startAnimation(quintaAnimacion);
            boton3.setAlpha((float) 0.99999);
            boton3.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo8, velocidadSalida);
        }
    };
    private Runnable hilo19 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton4.startAnimation(quintaAnimacion);
            boton4.setAlpha((float) 0.99999);
            boton4.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo9, velocidadSalida);
        }
    };
    private Runnable hilo20 = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            boton5.startAnimation(quintaAnimacion);
            boton5.setAlpha((float) 0.99999);
            boton5.setBackgroundResource(R.drawable.defecto);
            handler.postDelayed(hilo10, velocidadSalida);
        }
    };
    private Runnable hilo21 = new Runnable() {
        @Override
        public void run() {
            pulsadin.startAnimation(cuartaAnimacion);
            pulsadin.setBackgroundResource(R.drawable.defecto);
        }
    };
    private Runnable hilo22 = new Runnable() {
        @Override
        public void run() {
            finalizar();
            porDefecto();
        }
    };
    private Runnable hilo23 = new Runnable() {
        @Override
        public void run() {
            numeroBotones = 12;
            botonesPulsados[0] = findViewById(R.id.boton2);
            botonesPulsados[1] = findViewById(R.id.boton3);
            botonesPulsados[2] = findViewById(R.id.boton4);
            botonesPulsados[3] = findViewById(R.id.boton5);
            botonesPulsados[4] = findViewById(R.id.boton8);
            botonesPulsados[5] = findViewById(R.id.boton9);
            botonesPulsados[6] = findViewById(R.id.boton10);
            botonesPulsados[7] = findViewById(R.id.boton11);
            botonesPulsados[8] = findViewById(R.id.boton14);
            botonesPulsados[9] = findViewById(R.id.boton15);
            botonesPulsados[10] = findViewById(R.id.boton16);
            botonesPulsados[11] = findViewById(R.id.boton17);

            botonesPulsados[12] = findViewById(R.id.boton1);
            botonesPulsados[12].setEnabled(false);
            botonesPulsados[12].setVisibility(View.INVISIBLE);
            botonesPulsados[13] = findViewById(R.id.boton6);
            botonesPulsados[13].setEnabled(false);
            botonesPulsados[13].setVisibility(View.INVISIBLE);
            botonesPulsados[14] = findViewById(R.id.boton7);
            botonesPulsados[14].setEnabled(false);
            botonesPulsados[14].setVisibility(View.INVISIBLE);
            botonesPulsados[15] = findViewById(R.id.boton12);
            botonesPulsados[15].setEnabled(false);
            botonesPulsados[15].setVisibility(View.INVISIBLE);
            botonesPulsados[16] = findViewById(R.id.boton13);
            botonesPulsados[16].setEnabled(false);
            botonesPulsados[16].setVisibility(View.INVISIBLE);
            botonesPulsados[17] = findViewById(R.id.boton18);
            botonesPulsados[17].setEnabled(false);
            botonesPulsados[17].setVisibility(View.INVISIBLE);
            empiezaNiveles();
        }
    };
    private Runnable hilo25 = new Runnable() {
        @Override
        public void run() {
            goExplo();
        }
    };
    private Runnable hilo26 = new Runnable() {
        @Override
        public void run() {
            for(int i=0; i<numeroBotones;i++){
                botonesPulsados[i].setEnabled(true);
            }
        }
    };
    private Runnable hilo27 = new Runnable() {
        @Override
        public void run() {
            finalizar();
            porDefectoMitad();
        }
    };
    private Runnable hilo28 = new Runnable() {
        @Override
        public void run() {
            empiezaNiveles();
            cambiarSiguiente(random.nextInt(5)+1);
            cuenta();
        }
    };
    private Runnable hilo29 = new Runnable() {
        @Override
        public void run() {
            restar = true;
        }
    };
    private Runnable hilo30 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado] = true;
        }
    };
    private Runnable hilo31 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado2] = true;
        }
    };
    private Runnable hilo32 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado3] = true;
        }
    };
    private Runnable hilo33 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado4] = true;
        }
    };
    private Runnable hilo34 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado5] = true;
        }
    };
    private Runnable hilo35 = new Runnable() {
        @Override
        public void run() {
            restarBoton[pulsado6] = true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        handler = new Handler();

        primeraAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar);
        segundaAnimacion = AnimationUtils.loadAnimation(this, R.anim.desaparecer2);
        terceraAnimacion = AnimationUtils.loadAnimation(this, R.anim.cambio1);
        cuartaAnimacion = AnimationUtils.loadAnimation(this, R.anim.cambio2);
        quintaAnimacion = AnimationUtils.loadAnimation(this, R.anim.cambio3);
        sextaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar2);
        septimaAnimacion = AnimationUtils.loadAnimation(this, R.anim.cambio4);
        octavaAnimacion = AnimationUtils.loadAnimation(this, R.anim.desaparecer3);
        novenaAnimacion = AnimationUtils.loadAnimation(this, R.anim.desaparecer);
        decimaAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion5);
        cuentaAnimacion = AnimationUtils.loadAnimation(this, R.anim.cuenta);

        normalita = Typeface.createFromAsset(getAssets(), "fuentes/normal.otf");
        negrita = Typeface.createFromAsset(getAssets(), "fuentes/negrita.otf");

        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);

        enJuego = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nivel = extras.getInt("NIVEL");
            enCompetencia = extras.getBoolean("COMPETENCIA");
            if (enCompetencia) intentos = Integer.parseInt(Objects.requireNonNull(extras.getString("INTENTOS")));
        }

        musicaSi = datos.getBoolean("MUSICA", true);
        sonidosSi = datos.getBoolean("SONIDOS", true);


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alerta);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        play = findViewById(R.id.play);
        home = findViewById(R.id.homeMain);

        botonAlerta = dialog.findViewById(R.id.botonAlerta);
        botonAlertaNo = dialog.findViewById(R.id.botonNo);
        botonAlertaSi = dialog.findViewById(R.id.botonSi);
        botonIntentos = dialog.findViewById(R.id.botonIntentos);

        tituloAlerta = dialog.findViewById(R.id.tituloAlerta);
        mensajeAlerta = dialog.findViewById(R.id.mensajeAlerta);
        textoBotonAlertaNo = dialog.findViewById(R.id.textoBotonNo);
        textoBotonAlertaSi = dialog.findViewById(R.id.textoBotonSi);

        tituloAlerta.setTypeface(negrita);
        mensajeAlerta.setTypeface(normalita);
        textoBotonAlertaNo.setTypeface(negrita);
        textoBotonAlertaSi.setTypeface(negrita);

        soundPool = new SoundPool.Builder().setMaxStreams(20)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
                .build();
        toque = soundPool.load(this, R.raw.toque, 1);
        efecto = soundPool.load(this, R.raw.efecto, 1);
        intents = soundPool.load(this, R.raw.intents, 1);
        fallo = soundPool.load(this, R.raw.fallo, 1);
        fin = soundPool.load(this, R.raw.fin, 1);
        cuentaSonido = soundPool.load(this, R.raw.cuenta, 1);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.screen_main));
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());


        botonAlertaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                datos.edit().putBoolean("REINI", true).apply();
                dialog.dismiss();
                if (enCompetencia) {
                    salir = false;
                    Intent intent = new Intent(MainActivity.this, CompetenciaActivity.class);
                    startActivity(intent);
                }else {
                    salir = false;
                    Intent intent = new Intent(MainActivity.this, NivelesActivity.class);
                    startActivity(intent);
                }
            }
        });
        botonAlertaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datos.edit().putBoolean("REINI", true).apply();
                datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                inicio();
                play.setEnabled(true);
                home.setEnabled(true);
                dialog.dismiss();
            }
        });

        cuentaRegre = findViewById(R.id.regresiva);
        cuentaRegre.setTypeface(negrita);

        cuentaAnimacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cuentica--;
                cuentaRegre.setText(String.valueOf(cuentica));
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (cuentica != 1) {
                    cuentaRegre.startAnimation(cuentaAnimacion);
                }
                if (cuentica == 1) {
                    stopService(new Intent(MainActivity.this, Musica.class));
                    if (musicaSi) mediaPlayer.start();
                    paraAnimacion(primeraAnimacion);
                    enJuego = true;
                    cuentica = 4;
                    cuentaAnimacion.cancel();
                    handler.postDelayed(hilo28, 500);
                    cuentaRegre.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    public void inicio(){
        if (musicaSi && datos.getBoolean("REINI", true))startService(new Intent(this, Musica.class));

        View viewToast;
        int start;
        int[] iconos;
        float[] alphas;

        mediaPlayer = MediaPlayer.create(this, R.raw.partida);
        mediaPlayer.setVolume(0.7f, 0.7f);
        mediaPlayer.setLooping(true);

        miLayout = findViewById(R.id.elLayout);
        cronometro = findViewById(R.id.cronometro);
        cronometro.setTypeface(negrita);
        puntaje = findViewById(R.id.puntaje);
        puntaje.setTypeface(negrita);
        vida = findViewById(R.id.vidas);
        vida.setTypeface(negrita);
        iconoPuntos = findViewById(R.id.puntosIcono);
        lasVidas = findViewById(R.id.lasVidas);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        viewToast = inflater.inflate(R.layout.toast, null);
        toast = new Toast(MainActivity.this);
        toast.setView(viewToast);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        colores = new int[5];
        iconos = new int[5];
        alphas = new float[5];
        plays = new int[5];
        imageViews = new ImageView[4];
        botonesPulsados = new FrameLayout[18];
        pulsadoBoton = new Boolean[18];
        restarBoton = new Boolean[18];

        restarBoton[0] = true;
        restarBoton[1] = true;
        restarBoton[2] = true;
        restarBoton[3] = true;
        restarBoton[4] = true;
        restarBoton[5] = true;
        restarBoton[6] = true;
        restarBoton[7] = true;
        restarBoton[8] = true;
        restarBoton[9] = true;
        restarBoton[10] = true;
        restarBoton[11] = true;
        restarBoton[12] = true;
        restarBoton[13] = true;
        restarBoton[14] = true;
        restarBoton[15] = true;
        restarBoton[16] = true;
        restarBoton[17] = true;

        botonesPulsados[0] = findViewById(R.id.boton1);
        botonesPulsados[0].setVisibility(View.VISIBLE);
        botonesPulsados[1] = findViewById(R.id.boton2);
        botonesPulsados[1].setVisibility(View.VISIBLE);
        botonesPulsados[2] = findViewById(R.id.boton3);
        botonesPulsados[2].setVisibility(View.VISIBLE);
        botonesPulsados[3] = findViewById(R.id.boton4);
        botonesPulsados[3].setVisibility(View.VISIBLE);
        botonesPulsados[4] = findViewById(R.id.boton5);
        botonesPulsados[4].setVisibility(View.VISIBLE);
        botonesPulsados[5] = findViewById(R.id.boton6);
        botonesPulsados[5].setVisibility(View.VISIBLE);
        botonesPulsados[6] = findViewById(R.id.boton7);
        botonesPulsados[6].setVisibility(View.VISIBLE);
        botonesPulsados[7] = findViewById(R.id.boton8);
        botonesPulsados[7].setVisibility(View.VISIBLE);
        botonesPulsados[8] = findViewById(R.id.boton9);
        botonesPulsados[8].setVisibility(View.VISIBLE);
        botonesPulsados[9] = findViewById(R.id.boton10);
        botonesPulsados[9].setVisibility(View.VISIBLE);
        botonesPulsados[10] = findViewById(R.id.boton11);
        botonesPulsados[10].setVisibility(View.VISIBLE);
        botonesPulsados[11] = findViewById(R.id.boton12);
        botonesPulsados[11].setVisibility(View.VISIBLE);
        botonesPulsados[12] = findViewById(R.id.boton13);
        botonesPulsados[12].setVisibility(View.VISIBLE);
        botonesPulsados[13] = findViewById(R.id.boton14);
        botonesPulsados[13].setVisibility(View.VISIBLE);
        botonesPulsados[14] = findViewById(R.id.boton15);
        botonesPulsados[14].setVisibility(View.VISIBLE);
        botonesPulsados[15] = findViewById(R.id.boton16);
        botonesPulsados[15].setVisibility(View.VISIBLE);
        botonesPulsados[16] = findViewById(R.id.boton17);
        botonesPulsados[16].setVisibility(View.VISIBLE);
        botonesPulsados[17] = findViewById(R.id.boton18);
        botonesPulsados[17].setVisibility(View.VISIBLE);


        colores[0] = R.drawable.rosado;
        colores[1] = R.drawable.verde;
        colores[2] = R.drawable.amarillo;
        colores[3] = R.drawable.morado;
        colores[4] = R.drawable.cyan;

        plays[0] = R.drawable.play1;
        plays[1] = R.drawable.play2;
        plays[2] = R.drawable.play3;
        plays[3] = R.drawable.play4;
        plays[4] = R.drawable.play5;

        iconos[0] = R.drawable.puntosrosado;
        iconos[1] = R.drawable.puntosverde;
        iconos[2] = R.drawable.puntosamarillo;
        iconos[3] = R.drawable.puntosmorado;
        iconos[4] = R.drawable.puntosazul;

        textoToast =  viewToast.findViewById(R.id.textoToast);
        cardIntentos = viewToast.findViewById(R.id.cardIntentos);
        imagenToast = viewToast.findViewById(R.id.imagenToast);
        textoToast.setTypeface(normalita);

        alphas[0] = (float)0.9;
        alphas[1] = (float)0.99;
        alphas[2] = (float)0.999;
        alphas[3] = (float)0.9999;
        alphas[4] = (float)0.99999;

        siguiente = findViewById(R.id.siguiente);

        imageViews[0] = findViewById(R.id.mejora1);
        imageViews[0].setVisibility(View.INVISIBLE);
        imageViews[1] = findViewById(R.id.mejora2);
        imageViews[1].setVisibility(View.INVISIBLE);
        imageViews[2] = findViewById(R.id.mejora3);
        imageViews[2].setVisibility(View.INVISIBLE);
        imageViews[3] = findViewById(R.id.mejora4);
        imageViews[3].setVisibility(View.INVISIBLE);

        enMitad = false;
        enVida = false;
        enExplo = false;
        enTiempo = false;
        entraMejora = false;
        aBoolean = false;
        restar = true;
        numeroBotones = 18;
        color = 0;
        color2 = 0;
        cuentica = 4;
        cuenta = 16000;
        random = new Random(System.currentTimeMillis());
        randomColor = new Random(System.currentTimeMillis());
        seguidasMitad = 0;
        seguidasVidas = 0;
        vueltas = 0;
        cuentaFaltaExpo = 16;
        velocidadEntrada = 1000;
        velocidadSalida = 500;
        vidas = 10;
        vueltasGeneral = 0;
        contador = 0;
        cuentaTiempo = 16;
        vida.setText(String.valueOf(vidas));
        puntaje.setText(String.valueOf(contador));

        cronometro.setText(getString(R.string.contador));

        primeraAnimacion.setRepeatCount(Animation.INFINITE);
        play.startAnimation(primeraAnimacion);
        home.setAlpha(1);
        start = random.nextInt(5);

        porDefecto();

        for(int i = 0; i<5; i++){
            if(start == i){
                play.setBackgroundResource(colores[i]);
                play.setAlpha(alphas[i]);
                home.setBackgroundResource(colores[i]);
                iconoPuntos.setImageResource(iconos[i]);
                colorPlay = colores[i];
            }
        }

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                cambiarSiguiente(random.nextInt(5)+1);
            }
        });

        imageViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                imageViews[0].setAlpha((float) 0.99999999);
                comprobarMejora(mejora);
            }
        });
        imageViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                imageViews[1].setAlpha((float) 0.99999999);
                comprobarMejora(mejora);
            }
        });
        imageViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                imageViews[2].setAlpha((float) 0.99999999);
                comprobarMejora(mejora);
            }
        });
        imageViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                imageViews[3].setAlpha((float) 0.99999999);
                comprobarMejora(mejora);
            }
        });
    }

    public void porDefecto(){
        for(int i=0; i<numeroBotones;i++){
            botonesPulsados[i].setAlpha((float) 0.99999);
            botonesPulsados[i].setEnabled(true);
            pulsadoBoton[i] = false;
            botonesPulsados[i].setBackgroundResource(R.drawable.defecto);
        }
    }
    public void porDefectoMitad(){
        for(int i=0; i<numeroBotones;i++){
            botonesPulsados[i].setAlpha((float) 0.99999);
            botonesPulsados[i].setEnabled(false);
            pulsadoBoton[i] = false;
            botonesPulsados[i].setBackgroundResource(R.drawable.defecto);
        }
        handler.postDelayed(hilo26, 200);
    }
    public void comprobarMejora(int mejora){
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0);
        objectAnimator2.setValues(x, y);
        objectAnimator2.setDuration(500);
        objectAnimator2.setRepeatCount(0);
        objectAnimator2.start();
        imageViews[mejoraView].setEnabled(false);

        switch (mejora){
            case 0:
                if (Build.VERSION.SDK_INT >= 23 && musicaSi) mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(0.9f));
                enTiempo = true;
                entraMejora = true;
                novenaAnimacion.setRepeatCount(Animation.INFINITE);
                miLayout.startAnimation(novenaAnimacion);
                velocidadEntrada = 1200;
                velocidadSalida = 700;
                break;
            case 1:
                octavaAnimacion.setFillAfter(true);
                handler.postDelayed(hilo27, 300);
                botonesPulsados[0].startAnimation(octavaAnimacion);
                botonesPulsados[6].startAnimation(octavaAnimacion);
                botonesPulsados[12].startAnimation(octavaAnimacion);
                botonesPulsados[5].startAnimation(octavaAnimacion);
                botonesPulsados[11].startAnimation(octavaAnimacion);
                botonesPulsados[17].startAnimation(octavaAnimacion);
                botonesPulsados[0].setEnabled(false);
                botonesPulsados[6].setEnabled(false);
                botonesPulsados[12].setEnabled(false);
                botonesPulsados[5].setEnabled(false);
                botonesPulsados[11].setEnabled(false);
                botonesPulsados[17].setEnabled(false);
                handler.postDelayed(hilo23, 1000);
                break;
            case 2:
                if (Build.VERSION.SDK_INT >= 23 && musicaSi) mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.4f));
                enExplo = true;
                entraMejora = true;
                finalizar();
                porDefecto();
                cronometro.startAnimation(decimaAnimacion);
                goExplo();
                break;
            case 3:
                lasVidas.startAnimation(sextaAnimacion);
                vidas = vidas + 5;
                vida.setText(String.valueOf(vidas));
                enVida = false;
                break;
        }
    }

    public void paraAnimacion(Animation animation){
        animation.setRepeatCount(1);
    }

    public void mejoraRun(final ImageView mejora){
        AnimatorSet animatorSet;
        ObjectAnimator objectAnimator, movimientoX;

        mejora.setAlpha((float) 0.99999);
        mejora.setEnabled(true);
        mejora.setScaleX(1);
        mejora.setScaleY(1);

        int[] valorX = new int[4];
        valorX[0] = 125;
        valorX[1] = 150;
        valorX[2] = 175;
        valorX[3] = 200;

        movimientoX = ObjectAnimator.ofFloat(mejora, "translationX",  valorX[random.nextInt(4)]);
        objectAnimator = ObjectAnimator.ofFloat(mejora, "translationY", -100f, 2000f);
        objectAnimator2 = ObjectAnimator.ofFloat(mejora, "rotation", 0f, 360f);

        animatorSet = new AnimatorSet();
        movimientoX.setDuration(5000);
        movimientoX.setRepeatCount(ValueAnimator.INFINITE);
        movimientoX.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator.setDuration(20000);

        objectAnimator2.setDuration(3000);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator2.setRepeatMode(ValueAnimator.RESTART);

        animatorSet.playTogether(objectAnimator, objectAnimator2, movimientoX);
        animatorSet.start();

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mejora.setAlpha((float) 0.999999999);
            }
        });

        mejora.setBackgroundResource(plays[random.nextInt(5)]);
        mejora.setVisibility(View.VISIBLE);
    }

    public void mejoraTiempo(){
        mejoraView = random.nextInt(4);
        if (imageViews[mejoraView].getAlpha() == ((float)0.99999)){
            mejoraTiempo();
        }else {
            mejora = 0;
            imageViews[mejoraView].setImageResource(R.drawable.tiempo);
            mejoraRun(imageViews[mejoraView]);
        }
    }
    public void mejoraMitad(){
        mejoraView = random.nextInt(4);
        if (imageViews[mejoraView].getAlpha() == ((float)0.99999)){
            mejoraMitad();
        }else {
            mejora = 1;
            imageViews[mejoraView].setImageResource(R.drawable.mitad);
            mejoraRun(imageViews[mejoraView]);
            seguidasMitad = 0;
        }

    }
    public void mejoraExplo(){
        mejoraView = random.nextInt(4);
        if (imageViews[mejoraView].getAlpha() == ((float)0.99999)){
            mejoraExplo();
        }else {
            mejora = 2;
            imageViews[mejoraView].setImageResource(R.drawable.explo);
            mejoraRun(imageViews[mejoraView]);
        }
    }
    public void mejoraVidas(){
        mejoraView = random.nextInt(4);
        if (imageViews[mejoraView].getAlpha() == ((float)0.99999)){
            mejoraVidas();
        }else {
            mejora = 3;
            imageViews[mejoraView].setImageResource(R.drawable.vidas);
            mejoraRun(imageViews[mejoraView]);
            seguidasVidas = 0;
        }
    }

    public void cuenta(){
        vueltasGeneral++;
        vueltas++;
        if(vueltasGeneral == 12) {
            mejoraExplo();
        }
        if(vueltas == 8) {
            mejoraTiempo();
        }
        if(!enTiempo) {
            velocidadEntrada = velocidadEntrada - 20;
            velocidadSalida = velocidadSalida - 20;
        }
        siguiente.startAnimation(cuartaAnimacion);
        countDownTimer = new CountDownTimer(cuenta, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(enTiempo){
                    if (entraMejora) {
                        cronometro.startAnimation(decimaAnimacion);
                        entraMejora = false;
                    }
                    velocidadEntrada = velocidadEntrada + 30;
                    velocidadSalida = velocidadSalida + 30;
                    cuentaTiempo--;
                    cronometro.setText(String.valueOf(cuentaTiempo));
                    if (cuentaTiempo == 5){
                        cronometro.startAnimation(segundaAnimacion);
                        cronometro.startAnimation(septimaAnimacion);
                    }
                    if(cuentaTiempo == 0){
                        cronometro.startAnimation(decimaAnimacion);
                        enTiempo = false;
                        cuentaTiempo = 16;
                        vueltas = 0;
                        velocidadEntrada = 900;
                        velocidadSalida = 450;
                        novenaAnimacion.setRepeatCount(0);
                        if (Build.VERSION.SDK_INT >= 23 && musicaSi) mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.0f));
                    }
                }else if(enExplo){
                    if (entraMejora) {
                        cronometro.startAnimation(decimaAnimacion);
                        entraMejora = false;
                    }
                    cuentaFaltaExpo--;
                    cronometro.setText(String.valueOf(cuentaFaltaExpo));
                    if (cuentaFaltaExpo == 5){
                        cronometro.startAnimation(segundaAnimacion);
                        cronometro.startAnimation(septimaAnimacion);
                    }
                    if (cuentaFaltaExpo == 0){
                        cronometro.startAnimation(decimaAnimacion);
                        cuentaFaltaExpo = 16;
                        vueltasGeneral = 0;
                        vueltas = 0;
                        enExplo = false;
                        handler.removeCallbacks(hilo25);
                        porDefecto();
                        if (Build.VERSION.SDK_INT >= 23 && musicaSi) mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.0f));
                        for(int i = 0; i<numeroBotones;i++){
                            botonesPulsados[i].startAnimation(terceraAnimacion);
                        }
                        empiezaNiveles();
                        restar = false;
                        handler.postDelayed(hilo29, 1900);
                    }
                }else{
                    segundos = (int)(millisUntilFinished / 1000);
                    if (segundos == 5) {
                        cronometro.startAnimation(segundaAnimacion);
                        cronometro.startAnimation(septimaAnimacion);
                    }
                    cronometro.setText(String.valueOf((millisUntilFinished / 1000)));
                }

            }

            @Override
            public void onFinish() {
                if (sonidosSi) soundPool.play(cuentaSonido, 1,1,1, 0, 0.95f);
                vibrator.vibrate(700);
                restar = false;
                handler.postDelayed(hilo29, 1900);

                play.startAnimation(sextaAnimacion);
                if(siguiente.getAlpha() == ((float) 0.9)){
                    play.setBackgroundResource(R.drawable.rosado);
                    home.setBackgroundResource(R.drawable.rosado);
                    play.setAlpha((float) 0.9);
                    iconoPuntos.setImageResource(R.drawable.puntosrosado);
                    colorPlay = R.drawable.rosado;
                }else if(siguiente.getAlpha() == ((float) 0.99)){
                    play.setBackgroundResource(R.drawable.verde);
                    home.setBackgroundResource(R.drawable.verde);
                    play.setAlpha((float) 0.99);
                    iconoPuntos.setImageResource(R.drawable.puntosverde);
                    colorPlay = R.drawable.verde;
                }else if(siguiente.getAlpha() == ((float) 0.999)){
                    play.setBackgroundResource(R.drawable.amarillo);
                    home.setBackgroundResource(R.drawable.amarillo);
                    play.setAlpha((float) 0.999);
                    iconoPuntos.setImageResource(R.drawable.puntosamarillo);
                    colorPlay = R.drawable.amarillo;
                }else if(siguiente.getAlpha() == ((float) 0.9999)){
                    play.setBackgroundResource(R.drawable.morado);
                    home.setBackgroundResource(R.drawable.morado);
                    play.setAlpha((float) 0.9999);
                    iconoPuntos.setImageResource(R.drawable.puntosmorado);
                    colorPlay = R.drawable.morado;
                }else if(siguiente.getAlpha() == ((float) 0.99999)){
                    play.setBackgroundResource(R.drawable.cyan);
                    home.setBackgroundResource(R.drawable.cyan);
                    play.setAlpha((float) 0.99999);
                    iconoPuntos.setImageResource(R.drawable.puntosazul);
                    colorPlay = R.drawable.cyan;
                }
                cambiarSiguiente(random.nextInt(5)+1);
                cuenta();
            }
        };
        countDownTimer.start();
    }

    public void cambiarSiguiente(int opcion){
        if(!(vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4)){
            if(opcion == 1 && play.getAlpha() != ((float)0.9)){
                siguiente.setBackgroundResource(R.drawable.play1);
                siguiente.setAlpha((float) 0.9);
            }else if(opcion == 2 && play.getAlpha() != ((float)0.99)){
                siguiente.setBackgroundResource(R.drawable.play2);
                siguiente.setAlpha((float) 0.99);
            }else if(opcion == 3 && play.getAlpha() != ((float)0.999)){
                siguiente.setBackgroundResource(R.drawable.play3);
                siguiente.setAlpha((float) 0.999);
            }else if(opcion == 4 && play.getAlpha() != ((float)0.9999)){
                siguiente.setBackgroundResource(R.drawable.play4);
                siguiente.setAlpha((float) 0.9999);
            }else if(opcion == 5 && play.getAlpha() != ((float)0.99999)){
                siguiente.setBackgroundResource(R.drawable.play5);
                siguiente.setAlpha((float) 0.99999);
            }else {
                cambiarSiguiente(random.nextInt(5)+1);
            }
        }
    }
    public void empiezaNiveles(){
        if(nivel == 1){
            velocidadEntrada = 1200;
            velocidadSalida = 700;
            handler.postDelayed(hilo2, 700);
        }
        if(nivel == 2){
            velocidadEntrada = 1100;
            velocidadSalida = 600;
            handler.postDelayed(hilo3, 700);
        }
        if(nivel == 3){
            handler.postDelayed(hilo4, 700);
        }
        if (nivel == 4){
            velocidadEntrada = 950;
            velocidadSalida = 450;
            handler.postDelayed(hilo1, 700);
        }
    }

    public void play(View view){
        cuentaRegre.startAnimation(cuentaAnimacion);
        if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
        play.setEnabled(false);
        home.setAlpha((float) 0.6);
        home.setEnabled(false);
    }
    public  void finDelJuego(){
        mediaPlayer.release();
        if (musicaSi) soundPool.play(fin, 1,1,1, -1, 1);
        octavaAnimacion.setFillAfter(false);
        countDownTimer.cancel();
        finalizar();
        handler.removeCallbacks(hilo22);
        handler.removeCallbacks(hilo23);
        handler.removeCallbacks(hilo25);
        handler.removeCallbacks(hilo26);
        handler.removeCallbacks(hilo27);
        handler.removeCallbacks(hilo28);

        if (enCompetencia) {
            datos.edit().putBoolean("SUBIRPUNTOS", true).apply();
        }else {
            datos.edit().putBoolean("SUBIRPUNTOS", false).apply();
        }

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if (nivel == 2) contador = (int) (contador * (1.15));
                    if (nivel == 3) contador = (int) (contador * (1.3));
                    soundPool.release();
                    salir = false;
                    Intent intent = new Intent(MainActivity.this, FinalActivity.class);
                    intent.putExtra("PUNTOS", contador);
                    intent.putExtra("NIVEL", nivel);
                    intent.putExtra("INTENTOS", intentos);
                    intent.putExtra("COMPETENCIA", enCompetencia);
                    startActivity(intent);
                }
            });
        }else {
            if (nivel == 2) contador = (int) (contador * (1.15));
            if (nivel == 3) contador = (int) (contador * (1.3));
            soundPool.release();
            salir = false;
            Intent intent = new Intent(this, FinalActivity.class);
            intent.putExtra("PUNTOS", contador);
            intent.putExtra("NIVEL", nivel);
            intent.putExtra("INTENTOS", intentos);
            intent.putExtra("COMPETENCIA", enCompetencia);
            startActivity(intent);
        }
    }

    public void competencia() {
        randomColor3(random.nextInt(18));
        handler.postDelayed(hilo6, 700);
        handler.postDelayed(hilo7, 1500);
        handler.postDelayed(hilo9, 1100);
        handler.postDelayed(hilo10, 1900);
        handler.postDelayed(hilo72, 2300);
    }
    public void primerNivel() {
        randomColor4(random.nextInt(18));
        handler.postDelayed(hilo6, 900);
        handler.postDelayed(hilo7, 1300);
    }
    public void segundoNivel() {
        randomColor3(random.nextInt(18));
        handler.postDelayed(hilo6, 750);
        handler.postDelayed(hilo9, 1250);
        handler.postDelayed(hilo7, 1000);
    }
    public void tercerNivel() {
        randomColor3(random.nextInt(18));
        handler.postDelayed(hilo6, 700);
        handler.postDelayed(hilo7, 1500);
        handler.postDelayed(hilo9, 1100);
        handler.postDelayed(hilo10, 1900);
    }

    public void sumarPuntuacion(final View view) {
        if (enJuego) {
            pulsadin = view;
            if (vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
                finDelJuego();
            } else {
                if (view.getAlpha() == 1) {
                    if (color == colorPlay) {
                        for (int i = 0; i < numeroBotones; i++) {
                            if (view == botonesPulsados[i] && pulsadoBoton[i]) {
                                contador++;
                                if (!enExplo) {
                                    seguidasMitad++;
                                    seguidasVidas++;
                                }
                                puntaje.setText(String.valueOf(contador));
                                if (sonidosSi) soundPool.play(toque, 0.7f, 0.7f, 0, 0, 1);
                                pulsadoBoton[pulsado] = false;
                                pulsadin.setBackgroundResource(R.drawable.defecto);
                                pulsadin.startAnimation(cuartaAnimacion);
                            }
                        }
                    } else {
                        view.setBackgroundResource(R.drawable.error);
                        handler.postDelayed(hilo21, 50);
                        vidas--;
                        if (sonidosSi) soundPool.play(fallo, 1, 1, 0, 0, 1);
                        seguidasMitad = 0;
                        seguidasVidas = 0;
                        vida.setText(String.valueOf(vidas));
                        if (contador == 0) {
                            puntaje.setText(String.valueOf(contador));
                        } else {
                            contador--;
                            puntaje.setText(String.valueOf(contador));
                        }
                    }
                } else if (view.getAlpha() == ((float) 0.99)) {
                    if (color2 == colorPlay) {
                        for (int i = 0; i < numeroBotones; i++) {
                            if (view == botonesPulsados[i] && pulsadoBoton[i]) {
                                contador++;
                                if (!enExplo) {
                                    seguidasMitad++;
                                    seguidasVidas++;
                                }
                                puntaje.setText(String.valueOf(contador));
                                if (sonidosSi) soundPool.play(toque, 0.7f, 0.7f, 0, 0, 1);
                                pulsadoBoton[pulsado2] = false;
                                pulsadin.setBackgroundResource(R.drawable.defecto);
                                pulsadin.startAnimation(cuartaAnimacion);
                            }
                        }
                    } else {
                        view.setBackgroundResource(R.drawable.error);
                        handler.postDelayed(hilo21, 50);
                        vidas--;
                        if (sonidosSi) soundPool.play(fallo, 1, 1, 0, 0, 1);
                        seguidasMitad = 0;
                        seguidasVidas = 0;
                        vida.setText(String.valueOf(vidas));
                        if (contador == 0) {
                            puntaje.setText(String.valueOf(contador));
                        } else {
                            contador--;
                            puntaje.setText(String.valueOf(contador));
                        }
                    }
                } else if (view.getAlpha() == ((float) 0.9999999)) {
                    if (color6 == colorPlay) {
                        for (int i = 0; i < numeroBotones; i++) {
                            if (view == botonesPulsados[i] && pulsadoBoton[i]) {
                                contador++;
                                if (!enExplo) {
                                    seguidasMitad++;
                                    seguidasVidas++;
                                }
                                puntaje.setText(String.valueOf(contador));
                                if (sonidosSi) soundPool.play(toque, 0.7f, 0.7f, 0, 0, 1);
                                pulsadoBoton[pulsado6] = false;
                                pulsadin.setBackgroundResource(R.drawable.defecto);
                                pulsadin.startAnimation(cuartaAnimacion);
                            }
                        }
                    } else {
                        view.setBackgroundResource(R.drawable.error);
                        handler.postDelayed(hilo21, 50);
                        vidas--;
                        if (sonidosSi) soundPool.play(fallo, 1, 1, 0, 0, 1);
                        seguidasMitad = 0;
                        seguidasVidas = 0;
                        vida.setText(String.valueOf(vidas));
                        if (contador == 0) {
                            puntaje.setText(String.valueOf(contador));
                        } else {
                            contador--;
                            puntaje.setText(String.valueOf(contador));
                        }
                    }
                } else if (view.getAlpha() == ((float) 0.999) || view.getAlpha() == ((float) 0.9999) || view.getAlpha() == ((float) 0.999999)) {
                    for (int i = 0; i < numeroBotones; i++) {
                        if (view == botonesPulsados[i] && pulsadoBoton[i]) {
                            contador++;
                            if (!enExplo) {
                                seguidasMitad++;
                                seguidasVidas++;
                            }
                            puntaje.setText(String.valueOf(contador));
                            if (sonidosSi) soundPool.play(toque, 0.7f, 0.7f, 0, 0, 1);
                            pulsadoBoton[i] = false;
                            pulsadin.setBackgroundResource(R.drawable.defecto);
                            pulsadin.startAnimation(cuartaAnimacion);
                        }
                    }
                } else {
                    for (int i = 0; i < numeroBotones; i++) {
                        if (view == botonesPulsados[i] && restarBoton[i]) {
                            view.setBackgroundResource(R.drawable.error);
                            handler.postDelayed(hilo21, 50);
                            vidas--;
                            if (sonidosSi) soundPool.play(fallo, 1, 1, 0, 0, 1);
                            seguidasMitad = 0;
                            seguidasVidas = 0;
                            vida.setText(String.valueOf(vidas));
                            if (contador == 0) {
                                puntaje.setText(String.valueOf(contador));
                            } else {
                                contador--;
                                puntaje.setText(String.valueOf(contador));
                            }
                        }
                    }
                }
            }
        } else{
            if (sonidosSi) soundPool.play(fallo, 1, 1, 0, 0, 1);
            if (cuentica != 4) {
                textoToast.setText(getString(R.string.cuentaError));
                cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                toast.show();
            }else {
                textoToast.setText(getString(R.string.playError));
                cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                toast.show();
            }
        }
    }

    public void randomColor1(final int opcion, final int col){
        colorPuto1 = col;
        opcionRandom1 = opcion;
        if(vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        }else {

            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }

            for(int i=0; i<numeroBotones; i++){
                if(opcion == i){
                    pulsado = i;
                    boton = botonesPulsados[i];
                }
            }

            for(int i=0; i<5; i++){
                if(col == i){
                    color = colores[i];
                }
            }

            if (boton.getAlpha() == ((float)0.9999) || boton.getAlpha() == ((float)0.9999999) || boton.getAlpha() == ((float)0.99) || boton.getAlpha() == ((float)0.999) || boton.getAlpha() == ((float) 0.999999)) {
                randomColor1(random.nextInt(18), random.nextInt(5));
            } else {
                if (boton.getAlpha() == 1) {
                    if(colorPlay == color) {
                        if (pulsadoBoton[pulsado]){
                            if (restar) {
                                restarBoton[pulsado] = false;
                                handler.postDelayed(hilo30, 550);
                                vidas--;
                                if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                                seguidasMitad = 0;
                                seguidasVidas = 0;
                                vida.setText(String.valueOf(vidas));
                                pulsadoBoton[pulsado] = false;
                                boton.setBackgroundResource(R.drawable.error);
                            }
                            handler.postDelayed(hilo16, 50);
                        } else {
                            boton.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo6, velocidadSalida);
                        }
                    }else{
                        if (pulsadoBoton[pulsado]) {
                            boton.startAnimation(quintaAnimacion);
                            boton.setAlpha((float) 0.99999);
                            boton.setBackgroundResource(R.drawable.defecto);
                            handler.postDelayed(hilo6, velocidadSalida);
                        } else {
                            boton.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo6, velocidadSalida);
                        }
                    }
                } else {
                    boton.startAnimation(terceraAnimacion);
                    boton.setAlpha(1);
                    boton.setBackgroundResource(color);
                    pulsadoBoton[pulsado] = true;
                    handler.postDelayed(hilo11, velocidadEntrada);
                }
            }
        }
    }
    public void randomColor2(final int opcion, final int col){
        opcionRandom2 = opcion;
        if (vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        } else {
            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }


            for (int i = 0; i < numeroBotones; i++) {
                if (opcion == i) {
                    pulsado2 = i;
                    boton2 = botonesPulsados[i];
                }
            }

            if (boton2.getAlpha() == ((float) 0.99)) {
                color2 = col;
            } else {
                if (col == colores[0]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color2 = colores[4];
                            break;
                        case 2:
                            color2 = colores[0];
                            break;
                        case 3:
                            color2 = colores[1];
                            break;
                    }
                } else if (col == colores[1]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color2 = colores[0];
                            break;
                        case 2:
                            color2 = colores[1];
                            break;
                        case 3:
                            color2 = colores[2];
                            break;
                    }
                } else if (col == colores[2]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color2 = colores[1];
                            break;
                        case 2:
                            color2 = colores[2];
                            break;
                        case 3:
                            color2 = colores[3];
                            break;
                    }
                } else if (col == colores[3]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color2 = colores[2];
                            break;
                        case 2:
                            color2 = colores[3];
                            break;
                        case 3:
                            color2 = colores[4];
                            break;
                    }
                } else if (col == colores[4]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color2 = colores[3];
                            break;
                        case 2:
                            color2 = colores[4];
                            break;
                        case 3:
                            color2 = colores[0];
                            break;
                    }
                }
            }
            if (boton2.getAlpha() == ((float) 0.999) || boton2.getAlpha() == ((float) 0.9999999) || boton2.getAlpha() == 1 || boton2.getAlpha() == ((float) 0.9999) || boton2.getAlpha() == ((float) 0.999999)) {
                randomColor2(random.nextInt(18), random.nextInt(5));
            } else {
                if (boton2.getAlpha() == ((float) 0.99)) {
                    if (colorPlay == color2) {
                        if (pulsadoBoton[pulsado2]) {
                            if (restar) {
                                restarBoton[pulsado2] = false;
                                handler.postDelayed(hilo31, 550);
                                vidas--;
                                if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                                seguidasMitad = 0;
                                seguidasVidas = 0;
                                vida.setText(String.valueOf(vidas));
                                pulsadoBoton[pulsado2] = false;
                                boton2.setBackgroundResource(R.drawable.error);
                            }
                            handler.postDelayed(hilo17, 50);
                        } else {
                            boton2.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo7, velocidadSalida);
                        }
                    } else {
                        if (pulsadoBoton[pulsado2]) {
                            boton2.setAlpha((float) 0.99999);
                            boton2.startAnimation(quintaAnimacion);
                            boton2.setBackgroundResource(R.drawable.defecto);
                            handler.postDelayed(hilo7, velocidadSalida);
                        } else {
                            boton2.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo7, velocidadSalida);
                        }
                    }
                } else {
                    boton2.setAlpha((float) 0.99);
                    boton2.setBackgroundResource(color2);
                    boton2.startAnimation(terceraAnimacion);
                    pulsadoBoton[pulsado2] = true;
                    handler.postDelayed(hilo12, velocidadEntrada);
                }
            }
        }
    }
    public void randomColor3(final int opcion){
        opcionRandom3 = opcion;
        if(vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        }else {
            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }

            for (int i = 0; i < numeroBotones; i++) {
                if (opcion == i) {
                    pulsado3 = i;
                    boton3 = botonesPulsados[i];
                }
            }

            if (boton3.getAlpha() == 1 || boton3.getAlpha() == ((float)0.99) || boton3.getAlpha() == ((float)0.9999999) || boton3.getAlpha() == ((float)0.9999) || boton3.getAlpha() == ((float) 0.999999)) {
                randomColor3(random.nextInt(18));
            } else {
                if (boton3.getAlpha() == ((float) 0.999)) {
                    if (pulsadoBoton[pulsado3]) {
                        if (restar) {
                            restarBoton[pulsado3] = false;
                            handler.postDelayed(hilo32, 550);
                            seguidasMitad = 0;
                            seguidasVidas = 0;
                            vidas--;
                            if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                            vida.setText(String.valueOf(vidas));
                            pulsadoBoton[pulsado3] = false;
                            boton3.setBackgroundResource(R.drawable.error);
                        }
                        handler.postDelayed(hilo18, 50);
                    } else {
                        boton3.setAlpha((float) 0.99999);
                        handler.postDelayed(hilo8, velocidadSalida);
                    }
                } else {
                    boton3.setAlpha((float) 0.999);
                    boton3.setBackgroundResource(colorPlay);
                    boton3.startAnimation(terceraAnimacion);
                    pulsadoBoton[pulsado3] = true;
                    handler.postDelayed(hilo13, velocidadEntrada);
                }
            }
        }
    }
    public void randomColor4(final int opcion) {
        opcionRandom4 = opcion;
        if(vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        }else {
            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }

            for (int i = 0; i < numeroBotones; i++) {
                if (opcion == i) {
                    pulsado4 = i;
                    boton4 = botonesPulsados[i];
                }
            }

            if (boton4.getAlpha() == 1 || boton4.getAlpha() == ((float)0.99) || boton4.getAlpha() == ((float)0.9999999) || boton4.getAlpha() == ((float)0.999) || boton4.getAlpha() == ((float) 0.999999)) {
                randomColor4(random.nextInt(18));
            } else {
                if (boton4.getAlpha() == ((float) 0.9999)) {
                    if (pulsadoBoton[pulsado4]) {
                        if (restar) {
                            restarBoton[pulsado4] = false;
                            handler.postDelayed(hilo33, 550);
                            vidas--;
                            if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                            seguidasMitad = 0;
                            seguidasVidas = 0;
                            vida.setText(String.valueOf(vidas));
                            pulsadoBoton[pulsado4] = false;
                            boton4.setBackgroundResource(R.drawable.error);
                        }
                        handler.postDelayed(hilo19, 50);
                    } else {
                        boton4.setAlpha((float) 0.99999);
                        handler.postDelayed(hilo9, velocidadSalida);
                    }
                } else {
                    boton4.setAlpha((float) 0.9999);
                    boton4.setBackgroundResource(colorPlay);
                    boton4.startAnimation(terceraAnimacion);
                    pulsadoBoton[pulsado4] = true;
                    handler.postDelayed(hilo14, velocidadEntrada);
                }
            }
        }
    }
    public void randomColor5(final int opcion){
        opcionRandom5 = opcion;
        if(vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        }else {
            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }

            for (int i = 0; i < numeroBotones; i++) {
                if (opcion == i) {
                    pulsado5 = i;
                    boton5 = botonesPulsados[i];
                }
            }

            if (boton5.getAlpha() == 1 || boton5.getAlpha() == ((float)0.99) || boton5.getAlpha() == ((float)0.9999999) || boton5.getAlpha() == ((float)0.9999) || boton5.getAlpha() == ((float) 0.999)) {
                randomColor5(random.nextInt(18));
            } else {
                if (boton5.getAlpha() == ((float) 0.999999)) {
                    if (pulsadoBoton[pulsado5]) {
                        if (restar) {
                            restarBoton[pulsado5] = false;
                            handler.postDelayed(hilo34, 550);
                            vidas--;
                            if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                            seguidasMitad = 0;
                            seguidasVidas = 0;
                            vida.setText(String.valueOf(vidas));
                            pulsadoBoton[pulsado5] = false;
                            boton5.setBackgroundResource(R.drawable.error);
                        }
                        handler.postDelayed(hilo20, 50);
                    } else {
                        boton5.setAlpha((float) 0.99999);
                        handler.postDelayed(hilo10, velocidadSalida);
                    }
                } else {
                    boton5.setAlpha((float) 0.999999);
                    boton5.setBackgroundResource(colorPlay);
                    boton5.startAnimation(terceraAnimacion);
                    pulsadoBoton[pulsado5] = true;
                    handler.postDelayed(hilo15, velocidadEntrada);
                }
            }
        }
    }
    public void randomColor6(final int opcion, final int col){
        opcionRandom6 = opcion;
        if (vidas == 0 || vidas == -1 || vidas == -2 || vidas == -3 || vidas == -4) {
            finDelJuego();
        } else {
            if(seguidasMitad == 100 && !enMitad){
                enMitad = true;
                mejoraMitad();
            }
            if(seguidasVidas == 70 && !enVida){
                enVida = true;
                mejoraVidas();
            }


            for (int i = 0; i < numeroBotones; i++) {
                if (opcion == i) {
                    pulsado6 = i;
                    boton6 = botonesPulsados[i];
                }
            }

            if (boton6.getAlpha() == ((float) 0.9999999)) {
                color6 = col;
            } else {
                if (col == colores[0]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color6 = colores[4];
                            break;
                        case 2:
                            color6 = colores[0];
                            break;
                        case 3:
                            color6 = colores[1];
                            break;
                    }
                } else if (col == colores[1]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color6 = colores[0];
                            break;
                        case 2:
                            color6 = colores[1];
                            break;
                        case 3:
                            color6 = colores[2];
                            break;
                    }
                } else if (col == colores[2]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color6 = colores[1];
                            break;
                        case 2:
                            color6 = colores[2];
                            break;
                        case 3:
                            color6 = colores[3];
                            break;
                    }
                } else if (col == colores[3]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color6 = colores[2];
                            break;
                        case 2:
                            color6 = colores[3];
                            break;
                        case 3:
                            color6 = colores[4];
                            break;
                    }
                } else if (col == colores[4]) {
                    switch (randomColor.nextInt(3) + 1) {
                        case 1:
                            color6 = colores[3];
                            break;
                        case 2:
                            color6 = colores[4];
                            break;
                        case 3:
                            color6 = colores[0];
                            break;
                    }
                }
            }
            if (boton6.getAlpha() == ((float) 0.99) ||boton6.getAlpha() == ((float) 0.999) || boton6.getAlpha() == 1 || boton6.getAlpha() == ((float) 0.9999) || boton6.getAlpha() == ((float) 0.999999)) {
                randomColor6(random.nextInt(18), random.nextInt(5));
            } else {
                if (boton6.getAlpha() == ((float) 0.9999999)) {
                    if (colorPlay == color6) {
                        if (pulsadoBoton[pulsado6]) {
                            if (restar) {
                                restarBoton[pulsado6] = false;
                                handler.postDelayed(hilo35, 550);
                                vidas--;
                                if (sonidosSi) soundPool.play(fallo, 1,1,0, 0, 1);
                                seguidasMitad = 0;
                                seguidasVidas = 0;
                                vida.setText(String.valueOf(vidas));
                                pulsadoBoton[pulsado6] = false;
                                boton6.setBackgroundResource(R.drawable.error);
                            }
                            handler.postDelayed(hilo172, 50);
                        } else {
                            boton6.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo72, velocidadSalida);
                        }
                    } else {
                        if (pulsadoBoton[pulsado6]) {
                            boton6.setAlpha((float) 0.99999);
                            boton6.startAnimation(quintaAnimacion);
                            boton6.setBackgroundResource(R.drawable.defecto);
                            handler.postDelayed(hilo72, velocidadSalida);
                        } else {
                            boton6.setAlpha((float) 0.99999);
                            handler.postDelayed(hilo72, velocidadSalida);
                        }
                    }
                } else {
                    boton6.setAlpha((float) 0.9999999);
                    boton6.setBackgroundResource(color6);
                    boton6.startAnimation(terceraAnimacion);
                    pulsadoBoton[pulsado6] = true;
                    handler.postDelayed(hilo122, velocidadEntrada);
                }
            }
        }
    }

    public void goExplo() {
        for (int i =0; i<numeroBotones; i++) {
            boton3 = botonesPulsados[i];
            boton3.setAlpha((float) 0.999);
            pulsadoBoton[i] = true;
            boton3.setBackgroundResource(colorPlay);
            boton3.startAnimation(terceraAnimacion);
        }
        handler.postDelayed(hilo25, random.nextInt(250));
    }
    public void finalizar(){
        vibrator.cancel();
        handler.removeCallbacks(hilo1);
        handler.removeCallbacks(hilo2);
        handler.removeCallbacks(hilo3);
        handler.removeCallbacks(hilo4);
        handler.removeCallbacks(hilo5);
        handler.removeCallbacks(hilo6);
        handler.removeCallbacks(hilo7);
        handler.removeCallbacks(hilo72);
        handler.removeCallbacks(hilo8);
        handler.removeCallbacks(hilo9);
        handler.removeCallbacks(hilo10);
        handler.removeCallbacks(hilo11);
        handler.removeCallbacks(hilo12);
        handler.removeCallbacks(hilo122);
        handler.removeCallbacks(hilo13);
        handler.removeCallbacks(hilo14);
        handler.removeCallbacks(hilo15);
        handler.removeCallbacks(hilo16);
        handler.removeCallbacks(hilo17);
        handler.removeCallbacks(hilo172);
        handler.removeCallbacks(hilo18);
        handler.removeCallbacks(hilo19);
        handler.removeCallbacks(hilo20);
        handler.removeCallbacks(hilo21);
        handler.removeCallbacks(hilo29);
        handler.removeCallbacks(hilo30);
        handler.removeCallbacks(hilo31);
        handler.removeCallbacks(hilo32);
        handler.removeCallbacks(hilo33);
        handler.removeCallbacks(hilo34);
        handler.removeCallbacks(hilo35);
    }
    public void menu(View view){
        if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
        if (enCompetencia){
            salir = false;
            Intent intent = new Intent(MainActivity.this, CompetenciaActivity.class);
            startActivity(intent);
        }else {
            salir = false;
            Intent intent = new Intent(MainActivity.this, NivelesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (musicaSi && datos.getBoolean("REINI", true))startService(new Intent(this, Musica.class));
        if (datos.getBoolean("PARTIDAPERDIDA", false)) {
            alertaPartida();
        }else {
            inicio();
        }
        salir = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        octavaAnimacion.setFillAfter(false);
        finalizar();
        handler.removeCallbacks(hilo22);
        handler.removeCallbacks(hilo23);
        handler.removeCallbacks(hilo25);
        handler.removeCallbacks(hilo26);
        handler.removeCallbacks(hilo27);
        handler.removeCallbacks(hilo28);
        if (!play.isEnabled()) {
            countDownTimer.cancel();
        }
        if (salir) {
            datos.edit().putBoolean("PARTIDAPERDIDA", true).apply();
            if (enJuego){
                mediaPlayer.release();
                soundPool.release();
            }else {
                stopService(new Intent(this, Musica.class));
            }
        }
    }

    private void alertaPartida() {
        dialog.setCancelable(false);
        mensajeAlerta.setTextSize(25);
        botonAlertaNo.setVisibility(View.VISIBLE);
        botonAlertaNo.setEnabled(true);
        botonAlertaSi.setVisibility(View.VISIBLE);
        botonAlertaSi.setEnabled(true);
        botonAlerta.setVisibility(View.INVISIBLE);
        botonAlerta.setEnabled(false);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        tituloAlerta.setText(getString(R.string.tituloPartida));
        mensajeAlerta.setText(getString(R.string.mensajePartida));
        textoBotonAlertaNo.setText(getString(R.string.botonPartidaNo));
        textoBotonAlertaSi.setText(getString(R.string.botonPartidaSi));
        dialog.show();
    }

    public void onBackPressed(){
        if(aBoolean){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this, "Presiona de nuevo para salir", Toast.LENGTH_LONG).show();
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
        if (salir) datos.edit().putBoolean("REINI", false).apply();
    }

}