package com.example.android.obiectepierdute;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackgroundReportsTask extends AsyncTask<String,Void,String> {

    Context ctx;

    public BackgroundReportsTask(Context ctx ) {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String rer_url="http://34.205.211.253/obiecte-pierdute/core.php/obiecte";
        String method= params[0];
        if(method.equals("register")) {

            String nume = params[1];
            String hartuire = params[2];
            String mesaj = params[3];
            String locatie = params[4];
            String email = params[5];
            String utilizator = params[6];
            String privacy = params[7];

            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL(rer_url);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nume", nume);
                jsonObject.put("hartuire", hartuire);
                jsonObject.put("mesaj", mesaj);
                jsonObject.put("locatie", locatie);
                jsonObject.put("email", email);
                jsonObject.put("utilizator", utilizator);
                jsonObject.put("privacy", privacy);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 );
                conn.setConnectTimeout( 15000 );
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                conn.connect();

                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                os.flush();
                is = conn.getInputStream();
                return "Multumim pentru sesizarea facuta!";
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } finally {
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
