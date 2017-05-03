package com.example.android.obiectepierdute;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BackgroundDatabaseTask extends AsyncTask<String,Void,String> {

    Context ctx;

    public BackgroundDatabaseTask(Context ctx ) {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String rer_url="http://34.205.211.253/obiecte-pierdute/core.php/obiecte";
        String method= params[0];
        if(method.equals("register")) {

            String nume = params[1];
            String tip_obiect = params[2];
            String nume_obiect = params[3];
            String descriere = params[4];
            String locatie = params[5];
            String email = params[6];
            String nr_tel = params[7];
            String latitude = "";
            String longitude = "";

            String jsonUrl="https://maps.googleapis.com/maps/api/geocode/json?address="+locatie+"Bucuresti&key=AIzaSyAtXN8BKIktvnMY8f5XAbTC1TVBvQ2gyns";
            jsonUrl = jsonUrl.replaceAll(" ", "+");
            try {
                /*URL url= new URL(jsonUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder= new StringBuilder();*/

                URL url = new URL(jsonUrl);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder= new StringBuilder();

                String jsonString;
                while ((jsonString= bufferedReader.readLine())!= null) {
                    stringBuilder.append(jsonString+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                /*URLConnection.disconnect();*/
                String jsonFinal =  stringBuilder.toString().trim();
                JSONObject mainObj = new JSONObject(jsonFinal);
                if(mainObj != null){
                    JSONArray list = mainObj.getJSONArray("results");
                    if(list != null) {
                        JSONObject geometry = list.getJSONObject(0);
                        geometry = geometry.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");
                        latitude = location.getString("lat");
                        longitude = location.getString("lng");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }/* catch (JSONException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }*/ catch (JSONException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }

            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;

            try {
                //constants
                URL url = new URL(rer_url);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nume", nume);
                jsonObject.put("tip_obiect", tip_obiect);
                jsonObject.put("nume_obiect", nume_obiect);
                jsonObject.put("descriere", descriere);
                jsonObject.put("locatie", locatie);
                jsonObject.put("email", email);
                jsonObject.put("nr_tel", nr_tel);
                jsonObject.put("latitude", latitude);
                jsonObject.put("longitude", longitude);
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
