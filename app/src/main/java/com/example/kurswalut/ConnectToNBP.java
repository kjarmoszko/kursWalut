package com.example.kurswalut;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ConnectToNBP extends AsyncTask<Void, Void, RootObject[]> {

    @Override
    protected RootObject[] doInBackground(Void... voids) {
        try {
            String json = readUrl("http://api.nbp.pl/api/exchangerates/tables/a/?format=json");
            Gson gson = new Gson();
            RootObject[] rootObject = gson.fromJson(json, RootObject[].class);

            json = readUrl("http://api.nbp.pl/api/exchangerates/tables/c/?format=json");
            RootObject[] tmp = gson.fromJson(json, RootObject[].class);
            for(int i = 0; i < tmp[0].getRates().length; i++) {
                for(int j = 0; j < rootObject[0].getRates().length; j++) {
                    if(tmp[0].getRates()[i].getCode().equals(rootObject[0].getRates()[j].getCode())) {
                        rootObject[0].getRates()[j].setAsk(tmp[0].getRates()[i].getAsk());
                        rootObject[0].getRates()[j].setBid(tmp[0].getRates()[i].getBid());
                        break;
                    }
                }
            }
            for(int i = 0; i < rootObject[0].getRates().length; i++) {
                if(rootObject[0].getRates()[i].getAsk() == null) {
                    rootObject[0].getRates()[i].setAsk("-");
                    rootObject[0].getRates()[i].setBid("-");
                }
            }
            return rootObject;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);

            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
