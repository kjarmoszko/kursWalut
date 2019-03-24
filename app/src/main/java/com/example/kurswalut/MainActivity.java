package com.example.kurswalut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.DocumentsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    TextView textDate, tvlight1, tvlight2;
    ConstraintLayout bck;
    Context context;
    TextView textViewUSD, textViewEUR, textViewCHF, textViewGBP, textViewAUD, textViewCAD, textViewNOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializeConstraints();


        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(LightSensor != null) {
            mySensorManager.registerListener(LightSensorListener, LightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            tvlight1.setText("Sensor nie działa");
        }

        try {
            getExchangeRates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final SensorEventListener LightSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
                if(event.values[0]<5)
                    bck.setBackgroundColor(Color.parseColor("#FFFFFF"));
                else if(event.values[0]>=50)
                    bck.setBackgroundColor(Color.parseColor("#3D3C3C"));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



    public void getExchangeRates() throws Exception {

       // ConnectToNBP connectToNBP = new ConnectToNBP();
        RootObject[] rootObject;

        rootObject = new ConnectToNBP().execute().get();

        textDate.setText("Aktualny kurs");// z " + rootObject[0].getEffectiveDate());
        setExchangeRates(rootObject[0]);

    }

    private void setExchangeRates(RootObject data) {
        TextView textViewUSD0 = (TextView) findViewById(R.id.TW00);
        TextView textViewUSD1 = (TextView) findViewById(R.id.TW01);
        TextView textViewUSD2 = (TextView) findViewById(R.id.TW02);
        TextView textViewEUR0 = (TextView) findViewById(R.id.TW10);
        TextView textViewEUR1 = (TextView) findViewById(R.id.TW11);
        TextView textViewEUR2 = (TextView) findViewById(R.id.TW12);
        TextView textViewCHF0 = (TextView) findViewById(R.id.TW20);
        TextView textViewCHF1 = (TextView) findViewById(R.id.TW21);
        TextView textViewCHF2 = (TextView) findViewById(R.id.TW22);
        TextView textViewGBP0 = (TextView) findViewById(R.id.TW30);
        TextView textViewGBP1 = (TextView) findViewById(R.id.TW31);
        TextView textViewGBP2 = (TextView) findViewById(R.id.TW32);
        TextView textViewAUD0 = (TextView) findViewById(R.id.TW40);
        TextView textViewAUD1 = (TextView) findViewById(R.id.TW41);
        TextView textViewAUD2 = (TextView) findViewById(R.id.TW42);
        TextView textViewCAD0 = (TextView) findViewById(R.id.TW50);
        TextView textViewCAD1 = (TextView) findViewById(R.id.TW51);
        TextView textViewCAD2 = (TextView) findViewById(R.id.TW52);
        TextView textViewNOK0 = (TextView) findViewById(R.id.TW60);
        TextView textViewNOK1 = (TextView) findViewById(R.id.TW61);
        TextView textViewNOK2 = (TextView) findViewById(R.id.TW62);

        for(int i = 0; i < data.getRates().length; i++) {
            switch(data.getRates()[i].getCode()) {
                case "USD":
                    textViewUSD0.setText(data.getRates()[i].getBid());
                    textViewUSD1.setText(data.getRates()[i].getAsk());
                    textViewUSD2.setText(data.getRates()[i].getMid());
                    break;
                case "EUR":
                    textViewEUR0.setText(data.getRates()[i].getBid());
                    textViewEUR1.setText(data.getRates()[i].getAsk());
                    textViewEUR2.setText(data.getRates()[i].getMid());
                    break;
                case "CHF":
                    textViewCHF0.setText(data.getRates()[i].getBid());
                    textViewCHF1.setText(data.getRates()[i].getAsk());
                    textViewCHF2.setText(data.getRates()[i].getMid());
                    break;
                case "GBP":
                    textViewGBP0.setText(data.getRates()[i].getBid());
                    textViewGBP1.setText(data.getRates()[i].getAsk());
                    textViewGBP2.setText(data.getRates()[i].getMid());
                    break;
                case "AUD":
                    textViewAUD0.setText(data.getRates()[i].getBid());
                    textViewAUD1.setText(data.getRates()[i].getAsk());
                    textViewAUD2.setText(data.getRates()[i].getMid());
                    break;
                case "CAD":
                    textViewCAD0.setText(data.getRates()[i].getBid());
                    textViewCAD1.setText(data.getRates()[i].getAsk());
                    textViewCAD2.setText(data.getRates()[i].getMid());
                    break;
                case "NOK":
                    textViewNOK0.setText(data.getRates()[i].getBid());
                    textViewNOK1.setText(data.getRates()[i].getAsk());
                    textViewNOK2.setText(data.getRates()[i].getMid());
                    break;
            }
        }
    }

    private void inicializeConstraints(){
        bck = (ConstraintLayout) findViewById(R.id.mainActivity);
        tvlight1 = (TextView) findViewById(R.id.light1);
        tvlight2 = (TextView) findViewById(R.id.light2);
        textDate = (TextView) findViewById(R.id.textViewDate);
        context = getApplicationContext();
        textViewUSD = (TextView) findViewById(R.id.textViewUSD);
        textViewEUR = (TextView) findViewById(R.id.textViewEUR);
        textViewCHF = (TextView) findViewById(R.id.textViewCHF);
        textViewGBP = (TextView) findViewById(R.id.textViewGBP);
        textViewAUD = (TextView) findViewById(R.id.textViewAUD);
        textViewCAD = (TextView) findViewById(R.id.textViewCAD);
        textViewNOK = (TextView) findViewById(R.id.textViewNOK);

        textViewUSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","usd");
                startActivity(intent);
            }
        });
        textViewEUR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","eur");
                startActivity(intent);
            }
        });
        textViewCHF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","chf");
                startActivity(intent);
            }
        });
        textViewGBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","gbp");
                startActivity(intent);
            }
        });
        textViewAUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","aud");
                startActivity(intent);
            }
        });
        textViewCAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","cad");
                startActivity(intent);
            }
        });
        textViewNOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("currency","nok");
                startActivity(intent);
            }
        });


    }

}
