package com.example.android.obiectepierdute;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ViewReportsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String jsonString;
    String jsonFinal;
    JSONArray jsonArray;
    JSONObject jsonObject;
    ReportAdapter reportAdapter;
    ListView listView;
    public void getJson(View view) {

        /*new BackgroundTask().execute();*/

    }

    public void parseJson(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        BackgroundTask task = new BackgroundTask();
        try {
            jsonFinal = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        reportAdapter= new ReportAdapter(this,R.layout.report_layout);
        listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(reportAdapter);
        String nume,tip_obiect,obiect,descriere,locatie,email,nr_tel;
        try {
            /*jsonObject= new JSONObject(jsonString);
            jsonArray= jsonObject.getJSONArray("server_response");*/
            int count=0;
            jsonArray= new JSONArray(jsonFinal);
            int arrayLenght = jsonArray.length();
            while (count < arrayLenght) {
                JSONObject JO= jsonArray.getJSONObject(count);
                nume= JO.getString("nume");
                tip_obiect= JO.getString("tip_obiect");
                obiect= JO.getString("nume_obiect");
                descriere= JO.getString("descriere");
                locatie= JO.getString("locatie");
                email= JO.getString("email");
                nr_tel= JO.getString("nr_tel");
                Report report= new Report(nume,tip_obiect,obiect,descriere,locatie,email,nr_tel);
                reportAdapter.add(report);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
            /*textView.setText(e.getMessage());*/
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView harassmentList = (ListView) findViewById(R.id.listview);
                ReportAdapter hListAdapter = (ReportAdapter) harassmentList.getAdapter();
                Report harassmentDetails = (Report) hListAdapter.getItem(position);
                Uri locationUri = Uri.parse("geo:44.4356554,26.1009694?q=" + harassmentDetails.getLocatie());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,locationUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_reports, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            Intent page = new Intent(this, MainActivity.class);
            startActivity(page);
            this.finish();
        } else if (id == R.id.nav_report) {
            Intent page = new Intent(this, ReportHarassmentActivity.class);
            startActivity(page);
            this.finish();
        } else if (id == R.id.nav_view_reports) {
            Intent page = new Intent(this, ViewReportsActivity.class);
            startActivity(page);
            this.finish();
        } else if (id == R.id.nav_view_reports_map) {
            Intent page = new Intent(this, ViewReportsMapActivity.class);
            startActivity(page);
        } else if (id == R.id.nav_config) {
            Intent page = new Intent(this, DataConfigActivity.class);
            startActivity(page);
        } else if (id == R.id.nav_contact) {
            Intent page = new Intent(this, ContactUsActivity.class);
            startActivity(page);
        } else if (id == R.id.nav_about_us) {
            Intent page = new Intent(this, HelpMainActivity.class);
            startActivity(page);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
