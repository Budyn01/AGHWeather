package com.budyn.aghweather;

import android.app.NotificationManager;
import android.os.AsyncTask;
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

/**
 * Created by hlibe on 14.05.2016.
 */

//TODO do a extremal work in this place
public class Download extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        StringBuilder stringBuilder = new StringBuilder(result);
        int date;
        try {
            URL url = new URL(urls[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            date = inputStream.read();
            while(date != -1){
                stringBuilder.append((char) date);
                date = inputStream.read();
            }
            result = stringBuilder.toString();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String makeNotification(){
        try {
            String result = this.get();
            if(result != null){
                Log.i(MainActivity.TAG, "onCreate: " + result);
                Pattern pattern = Pattern.compile("data=\"(.*?)\">");
                Matcher matcher = pattern.matcher(result);
                String weatherDate = "";
                String weatherTemp = "";
                if(matcher.find()){
                    weatherDate = matcher.group(1);
                }
                pattern = Pattern.compile("<ta>(.*?)</ta>");
                matcher = pattern.matcher(result);
                if(matcher.find()){
                    weatherTemp = matcher.group(1);
                }
                Log.i(MainActivity.TAG, "onCreate: " + weatherDate);
                Log.i(MainActivity.TAG, "onCreate: " + weatherTemp);
                String l[] = weatherTemp.split(" ");
                pattern = Pattern.compile(" (.*?)+02");
                matcher = pattern.matcher(weatherDate);
                if(matcher.find()){
                    weatherDate = matcher.group(1);
                }
                Log.i(MainActivity.TAG, "onCreate: " + weatherDate);
                Date date = new Date();
                String text = "";
                Float temp = Float.parseFloat(l[0]);
                if(temp <= 0) {
                    text = "Pizga chłodem, jest: " + temp + "°C, kilka klinów na pewno pomoże!";
                } else if ( temp > 18) {
                    text = "Jest: " + temp + "°C nakurwiaj po browary!";
                } else if (temp > 0 && temp <= 18) {
                    text = "Jest: " + temp + "°C, czas na browca w akademcu!!";
                }
                return text;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
