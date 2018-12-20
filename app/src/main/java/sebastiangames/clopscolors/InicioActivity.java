package sebastiangames.clopscolors;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.Random;

import javax.annotation.Nullable;

public class InicioActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private Vibrator vibrator;
    private SoundPool soundPool;
    private int efecto, intents, fallo;
    private ImageView competencia, musicaImagen, sonidoImagen, pregunta;
    private FrameLayout icono, jugar, music, info, informa, atras, botonAlerta, botonAlertaNo, botonAlertaSi, botonIntentos;
    private Animation terceraAnimacion, cuartaAnimacion;
    private Boolean aBoolean, sonidosSi, salir;
    private Handler handler;
    private TextView tituloAlerta, mensajeAlerta, textoBotonAlerta, textoBotonAlertaNo,
            textoBotonAlertaSi;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private Dialog dialog, progressBar, musica;
    private InterstitialAd interstitialAd;
    private SharedPreferences datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Random random;
        FrameLayout musicAlerta, sonidoAlerta;
        Animation primeraAnimacion, segundaAnimacion,quintaAnimacion;
        TextView textoJugar, textoBy, textoNombre1, competenciaTi;
        Typeface negrita, normalita;
        int[] colores, seleccionados;
        GoogleSignInOptions googleSignInOptions;
        AdView adView;
        final FirebaseFirestore db;

        MobileAds.initialize(this, getString(R.string.mods_id));

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.screen_compue));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                salir = false;
                Intent intent = new Intent(InicioActivity.this, CompetenciaActivity.class);
                startActivity(intent);
                progressBar.dismiss();
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
                .build();
        efecto = soundPool.load(this, R.raw.efecto, 1);
        fallo = soundPool.load(this, R.raw.fallo, 1);
        intents = soundPool.load(this, R.raw.intents, 1);
        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);
        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        progressBar = new Dialog(this);
        progressBar.setContentView(R.layout.progress);
        Objects.requireNonNull(progressBar.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alerta);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        musica = new Dialog(this);
        musica.setContentView(R.layout.musica);
        Objects.requireNonNull(musica.getWindow()).setGravity(Gravity.TOP | Gravity.START);
        musica.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        normalita = Typeface.createFromAsset(getAssets(), "fuentes/normal.otf");
        negrita = Typeface.createFromAsset(getAssets(), "fuentes/negrita.otf");

        random = new Random(System.currentTimeMillis());
        colores = new int[5];

        seleccionados = new int[3];
        aBoolean = false;
        handler = new Handler();

        colores[0] = R.drawable.rosado;
        colores[1] = R.drawable.verde;
        colores[2] = R.drawable.amarillo;
        colores[3] = R.drawable.morado;
        colores[4] = R.drawable.cyan;

        icono = findViewById(R.id.iconoInicio);
        jugar = findViewById(R.id.jugar);
        music = findViewById(R.id.musicInicio);
        info = findViewById(R.id.inforInicio);
        informa = findViewById(R.id.informacion);
        atras = findViewById(R.id.atrasInfo);
        competencia = findViewById(R.id.compite);
        botonAlerta = dialog.findViewById(R.id.botonAlerta);
        botonAlertaNo = dialog.findViewById(R.id.botonNo);
        botonAlertaSi = dialog.findViewById(R.id.botonSi);
        botonIntentos = dialog.findViewById(R.id.botonIntentos);
        pregunta = dialog.findViewById(R.id.pregunta);
        musicAlerta = musica.findViewById(R.id.musicaAlerta);
        sonidoAlerta = musica.findViewById(R.id.sonidoAlerta);
        musicaImagen = musica.findViewById(R.id.musicaImagen);
        sonidoImagen = musica.findViewById(R.id.sonidoImagen);

        musicaImagen.setAlpha((float) 0.999);
        sonidoImagen.setAlpha((float) 0.999);

        seleccionados[0] = colores[random.nextInt(5)];
        seleccionados[1] = colores[random.nextInt(5)];
        seleccionados[2] = colores[random.nextInt(5)];

        while (seleccionados[0] == seleccionados[1]){
            seleccionados[0] = colores[random.nextInt(5)];
        }
        while (seleccionados[1] == seleccionados[2] || seleccionados[0] == seleccionados[2]){
            seleccionados[2] = colores[random.nextInt(5)];
        }

        jugar.setBackgroundResource(seleccionados[0]);
        music.setBackgroundResource(seleccionados[1]);
        info.setBackgroundResource(seleccionados[2]);

        atras.setEnabled(false);

        textoJugar = findViewById(R.id.textoJugar);
        textoBy = findViewById(R.id.textoInicioBy);
        competenciaTi = findViewById(R.id.tituloCompetencia);
        textoNombre1 = findViewById(R.id.textoInicioNombre1);
        tituloAlerta = dialog.findViewById(R.id.tituloAlerta);
        mensajeAlerta = dialog.findViewById(R.id.mensajeAlerta);
        textoBotonAlerta = dialog.findViewById(R.id.textoBotonAlerta);
        textoBotonAlertaNo = dialog.findViewById(R.id.textoBotonNo);
        textoBotonAlertaSi = dialog.findViewById(R.id.textoBotonSi);

        competenciaTi.setTypeface(negrita);
        textoJugar.setTypeface(negrita);
        textoBy.setTypeface(negrita);
        textoNombre1.setTypeface(normalita);

        tituloAlerta.setTypeface(negrita);
        mensajeAlerta.setTypeface(normalita);
        textoBotonAlerta.setTypeface(negrita);
        textoBotonAlertaNo.setTypeface(negrita);
        textoBotonAlertaSi.setTypeface(negrita);

        primeraAnimacion = AnimationUtils.loadAnimation(this, R.anim.iconoinicio);
        icono.startAnimation(primeraAnimacion);

        segundaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar);
        jugar.startAnimation(segundaAnimacion);

        terceraAnimacion = AnimationUtils.loadAnimation(this, R.anim.info);
        cuartaAnimacion = AnimationUtils.loadAnimation(this, R.anim.info2);

        if(mAuth.getCurrentUser() != null ) {
            competencia.setAlpha((float) 0.99999999999999);
        }else {
            competencia.setAlpha((float) 0.6);
        }

        quintaAnimacion = AnimationUtils.loadAnimation(this, R.anim.competencia);
        competencia.startAnimation(quintaAnimacion);

        competencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir = false;
                if (competencia.getAlpha() == ((float)0.5)){
                    alertaActu();
                }else {
                    progressBar.show();
                    if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                    if (isOnline(InicioActivity.this)){
                        if (mAuth.getCurrentUser() == null){
                            progressBar.dismiss();
                            alertaInicio();
                        }else if (isOnline(InicioActivity.this)){
                            db.collection("Versiones").document("VERSION").get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (Objects.equals(Objects.requireNonNull(task.getResult()).get("version"), "1.0")){
                                                if (interstitialAd.isLoaded()){
                                                    interstitialAd.show();
                                                }else {
                                                    salir = false;
                                                    Intent intent = new Intent(InicioActivity.this, CompetenciaActivity.class);
                                                    startActivity(intent);
                                                    progressBar.dismiss();
                                                    if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                                                }
                                            }else {
                                                progressBar.dismiss();
                                                alertaActu();
                                                competencia.setAlpha((float) 0.5);
                                            }
                                        }
                                    });
                        }else{
                            progressBar.dismiss();
                            alertaInternet();
                        }
                    }else{
                        progressBar.dismiss();
                        alertaInternet();
                    }
                }
            }
        });

        botonAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                if (textoBotonAlerta.getText() == getString(R.string.botonAlerta)) {
                    iniciarSesion();
                }else {
                    dialog.dismiss();
                }
            }
        });
        botonAlertaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                dialog.dismiss();
                salir = false;
                Intent intent = new Intent(InicioActivity.this, NivelesActivity.class);
                startActivity(intent);
            }
        });
        botonAlertaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                stopService(new Intent(InicioActivity.this, Musica.class));
                dialog.dismiss();
                salir = false;
                Intent intent = new Intent(InicioActivity.this, TutoActivity.class);
                startActivity(intent);
            }
        });

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                if (!datos.contains("RECORD")) {
                    alertaJugar();
                }else {
                    salir = false;
                    Intent intent = new Intent(InicioActivity.this, NivelesActivity.class);
                    startActivity(intent);
                }
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musica.show();
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });
        musicAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicaImagen.getAlpha() == ((float)0.99)) {
                    startService(new Intent(InicioActivity.this, Musica.class));
                    musicaImagen.setAlpha((float) 0.999);
                    musicaImagen.setImageDrawable(getResources().getDrawable(R.drawable.music));
                    datos.edit().putBoolean("MUSICA", true).apply();
                }else {
                    if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                    vibrator.vibrate(300);
                    stopService(new Intent(InicioActivity.this, Musica.class));
                    musicaImagen.setAlpha((float) 0.99);
                    musicaImagen.setImageDrawable(getResources().getDrawable(R.drawable.sinmusica));
                    datos.edit().putBoolean("MUSICA", false).apply();
                }
            }
        });
        sonidoAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidoImagen.getAlpha() == ((float)0.99)) {
                    soundPool.play(efecto, 1,1,1, 0, 1);
                    sonidoImagen.setAlpha((float) 0.999);
                    sonidoImagen.setImageDrawable(getResources().getDrawable(R.drawable.sonido));
                    datos.edit().putBoolean("SONIDOS", true).apply();
                    sonidosSi = true;
                }else {
                    vibrator.vibrate(300);
                    sonidoImagen.setAlpha((float) 0.99);
                    sonidoImagen.setImageDrawable(getResources().getDrawable(R.drawable.sinsonido));
                    datos.edit().putBoolean("SONIDOS", false).apply();
                    sonidosSi = false;
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                atras.setEnabled(true);

                music.setEnabled(false);
                competencia.setEnabled(false);
                info.setEnabled(false);
                jugar.setEnabled(false);
                icono.setEnabled(false);


                informa.startAnimation(terceraAnimacion);
                atras.setVisibility(View.VISIBLE);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                atras.setEnabled(false);

                music.setEnabled(true);
                competencia.setEnabled(true);
                info.setEnabled(true);
                jugar.setEnabled(true);
                icono.setEnabled(true);


                informa.startAnimation(cuartaAnimacion);
                atras.setVisibility(View.INVISIBLE);
            }
        });
        icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });
    }

    public static boolean isOnline(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void iniciarSesion(){
        dialog.dismiss();
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void alertaInicio(){
        mensajeAlerta.setTextSize(25);
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.VISIBLE);
        botonAlerta.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        tituloAlerta.setText(getString(R.string.tituloAlerta));
        mensajeAlerta.setText(getString(R.string.textoAlerta));
        textoBotonAlerta.setText(getString(R.string.botonAlerta));
        pregunta.setVisibility(View.INVISIBLE);
        pregunta.setEnabled(false);
        dialog.show();
    }
    public void alertaInicioError(){
        if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
        mensajeAlerta.setTextSize(25);
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.VISIBLE);
        botonAlerta.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        tituloAlerta.setText(getString(R.string.tituloAlerta));
        mensajeAlerta.setText(getString(R.string.errorSesion));
        textoBotonAlerta.setText(getString(R.string.botonAlerta));
        pregunta.setVisibility(View.INVISIBLE);
        pregunta.setEnabled(false);
        dialog.show();
    }
    public void alertaInternet() {
        if (sonidosSi) soundPool.play(fallo, 2f, 2f, 1, 0, 1);
        mensajeAlerta.setTextSize(25);
        tituloAlerta.setText(getString(R.string.tituloAlertaInternet));
        mensajeAlerta.setText(getString(R.string.textoAlertaInternet));
        textoBotonAlerta.setText(getString(R.string.botonAlertaInternet));
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.VISIBLE);
        botonAlerta.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        pregunta.setVisibility(View.INVISIBLE);
        pregunta.setEnabled(false);
        dialog.show();
    }
    private void alertaActu() {
        if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
        tituloAlerta.setText(getString(R.string.tituloAlertaActu));
        mensajeAlerta.setText(getString(R.string.textoAlertaActu));
        textoBotonAlerta.setText(getString(R.string.botonAlertaActu));
        mensajeAlerta.setTextSize(25);
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.VISIBLE);
        botonAlerta.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        pregunta.setVisibility(View.INVISIBLE);
        pregunta.setEnabled(false);
        dialog.show();
    }
    public void alertaJugar(){
        mensajeAlerta.setTextSize(25);
        tituloAlerta.setText(getString(R.string.tituloAlertaTuto));
        mensajeAlerta.setText(getString(R.string.textoAlertaTuto));
        botonAlerta.setVisibility(View.INVISIBLE);
        botonAlerta.setEnabled(false);
        botonAlertaNo.setVisibility(View.VISIBLE);
        botonAlertaNo.setEnabled(true);
        textoBotonAlertaNo.setText(getString(R.string.botonAlertaNo));
        botonAlertaSi.setVisibility(View.VISIBLE);
        textoBotonAlertaSi.setText(getString(R.string.botonAlertaSi));
        botonAlertaSi.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        pregunta.setVisibility(View.INVISIBLE);
        pregunta.setEnabled(false);
        dialog.show();
    }

    private void firebaseGooglePlay(final GoogleSignInAccount googleSignInAccount) {
        progressBar.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.dismiss();
                    salir = false;
                    Intent intent = new Intent(InicioActivity.this, CompetenciaActivity.class);
                    startActivity(intent);
                }else {
                    progressBar.dismiss();
                    alertaInicioError();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                GoogleSignInAccount signedInAccount = task.getResult();
                firebaseGooglePlay(Objects.requireNonNull(signedInAccount));
            } else {
                Toast.makeText(this, getString(R.string.errorSesion), Toast.LENGTH_LONG).show();
                alertaInicio();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Boolean musicaSi;
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        musicaSi = datos.getBoolean("MUSICA", true);
        sonidosSi = datos.getBoolean("SONIDOS", true);

        if (musicaSi) {
            musicaImagen.setAlpha((float) 0.999);
            musicaImagen.setImageDrawable(getResources().getDrawable(R.drawable.music));
            startService(new Intent(this, Musica.class));
        }else {
            musicaImagen.setAlpha((float) 0.99);
            musicaImagen.setImageDrawable(getResources().getDrawable(R.drawable.sinmusica));
        }
        if (sonidosSi){
            sonidoImagen.setAlpha((float) 0.999);
            sonidoImagen.setImageDrawable(getResources().getDrawable(R.drawable.sonido));
        }else {
            sonidoImagen.setAlpha((float) 0.99);
            sonidoImagen.setImageDrawable(getResources().getDrawable(R.drawable.sinsonido));
        }
        salir = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (salir) {
            stopService(new Intent(this, Musica.class));
            vibrator.cancel();
        }
    }

    public void onBackPressed(){
        if(aBoolean){
            soundPool.release();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(InicioActivity.this, "Presiona de nuevo para salir", Toast.LENGTH_LONG).show();
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