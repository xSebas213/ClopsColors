package sebastiangames.clopscolors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class FinalActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private ImageView imagenToast;
    private String id;
    private SoundPool soundPool;
    private FrameLayout multiplica;
    private TextView score, punticosOp, textoToast;
    private int puntuacion, nuevoRecord, veces, zoom;
    private int nivel, intentos, puntosRanking, puntosViejos, efecto, intents, fallo;
    private Boolean aBoolean, competencia, visto, sonidosSi, musicaSi, salir, reinicio, focus;
    private Handler handler;
    private Animation terceraAnimacion, quintaAnimacion, sextaAnimacion;
    private CollectionReference usuarios;
    private Map<String, Object> usuario;
    private RewardedVideoAd rewardedVideoAd;
    private SharedPreferences datos;
    private SharedPreferences.Editor editor;
    private Toast toast;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        FrameLayout playAgain, menu;
        TextView textoMultiplica, record, puntosRecord;
        int[] fondos, seleccionados;
        Random random;
        FirebaseFirestore db;
        Typeface normalita, negrita;
        Animation primeraAnimacion, segundaAnimacion, cuartaAnimacion;

        FirebaseAuth mAuth;

        AdView adView;
        View viewToast;

        adView = findViewById(R.id.adViewFinal);
        AdRequest adRequest;adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);
        puntosViejos = datos.getInt("PUNTICOS", 0);
        nuevoRecord = datos.getInt("RECORD", 0);
        sonidosSi = datos.getBoolean("SONIDOS", true);
        musicaSi = datos.getBoolean("MUSICA", true);
        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();
        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
                .build();
        efecto = soundPool.load(this, R.raw.efecto, 1);
        intents = soundPool.load(this, R.raw.intents, 1);
        fallo = soundPool.load(this, R.raw.fallo, 1);

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        cargarVideo();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        viewToast = inflater.inflate(R.layout.toast, null);
        toast = new Toast(FinalActivity.this);
        toast.setView(viewToast);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 50);

        primeraAnimacion = AnimationUtils.loadAnimation(this, R.anim.puntosfin);
        segundaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar);
        terceraAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion4);
        cuartaAnimacion = AnimationUtils.loadAnimation(this, R.anim.desaparecer);
        quintaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar4);
        sextaAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion4);

        normalita = Typeface.createFromAsset(getAssets(), "fuentes/normal.otf");
        negrita = Typeface.createFromAsset(getAssets(), "fuentes/negrita.otf");

        aBoolean = false;
        visto = false;
        seleccionados = new int[3];
        handler = new Handler();
        usuario = new HashMap<>();
        fondos =  new int[5];

        fondos[0] = R.drawable.rosado;
        fondos[1] = R.drawable.verde;
        fondos[2] = R.drawable.amarillo;
        fondos[3] = R.drawable.morado;
        fondos[4] = R.drawable.cyan;

        if (extras != null) {
            puntuacion = extras.getInt("PUNTOS");
            nivel = extras.getInt("NIVEL");
            competencia = extras.getBoolean("COMPETENCIA");
            intentos = extras.getInt("INTENTOS");
        }

        if (savedInstanceState != null ){
            puntuacion = savedInstanceState.getInt("PUNTOSYA");
            reinicio = savedInstanceState.getBoolean("REINICIO");
            focus = savedInstanceState.getBoolean("REINICIO");
        }else{
            reinicio = true;
            focus = true;
        }

        actualizarPuntos();

        record = findViewById(R.id.record);
        record.setTypeface(normalita);
        puntosRecord = findViewById(R.id.puntosRecord);
        puntosRecord.setTypeface(negrita);
        puntosRecord.setElevation(32);

        textoToast =  viewToast.findViewById(R.id.textoToast);
        imagenToast = viewToast.findViewById(R.id.imagenToast);
        textoToast.setTypeface(normalita);

        textoMultiplica = findViewById(R.id.textoMultiplica);
        punticosOp = findViewById(R.id.punticos);
        playAgain = findViewById(R.id.playAgain);
        multiplica = findViewById(R.id.multiplicar);
        menu = findViewById(R.id.homeFin);
        score = findViewById(R.id.score);

        multiplica.setEnabled(false);
        multiplica.setAlpha((float) 0.6);

        random = new Random(System.currentTimeMillis());
        textoMultiplica.setTypeface(negrita);

        seleccionados[0] = fondos[random.nextInt(5)];
        seleccionados[1] = fondos[random.nextInt(5)];
        seleccionados[2] = fondos[random.nextInt(5)];

        while (seleccionados[0] == seleccionados[1]){
            seleccionados[0] = fondos[random.nextInt(5)];
        }
        while (seleccionados[1] == seleccionados[2] || seleccionados[0] == seleccionados[2]){
            seleccionados[2] = fondos[random.nextInt(5)];
        }

        playAgain.setBackgroundResource(seleccionados[0]);
        menu.setBackgroundResource(seleccionados[1]);
        multiplica.setBackgroundResource(seleccionados[2]);
        punticosOp.setTypeface(normalita);
        score.setElevation(32);
        score.setTypeface(negrita);

        if(puntuacion>nuevoRecord){
            nuevoRecord = puntuacion;
            punticosOp.setText(getString(R.string.nuevoRecord));
            score.setText(String.valueOf(nuevoRecord));
            puntosRecord.setText(String.valueOf(nuevoRecord));
            guardarRecord();
        }else{
            score.setText(String.valueOf(puntuacion));
            puntosRecord.setText(String.valueOf(nuevoRecord));
        }

        score.startAnimation(primeraAnimacion);
        playAgain.startAnimation(segundaAnimacion);
        puntosRecord.startAnimation(cuartaAnimacion);

        multiplica.startAnimation(quintaAnimacion);
        zoom = 2;
        veces = 0;

        quintaAnimacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                veces++;
                if(veces == 2){
                    veces = 0;
                    zoom--;
                    if(zoom == 0){
                        multiplica.startAnimation(sextaAnimacion);
                    }else {
                        multiplica.startAnimation(quintaAnimacion);
                    }
                }else{
                    multiplica.startAnimation(quintaAnimacion);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        sextaAnimacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                multiplica.startAnimation(quintaAnimacion);
                zoom = 2;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        if (competencia) {
            intentos--;

            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            id = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
            usuarios = db.collection("Usuarios");

            if (datos.getBoolean("SUBIRPUNTOS", true)) {
                usuarios.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        usuario = Objects.requireNonNull(task.getResult()).getData();
                        puntosRanking = Integer.parseInt((String) Objects.requireNonNull(Objects.requireNonNull(usuario).get("puntos")));
                        int puntosTo = puntosRanking + puntuacion;

                        usuario.put("puntos", Integer.toString(puntosTo));
                        usuario.put("intentos", Integer.toString(intentos));

                        usuarios.document(id).update(usuario);
                        subirPuntos(puntosTo);
                    }
                });
            }
        }
    }

    public void subirPuntos(final int puntos){
        if (datos.getBoolean("VERSION", true)){
            Games.getLeaderboardsClient(FinalActivity.this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(FinalActivity.this)))
                    .submitScore(getString(R.string.leaderboard_ranking), puntos);
        }else {
            textoToast.setText(getString(R.string.sinRanking));
            imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
            toast.show();
        }
    }

    public void guardarRecord(){
        editor = datos.edit();
        editor.putInt("RECORD", nuevoRecord);
        editor.apply();
    }

    public void actualizarPuntos(){
        editor = datos.edit();
        editor.putInt("PUNTICOS", (puntosViejos + puntuacion));
        editor.apply();
    }

    public void cargarVideo(){
        rewardedVideoAd.loadAd(getString(R.string.video_puntos), new AdRequest.Builder()
                .build());
    }
    public void multiplicaPuntos(View view){
        if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
        rewardedVideoAd.show();
    }

    public void playAgain(View view){
        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();
        if (competencia) {
            if (isOnline(this)) {
                if (datos.getBoolean("VERSION", true)){
                    if (intentos > 0) {
                        salir = false;
                        if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                        Intent intent = new Intent(FinalActivity.this, MainActivity.class);
                        intent.putExtra("NIVEL", nivel);
                        intent.putExtra("INTENTOS", Integer.toString(intentos));
                        intent.putExtra("COMPETENCIA", competencia);
                        startActivity(intent);
                    } else {
                        if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
                        textoToast.setText(getString(R.string.sinIntentos));
                        imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                        toast.show();
                    }
                }else {
                    if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
                    textoToast.setText(getString(R.string.sinRankingPlay));
                    imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                    toast.show();
                }
            }else {
                if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
                textoToast.setText(getString(R.string.textoAlertaInternet));
                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                toast.show();
            }
        } else {
            salir = false;
            if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NIVEL", nivel);
            intent.putExtra("INTENTOS", Integer.toString(intentos));
            intent.putExtra("COMPETENCIA", competencia);
            startActivity(intent);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (musicaSi) startService(new Intent(this, Musica.class));
        }else {
            focus = true;
            if (salir) stopService(new Intent(this, Musica.class));
        }
    }

    public static boolean isOnline(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void menu(View view){
        salir = false;
        if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
        if (competencia){
            if (isOnline(this) && datos.getBoolean("VERSION", true)) {
                Intent intent = new Intent(this, CompetenciaActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, InicioActivity.class);
                startActivity(intent);
            }
        }else {
            Intent intent = new Intent(this, NivelesActivity.class);
            startActivity(intent);
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
            Toast.makeText(FinalActivity.this, "Presiona de nuevo para salir", Toast.LENGTH_LONG).show();
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
        datos.edit().putBoolean("SUBIRPUNTOS", false).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        salir = true;
        if (musicaSi && focus) startService(new Intent(this, Musica.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (salir) stopService(new Intent(this, Musica.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("PUNTOSYA", puntuacion);
        outState.putBoolean("REINICIO", false);
        outState.putBoolean("FOCUS", false);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (!visto  && reinicio){
            multiplica.setEnabled(true);
            multiplica.setAlpha((float) 0.99999);
        }
    }
    @Override
    public void onRewardedVideoAdOpened() {
    }
    @Override
    public void onRewardedVideoStarted() {
    }
    @Override
    public void onRewardedVideoAdClosed() {
        if (!visto) {
            multiplica.setEnabled(false);
            multiplica.setAlpha((float) 0.6);
            textoToast.setText(getString(R.string.sinVideo));
            imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
            toast.show();
            if (sonidosSi) soundPool.play(fallo, 2f,2f,1, 0, 1);
            cargarVideo();
        }
    }
    @Override
    public void onRewarded(RewardItem rewardItem) {

    }
    @Override
    public void onRewardedVideoAdLeftApplication() {

    }
    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        cargarVideo();
    }
    @Override
    public void onRewardedVideoCompleted() {
        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();
        puntuacion = (int) (puntuacion * (1.3));
        punticosOp.setText(getString(R.string.nuevosPuntos));
        score.setText(String.valueOf(puntuacion));
        terceraAnimacion.setRepeatCount(3);
        score.startAnimation(terceraAnimacion);
        actualizarPuntos();
        visto = true;
        salir = true;
        multiplica.setEnabled(false);
        multiplica.setAlpha((float) 0.6);
        if (competencia){
            int puntosTo = puntosRanking + puntuacion;
            usuarios.document(id).update("puntos", Integer.toString(puntosTo));
            subirPuntos(puntosTo);
        }
    }
}