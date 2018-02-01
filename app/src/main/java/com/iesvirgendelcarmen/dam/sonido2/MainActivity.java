package com.iesvirgendelcarmen.dam.sonido2;


import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
SeekBar seekbar;
TextView nombre;
TextView duracion;
MediaPlayer mediaPlayer;
double tiempoCancion;
double tiempoPasado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    seekbar = (SeekBar) findViewById(R.id.seekBar);
    nombre = (TextView) findViewById(R.id.nombre);
    duracion= (TextView) findViewById(R.id.duracion);

    mediaPlayer = MediaPlayer.create(this, R.raw.codigo_davinci);
    tiempoCancion = mediaPlayer.getDuration();
    seekbar.setClickable(false);
    seekbar.setMax((int) tiempoCancion);
    nombre.setText("codigo_davinci.mp3");




    }

    public void play(View view){
        mediaPlayer.start();
        tiempoPasado = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) tiempoPasado);
        controladorTiempo.postDelayed(actualizaBarra, 100);
    }
    public void pause(View view){
        mediaPlayer.pause();
    }
    public void rewind(View view){
        int retroceso=2000;
        if ((tiempoPasado - retroceso) > 0) {
            tiempoPasado = tiempoPasado - retroceso;
            mediaPlayer.seekTo((int) tiempoPasado);
        }
    }
    public void foward(View view){
        int avance=2000;
        if ((tiempoPasado + avance) <= tiempoCancion) {
            tiempoPasado = tiempoPasado + avance;
            mediaPlayer.seekTo((int) tiempoPasado);
            }
    }

    private Runnable actualizaBarra = new Runnable() {
        @Override
        public void run() {
            tiempoPasado = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) tiempoPasado);
            double timeRestante = tiempoCancion - tiempoPasado;

            duracion.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) timeRestante),
                    TimeUnit.MILLISECONDS.toSeconds((long) timeRestante) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                    timeRestante))));

            controladorTiempo.postDelayed(this, 100);
            controladorTiempo.postDelayed(actualizaBarra, 100);
        }
    };
    private Handler controladorTiempo = new Handler();


}
