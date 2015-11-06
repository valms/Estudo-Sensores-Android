package br.com.valmarjunior.estudosensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SensoresActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorMagnetico;
    private Sensor sensorAcelerometro;
    private ImageView imageView;
    private TextView textView;
    private float direcaoAtual = 0f;
    private float[] dadosGeoreferenciado;
    private float[] dadosGeomagneticos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        this.imageView = (ImageView) this.findViewById(R.id.imagemBussula);
        this.textView = (TextView) this.findViewById(R.id.textViewGraus);

        this.sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        this.sensorMagnetico = this.sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        // this.sensorAcelerometro = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensorMagnetico, SensorManager.SENSOR_DELAY_GAME);
        this.sensorManager.registerListener(this, this.sensorAcelerometro, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Previnindo o Sensor drenar bateria. (Boa prática)
        this.sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tratarDadosSensores(event);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void tratarDadosSensores(SensorEvent sensorEvent) {

        float graus = Math.round(sensorEvent.values[0]);

        this.textView.setText(String.valueOf(graus));


        RotateAnimation rotateAnimation = new RotateAnimation(direcaoAtual, -graus,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
        direcaoAtual = -graus;


    }
}
