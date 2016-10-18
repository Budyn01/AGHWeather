package com.budyn.aghweather;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String WEATHER_URL = "http://meteo.ftj.agh.edu.pl/meteo/meteo.xml";
    public static final String TAG = "AGH Weather";


    private void makeNotification(){
        Download download = new Download();
        download.execute(WEATHER_URL);
        String text = download.makeNotification();

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.logo_agh)
                        .setContentTitle("Pogoda AGH")
                        .setContentText(text);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int mNotificationId = 001;
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
        makeNotification();
        new CountDownTimer(10*60*1000, 60*1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                makeNotification();
                this.start();
            }
        }.start();



    }
}
