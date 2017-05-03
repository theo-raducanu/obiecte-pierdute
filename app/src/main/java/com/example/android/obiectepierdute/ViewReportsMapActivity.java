package com.example.android.obiectepierdute;

/*import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewReportsMapActivity extends AppCompatActivity {

    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_map);

        mapButton = (Button) findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri locationUri = Uri.parse("geo:44.4356554,26.1009694?q=Strada+Lipscani");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,locationUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}*/

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ViewReportsMapActivity extends FragmentActivity implements OnMapReadyCallback   {
    private static final String LOG_TAG = "ExampleApp";

    private static final String SERVICE_URL = "http://34.205.211.253/obiecte-pierdute/core.php/obiecte";

    protected GoogleMap map;
    String jsonString;
    String jsonFinal;
    JSONArray jsonArray;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_map);
        setUpMapIfNeeded();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        /*setUpMapIfNeeded();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_map);
        /*setUpMapIfNeeded();*/
        /*SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        try {
            mapFragment.getMapAsync(this); // <---it said "onMapReadyCallback can not be applied to this activity;
        } catch ( Exception e) {
            e.printStackTrace();
            TextView textView = (TextView) findViewById(R.id.editText);
            textView.setText(e.getMessage());
        }
        setUpMap();
        Toast.makeText(this, "Noroc" , Toast.LENGTH_LONG).show();
    }

    /*private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync();
            if (map != null) {
                setUpMap();
            }
        }
    }*/

    private void setUpMap() {
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException, JSONException {
        /*HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });*/
        BackgroundTask task = new BackgroundTask();
        try {
            jsonFinal = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, jsonFinal , Toast.LENGTH_LONG).show();
        createMarkersFromJson(jsonFinal);
    }

    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Toast.makeText(this, jsonObj.getString("nume_obiect") , Toast.LENGTH_LONG).show();
            map.addMarker(new MarkerOptions()
                    .title(jsonObj.getString("nume_obiect"))
                    .position(new LatLng(
                            /*jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1)*/
                            44.4356554,26.1009694
                    ))
            );
        }
    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String jsonUrl= "http://34.205.211.253/obiecte-pierdute/core.php/obiecte";
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url= new URL(jsonUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder= new StringBuilder();

                while ((jsonString= bufferedReader.readLine())!= null) {
                    stringBuilder.append(jsonString+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public BackgroundTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            /*TextView textView = (TextView) findViewById(R.id.textView);

            jsonFinal= result;*/
            /*textView.setText("1"+jsonFinal);*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
