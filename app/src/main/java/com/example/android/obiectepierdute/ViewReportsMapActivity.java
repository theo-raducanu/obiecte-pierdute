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
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.android.obiectepierdute.R.id.map;

public class ViewReportsMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    private static final LatLng BUCHAREST = new LatLng(44.4356554,26.1009694);
    String jsonString;
    String jsonFinal;
    JSONArray jsonArray;

    /**
     * Overlay that shows a short help text when first launched. It also provides an option to
     * exit the app.
     */
    /*private DismissOverlayView mDismissOverlay;*/

    /**
     * The map. It is initialized when the map has been fully loaded and is ready to be used.
     *
     * @see #onMapReady(com.google.android.gms.maps.GoogleMap)
     */
    private GoogleMap mMap;

    private MapFragment mMapFragment;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        // Set the layout. It only contains a SupportMapFragment and a DismissOverlay.
        setContentView(R.layout.activity_view_reports_map);

        // Enable ambient support, so the map remains visible in simplified, low-color display
        // when the user is no longer actively using the app but the app is still visible on the
        // watch face.
        /*setAmbientEnabled();*/

        // Retrieve the containers for the root of the layout and the map. Margins will need to be
        // set on them to account for the system window insets.
        final FrameLayout topFrameLayout = (FrameLayout) findViewById(R.id.root_container);
        final FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_container);

        // Set the system view insets on the containers when they become available.
        topFrameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Call through to super implementation and apply insets
                insets = topFrameLayout.onApplyWindowInsets(insets);

                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mapFrameLayout.getLayoutParams();

                // Add Wearable insets to FrameLayout container holding map as margins
                params.setMargins(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                mapFrameLayout.setLayoutParams(params);

                return insets;
            }
        });

        // Obtain the DismissOverlayView and display the intro help text.
        /*mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);*/
        /*mDismissOverlay.setIntroText(R.string.intro_text);
        mDismissOverlay.showIntroIfNecessary();*/

        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mMapFragment.getMapAsync(this);

    }

    /*private void setAmbientEnabled() {
    }


    *//**
     * Starts ambient mode on the map.
     * The API swaps to a non-interactive and low-color rendering of the map when the user is no
     * longer actively using the app.
     *//*
    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        mMapFragment.onEnterAmbient();
        mMapFragment.onEnterAmbient(ambientDetails);
    }

    *//**
     * Exits ambient mode on the map.
     * The API swaps to the normal rendering of the map when the user starts actively using the app.
     *//*
    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        mMapFragment.onExitAmbient();
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Map is ready to be used.
        mMap = googleMap;

        // Set the long click listener as a way to exit the map.
        mMap.setOnMapLongClickListener(this);

        // Add a marker with a title that is shown in its info window.
        /*mMap.addMarker(new MarkerOptions().position(BUCHAREST)
                .title("Sydney Opera House"));*/

        // Move the camera to show the marker.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BUCHAREST, 11));
        try {
            retrieveAndAddCities();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Display the dismiss overlay with a button to exit this activity.
        /*mDismissOverlay.show();*/
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
        }*/

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        /*runOnUiThread(new Runnable() {
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
        /*Toast.makeText(this, jsonFinal , Toast.LENGTH_LONG).show();*/
        createMarkersFromJson(jsonFinal);
    }

    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            /*Toast.makeText(this, jsonObj.getString("nume_obiect"), Toast.LENGTH_LONG).show();*/
            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title(jsonObj.getString("nume_obiect"))
                    .position(new LatLng(
                            /*jsonObj.getJSONArray("latlng").getDouble(0),
                            /*jsonObj.getJSONArray("latlng").getDouble(1) */
                            Double.parseDouble(jsonObj.getString("latitude")), Double.parseDouble(jsonObj.getString("longitude"))
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
            /*TextView textView = (TextView) findViewById(R.id.textView);*/

            jsonFinal= result;
            /*textView.setText("1"+jsonFinal);*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}

/*public class ViewReportsMapActivity extends FragmentActivity implements OnMapReadyCallback   {
    private static final String LOG_TAG = "ExampleApp";

    private static final String SERVICE_URL = "http://34.205.211.253/obiecte-pierdute/core.php/obiecte";

    protected GoogleMap map;
    String jsonString;
    String jsonFinal;
    JSONArray jsonArray;

    *//*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_map);
        setUpMapIfNeeded();
    }*//*

    @Override
    protected void onResume() {
        super.onResume();
        *//*setUpMapIfNeeded();*//*
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_map);
        View view = inflater.inflate(R.layout.activity_view_reports_map, null, false);
        Toast.makeText(this, "Noroc1" , Toast.LENGTH_LONG).show();
        *//*setUpMapIfNeeded();*//*
        *//*SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);*//*
        try {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } catch ( Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
        }

        *//*try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this); // <---it said "onMapReadyCallback can not be applied to this activity;
        } catch ( Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
            *//**//*TextView textView = (TextView) findViewById(R.id.editText);
            textView.setText(e.getMessage());*//**//*
        }*//*
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Noroc2" , Toast.LENGTH_LONG).show();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        *//*setUpMap();*//*
    }

    *//*private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync();
            if (map != null) {
                setUpMap();
            }
        }
    }*//*

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
        *//*HttpURLConnection conn = null;
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
        });*//*
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
                            *//*jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1)*//*
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
            *//*TextView textView = (TextView) findViewById(R.id.textView);

            jsonFinal= result;*//*
            *//*textView.setText("1"+jsonFinal);*//*
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}*/
