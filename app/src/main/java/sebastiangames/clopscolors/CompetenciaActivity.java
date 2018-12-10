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
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class CompetenciaActivity extends AppCompatActivity {
    private static final int RC_LEADERBOARD_UI = 9004;
    private ImageView fondoPremio;
    private ImageManager imageManager;
    private ImageView imagenJugador, imagenToast;
    private String  intentos, id, puntos;
    private FrameLayout infoCompe, competir, botonAlerta, botonAlertaNo, botonAlertaSi, botonIntentos;
    private TextView textoPuntosIntentos, puntosJuador, nombreJugador, puestoJugador,
            textoBotonAlerta, mensajeAlerta, tituloAlerta, textoToast, textoBotonNo, textoBotonSi;
    private Animation primeraAnimacion, cuartaAnimacion;
    private int[] premiosFondo;
    private CollectionReference usuarios;
    private Map<String, Object> usuario;
    private Boolean aBoolean, sonidosSi, create, musicaSi, salir, actualizacion;
    private Handler handler;
    private SoundPool soundPool;
    private Dialog dialog, premios, ganador, progressBar;
    private int puntosIntentos, numeroPremio, efecto, intents, fallo, nice;
    private SharedPreferences datos;
    private SharedPreferences.Editor editor;
    private Toast toast;
    private CardView cardIntentos;
    private EditText numeroGanador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competencia);

        FirebaseAuth mAuth;
        FirebaseFirestore db;
        ImageView cerrar,informaImagen;
        Random random;
        FrameLayout homeCompe, posiciones, masIntentos, botonPremios, botonGanador;
        TextView textoCompetir, textoIntentos, textoBotonIntentos, textoPremios, textoBotonPremio, tituloGanador, mensajeGanador, textoBotonGanador;
        Animation segundaAnimacion, terceraAnimacion, quientaAnimacion, sextaAnimacion;
        Typeface negrita, normalita;
        int[] colores, seleccionados;
        AdView adView;
        View viewToast;

        datos = getSharedPreferences("MisDatos", Context.MODE_PRIVATE);
        puntosIntentos = datos.getInt("PUNTICOS", 0);
        musicaSi = datos.getBoolean("MUSICA", true);
        sonidosSi = datos.getBoolean("SONIDOS", true);
        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
                .build();
        efecto = soundPool.load(this, R.raw.efecto, 1);
        intents = soundPool.load(this, R.raw.intents, 1);
        fallo = soundPool.load(this, R.raw.fallo, 1);
        nice = soundPool.load(this, R.raw.fin, 1);

        datos.edit().putBoolean("PARTIDAPERDIDA", false).apply();

        adView = findViewById(R.id.adViewCompetir);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        progressBar = new Dialog(this);
        progressBar.setContentView(R.layout.progress);
        Objects.requireNonNull(progressBar.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        random = new Random(System.currentTimeMillis());
        colores = new int[5];
        numeroPremio = 0;
        create = false;
        seleccionados = new int[3];
        premiosFondo = new int[5];
        usuario = new HashMap<>();
        aBoolean = false;
        actualizacion = false;
        handler = new Handler();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        viewToast = inflater.inflate(R.layout.toast, null);
        toast = new Toast(CompetenciaActivity.this);
        toast.setView(viewToast);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);

        normalita = Typeface.createFromAsset(getAssets(), "fuentes/normal.otf");
        negrita = Typeface.createFromAsset(getAssets(), "fuentes/negrita.otf");

        imageManager = ImageManager.create(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alerta);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ganador = new Dialog(this);
        ganador.setContentView(R.layout.ganador);
        ganador.setCancelable(false);
        Objects.requireNonNull(ganador.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textoBotonGanador = ganador.findViewById(R.id.textoBotonGanador);
        tituloGanador = ganador.findViewById(R.id.tiutloGanador);
        mensajeGanador = ganador.findViewById(R.id.mensajeGanador);
        numeroGanador = ganador.findViewById(R.id.numeroGanador);
        botonGanador = ganador.findViewById(R.id.botonGanador);

        premios = new Dialog(this);
        premios.setContentView(R.layout.premios);
        Objects.requireNonNull(premios.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        homeCompe = findViewById(R.id.homiCompi);
        infoCompe = findViewById(R.id.infoCompetencia);
        competir = findViewById(R.id.competir);
        masIntentos = findViewById(R.id.masIntentos);
        posiciones = findViewById(R.id.posiciones);
        textoCompetir = findViewById(R.id.textoCompetir);
        textoPremios = findViewById(R.id.textoPremios);
        textoPuntosIntentos = findViewById(R.id.intentos);
        textoIntentos = findViewById(R.id.textoIntentos);
        informaImagen = findViewById(R.id.imagenInfo);
        imagenJugador = findViewById(R.id.imagenJugador);
        puntosJuador = findViewById(R.id.puntosJugador);
        nombreJugador = findViewById(R.id.nombreJugador);
        puestoJugador = findViewById(R.id.puestoJugador);
        botonAlerta = dialog.findViewById(R.id.botonAlerta);
        botonAlertaNo = dialog.findViewById(R.id.botonNo);
        botonAlertaSi = dialog.findViewById(R.id.botonSi);
        tituloAlerta = dialog.findViewById(R.id.tituloAlerta);
        mensajeAlerta = dialog.findViewById(R.id.mensajeAlerta);
        textoBotonAlerta = dialog.findViewById(R.id.textoBotonAlerta);
        botonIntentos = dialog.findViewById(R.id.botonIntentos);
        textoBotonIntentos = dialog.findViewById(R.id.textoBotonIntentos);
        textoBotonNo = dialog.findViewById(R.id.textoBotonNo);
        textoBotonSi = dialog.findViewById(R.id.textoBotonSi);
        textoToast =  viewToast.findViewById(R.id.textoToast);
        cardIntentos = viewToast.findViewById(R.id.cardIntentos);
        imagenToast = viewToast.findViewById(R.id.imagenToast);
        cerrar = premios.findViewById(R.id.cerrar);
        botonPremios = premios.findViewById(R.id.enterate);
        textoBotonPremio = premios.findViewById(R.id.textoBotonPremios);
        fondoPremio = premios.findViewById(R.id.fondoPremio);

        textoToast.setTypeface(normalita);
        textoCompetir.setTypeface(negrita);
        textoPremios.setTypeface(normalita);
        textoIntentos.setTypeface(normalita);
        textoPuntosIntentos.setTypeface(negrita);
        nombreJugador.setTypeface(normalita);
        puntosJuador.setTypeface(negrita);
        puestoJugador.setTypeface(negrita);
        tituloAlerta.setTypeface(negrita);
        mensajeAlerta.setTypeface(normalita);
        textoBotonAlerta.setTypeface(negrita);
        textoBotonNo.setTypeface(negrita);
        textoBotonSi.setTypeface(negrita);
        textoBotonIntentos.setTypeface(negrita);
        textoBotonPremio.setTypeface(negrita);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        id = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        usuarios = db.collection("Usuarios");

        progressBar.show();
        usuarios.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (Objects.requireNonNull(task.getResult()).get("intentos") == null || task.getResult().get("puntos") == null){
                    intentos = "10";
                    puntos = "0";
                }else {
                    puntos = (String) task.getResult().get("puntos");
                    intentos = (String) task.getResult().get("intentos");
                }

                mostrarPerfil();

                if (Objects.requireNonNull(task.getResult()).get("ganador") != null && Objects.equals(task.getResult().get("ganador"), "Sisa")){
                    usuario.put("ganador", "Sisa");
                    ganador.show();
                }else {
                    usuario.put("ganador", "");
                }
                usuario.put("puntos", puntos);
                usuario.put("intentos", intentos);

                if (task.getResult().exists()){
                    usuarios.document(id).update(usuario);
                }else {
                    usuarios.document(id).set(usuario);
                }
                textoPuntosIntentos.setText(intentos);
            }
        });

        colores[0] = R.drawable.rosado;
        colores[1] = R.drawable.verde;
        colores[2] = R.drawable.amarillo;
        colores[3] = R.drawable.morado;
        colores[4] = R.drawable.cyan;

        seleccionados[0] = colores[random.nextInt(5)];
        seleccionados[1] = colores[random.nextInt(5)];
        seleccionados[2] = colores[random.nextInt(5)];

        premiosFondo[0] = R.drawable.clowns;
        premiosFondo[1] = R.drawable.skin;
        premiosFondo[2] = R.drawable.ofocus;
        premiosFondo[3] = R.drawable.superfood;
        premiosFondo[4] = R.drawable.leo;

        fondoPremio.setBackgroundResource(premiosFondo[numeroPremio]);

        while (seleccionados[0] == seleccionados[1]){
            seleccionados[0] = colores[random.nextInt(5)];
        }
        while (seleccionados[1] == seleccionados[2] || seleccionados[0] == seleccionados[2]){
            seleccionados[2] = colores[random.nextInt(5)];
        }

        competir.setBackgroundResource(seleccionados[0]);
        infoCompe.setBackgroundResource(seleccionados[1]);
        homeCompe.setBackgroundResource(seleccionados[2]);

        primeraAnimacion = AnimationUtils.loadAnimation(this, R.anim.agrandar);
        competir.setEnabled(false);
        competir.setAlpha((float) 0.8);

        segundaAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion3);
        textoPuntosIntentos.startAnimation(segundaAnimacion);

        terceraAnimacion = AnimationUtils.loadAnimation(this, R.anim.rotacion6);
        cuartaAnimacion = AnimationUtils.loadAnimation(this, R.anim.agran);
        quientaAnimacion = AnimationUtils.loadAnimation(this, R.anim.ganador);
        sextaAnimacion = AnimationUtils.loadAnimation(this, R.anim.ganadorboton);

        textoBotonGanador.setTypeface(negrita);
        tituloGanador.setTypeface(negrita);
        mensajeGanador.setTypeface(normalita);
        numeroGanador.setTypeface(normalita);
        botonGanador.startAnimation(sextaAnimacion);
        tituloGanador.startAnimation(quientaAnimacion);

        botonGanador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganador.dismiss();
                usuarios.document(id).update("ganador", numeroGanador.getText().toString().trim());
            }
        });

        cuartaAnimacion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        infoCompe.startAnimation(cuartaAnimacion);
                    }
                }, 3100);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                infoCompe.startAnimation(cuartaAnimacion);
            }
        }, 1000);
        informaImagen.startAnimation(terceraAnimacion);
        informaImagen.setScaleX((float) 0.9);
        informaImagen.setScaleY((float) 0.9);

        competir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.show();
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                if (isOnline(CompetenciaActivity.this)) {
                    Games.getLeaderboardsClient(CompetenciaActivity.this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(CompetenciaActivity.this)))
                            .loadCurrentPlayerLeaderboardScore(getString(R.string.leaderboard_ranking), 2, 0)
                            .addOnCompleteListener(new OnCompleteListener<AnnotatedData<LeaderboardScore>>() {
                                @Override
                                public void onComplete(@NonNull Task<AnnotatedData<LeaderboardScore>> task) {
                                    try {
                                        Objects.requireNonNull(task.getResult()).get();
                                        if (!intentos.equals("0")) {
                                            if (puntos.equals("0")){
                                                progressBar.dismiss();
                                                alertaCompetir();
                                            }else {
                                                progressBar.dismiss();
                                                salir = false;
                                                Intent intent = new Intent(CompetenciaActivity.this, MainActivity.class);
                                                intent.putExtra("NIVEL", 4);
                                                intent.putExtra("COMPETENCIA", true);
                                                intent.putExtra("INTENTOS", intentos);
                                                startActivity(intent);
                                            }
                                        }else {
                                            progressBar.dismiss();
                                            textoToast.setText(getString(R.string.sinIntentos));
                                            cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                            imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                                            toast.show();
                                        }

                                    } catch (Exception e) {
                                        progressBar.dismiss();
                                        actualizacion = true;
                                        alertaActu();
                                    }
                                }
                            });
                }else {
                    progressBar.dismiss();
                    alertaInternet();
                }
            }
        });

        botonPremios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.page_face))));
                }catch (Exception e){
                    textoToast.setText(getString(R.string.sinFace));
                    cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                    toast.show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.web_face))));
                }
            }
        });

        masIntentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaIntentos();
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });

        homeCompe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir = false;
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                Intent intent = new Intent(CompetenciaActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        botonAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(CompetenciaActivity.this) && (!actualizacion)) {
                    if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                    dialog.dismiss();
                }else {
                    if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                    salir = false;
                    Intent intent = new Intent(CompetenciaActivity.this, InicioActivity.class);
                    startActivity(intent);
                }
            }
        });

        botonAlertaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir = false;
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                Intent intent = new Intent(CompetenciaActivity.this, NivelesActivity.class);
                startActivity(intent);
            }
        });
        botonAlertaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir = false;
                if (sonidosSi) soundPool.play(intents, 0.5f,0.5f,1, 0, 1);
                dialog.dismiss();
                Intent intent = new Intent(CompetenciaActivity.this, MainActivity.class);
                intent.putExtra("NIVEL", 4);
                intent.putExtra("COMPETENCIA", true);
                intent.putExtra("INTENTOS", intentos);
                startActivity(intent);
            }
        });

        botonIntentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (puntosIntentos < 3000){
                    if (sonidosSi) soundPool.play(fallo, 1,1,1, 0, 1);
                    textoToast.setText(getString(R.string.falloMensaje));
                    dialog.dismiss();
                    cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.errortoast));
                    toast.show();
                }else {
                    if (isOnline(CompetenciaActivity.this)) {
                        if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                        intentos = Integer.toString(Integer.parseInt(intentos) + 1);
                        textoPuntosIntentos.setText(intentos);
                        puntosIntentos =  puntosIntentos - 3000;

                        editor = datos.edit();
                        editor.putInt("PUNTICOS", puntosIntentos);
                        editor.apply();

                        usuarios.document(id).update("intentos", intentos);

                        dialog.dismiss();
                        textoToast.setText(getString(R.string.felicidadesMensaje));
                        cardIntentos.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.correcto));
                        toast.show();
                    }else {
                        alertaInternet();
                    }
                }
            }
        });

        posiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                progressBar.show();
                if (isOnline(CompetenciaActivity.this)) {
                    Games.getLeaderboardsClient(CompetenciaActivity.this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(CompetenciaActivity.this)))
                            .loadCurrentPlayerLeaderboardScore(getString(R.string.leaderboard_ranking), 2, 0)
                            .addOnCompleteListener(new OnCompleteListener<AnnotatedData<LeaderboardScore>>() {
                                @Override
                                public void onComplete(@NonNull Task<AnnotatedData<LeaderboardScore>> task) {
                                    try {
                                        Objects.requireNonNull(task.getResult()).get();
                                        Games.getLeaderboardsClient(getApplicationContext(), Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getApplicationContext())))
                                                .getLeaderboardIntent(getString(R.string.leaderboard_ranking))
                                                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                                                    @Override
                                                    public void onSuccess(Intent intent) {
                                                        Games.getLeaderboardsClient(CompetenciaActivity.this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(CompetenciaActivity.this)))
                                                                .submitScore(getString(R.string.leaderboard_ranking), Integer.parseInt(puntos));
                                                        salir = false;
                                                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                                                        progressBar.dismiss();
                                                        create = true;
                                                    }
                                                });
                                    } catch (Exception e) {
                                        progressBar.dismiss();
                                        actualizacion = true;
                                        alertaActu();
                                    }
                                }
                            });



                }else {
                    progressBar.dismiss();
                    alertaInternet();
                }

            }
        });

        fondoPremio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                if (numeroPremio == 4) numeroPremio = -1;
                fondoPremio.setBackgroundResource(premiosFondo[numeroPremio + 1]);
                numeroPremio = numeroPremio + 1;
            }
        });

        infoCompe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
                premios.show();
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                premios.dismiss();
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });

        imagenJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sonidosSi) soundPool.play(efecto, 1,1,1, 0, 1);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sonidosSi && create) soundPool.play(efecto, 1,1,1, 0, 1);
        if (GoogleSignIn.getLastSignedInAccount(this) == null){
            salir = false;
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(CompetenciaActivity.this, InicioActivity.class);
            startActivity(intent);
        }
    }

    private void alertaIntentos(){
        tituloAlerta.setText(getString(R.string.tituloIntentos));
        mensajeAlerta.setText(String.valueOf(puntosIntentos));
        mensajeAlerta.setTextSize(50);
        botonIntentos.setVisibility(View.VISIBLE);
        botonIntentos.setEnabled(true);
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.INVISIBLE);
        botonAlerta.setEnabled(false);
        dialog.show();
    }

    private void alertaCompetir(){
        tituloAlerta.setText(getString(R.string.tituloCompetir));
        mensajeAlerta.setText(getString(R.string.mensajeCompetir));
        mensajeAlerta.setTextSize(25);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        botonAlertaNo.setVisibility(View.VISIBLE);
        textoBotonNo.setText(getString(R.string.botonCompetirNo));
        botonAlertaNo.setEnabled(true);
        botonAlertaSi.setVisibility(View.VISIBLE);
        textoBotonSi.setText(getString(R.string.botonCompetirSi));
        botonAlertaSi.setEnabled(true);
        botonAlerta.setVisibility(View.INVISIBLE);
        botonAlerta.setEnabled(false);
        dialog.show();
    }
    private void alertaActu() {
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
        dialog.show();
    }
    private void alertaInternet() {
        tituloAlerta.setText(getString(R.string.tituloAlertaInternet));
        mensajeAlerta.setText(getString(R.string.textoAlertaInternet));
        textoBotonAlerta.setText(getString(R.string.botonAlertaInternet));
        mensajeAlerta.setTextSize(25);
        botonAlertaNo.setVisibility(View.INVISIBLE);
        botonAlertaNo.setEnabled(false);
        botonAlertaSi.setVisibility(View.INVISIBLE);
        botonAlertaSi.setEnabled(false);
        botonAlerta.setVisibility(View.VISIBLE);
        botonAlerta.setEnabled(true);
        botonIntentos.setVisibility(View.INVISIBLE);
        botonIntentos.setEnabled(false);
        dialog.show();
    }

    public static boolean isOnline(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void mostrarPerfil(){
        progressBar.dismiss();
        Games.getPlayersClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).getCurrentPlayer().addOnCompleteListener(new OnCompleteListener<Player>() {
            @Override
            public void onComplete(@NonNull Task<Player> task) {
                if (task.isSuccessful()){
                    Uri uri = Objects.requireNonNull(task.getResult()).getHiResImageUri();
                    imageManager.loadImage(imagenJugador, uri);
                    nombreJugador.setText(Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(CompetenciaActivity.this)).getDisplayName());
                    puntosJuador.setText(puntos);
                }else{
                    imagenJugador.setBackgroundResource(R.drawable.usuarioerror);
                    nombreJugador.setText("");
                    puntosJuador.setText("");
                }
            }
        });
        Games.getLeaderboardsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)))
                .loadCurrentPlayerLeaderboardScore(getString(R.string.leaderboard_ranking), 2, 0)
                .addOnCompleteListener(new OnCompleteListener<AnnotatedData<LeaderboardScore>>() {
                    @Override
                    public void onComplete(@NonNull Task<AnnotatedData<LeaderboardScore>> task) {
                        if (task.isSuccessful()) {
                            competir.setAlpha(1);
                            competir.setEnabled(true);
                            competir.startAnimation(primeraAnimacion);

                            if(puntos.equals("0")){
                                puestoJugador.setText(getString(R.string.sinPuesto));
                            }else {
                                try{
                                    puestoJugador.setText(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get()).getDisplayRank());
                                }catch (Exception e){
                                    puestoJugador.setText("");
                                }
                            }
                        }else {
                            puestoJugador.setText("");
                        }
                    }
                });

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
            Toast.makeText(CompetenciaActivity.this, "Presiona de nuevo para salir", Toast.LENGTH_LONG).show();
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