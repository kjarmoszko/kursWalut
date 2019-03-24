package com.example.kurswalut;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ConnectToNBPSchema extends AsyncTask<String, Void, RootObjectSchema> {

    @Override
    protected RootObjectSchema doInBackground(String... voids) {
        try {
            String json = readUrl("http://api.nbp.pl/api/exchangerates/rates/a/"+voids[0]+"/last/10/?format=json");
            Gson gson = new Gson();
            RootObjectSchema rootObjectSchema = gson.fromJson(json, RootObjectSchema.class);

            return rootObjectSchema;
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
