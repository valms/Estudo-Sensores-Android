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
import android.widget.TextView;

public class SensoresActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private long ultimaAtualizacao = 0;
    private float last_x, last_y, last_z;
    private static final int SINAL_BALANCO = 600;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        this.sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        textView = (TextView) findViewById(R.id.textView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Previnindo o Sensor drenar bateria. (Boa prática)
        this.sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tratarDadosAcelerometro(event);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void tratarDadosAcelerometro(SensorEvent sensorEvent) {
        float dadosSensor[] = sensorEvent.values;

        textView.setText("X:" + String.valueOf(dadosSensor[0]) + "\nY: " + String.valueOf(dadosSensor[2])
                + "\nZ:" + String.valueOf(dadosSensor[2]));

    }
}
