package com.example.android.obiectepierdute;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReportHarassmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinner1;
    private Button btnSubmit;
    private EditText nume, locatie;
    private TextView mesaj, email;
    private RadioGroup utilizator,privacy;
    private Spinner hartuire;
    private String s_hartuire, s_nume, s_mesaj, s_locatie, s_email, s_utilizator, s_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_harassment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nume = (EditText)findViewById(R.id.nume);
        locatie = (EditText)findViewById(R.id.location);
        email = (TextView)findViewById(R.id.email);
        mesaj = (TextView)findViewById(R.id.intamplare);
        utilizator =(RadioGroup)findViewById(R.id.tip_utilizator);
        privacy =(RadioGroup)findViewById(R.id.privacy);
        hartuire = (Spinner)findViewById(R.id.selectHartuire);

        addItemsOnSpinner();
    }

    public void OnReport(View view){

        if (nume.getText().toString().trim().equals("")) {

            nume.setError("Numele si prenumele sunt obligatorii!");
            //Toast.makeText(getApplicationContext(), "Numele si prenumele sunt obligatorii!", Toast.LENGTH_LONG).show();

        }else if(privacy.getCheckedRadioButtonId() == -1){

            Toast.makeText(getApplicationContext(), "Alegeti cum doriti sa publicati", Toast.LENGTH_LONG).show();

        }else if (utilizator.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getApplicationContext(), "Alegeti ce tip de utilizator sunteti", Toast.LENGTH_LONG).show();

        }else if(String.valueOf(hartuire.getSelectedItem()).equals("Alege tipul de hartuire *")) {

            Toast.makeText(getApplicationContext(), "Alege tipul de hartuire", Toast.LENGTH_LONG).show();

        }else {
            if (locatie.getText().toString().trim().equals("")) {

                //Locatie.setError("Introducerea locatiei e obligatorie!");
                Toast.makeText(getApplicationContext(), "Introducerea locatiei e obligatorie!", Toast.LENGTH_LONG).show();
            } else {
                //Introduc in DB
                try {
                    RadioButton radio = (RadioButton) utilizator.findViewById(utilizator.getCheckedRadioButtonId());
                    s_utilizator = (radio.getText().equals("Victima") ) ? "Victima" : "Martor";
                    radio = (RadioButton) privacy.findViewById(privacy.getCheckedRadioButtonId());
                    s_privacy = (radio.getText().equals("Public") ) ? "Public" : "Privat";
                    s_hartuire = String.valueOf(hartuire.getSelectedItem());
                    s_nume = nume.getText().toString().trim();
                    s_mesaj = mesaj.getText().toString();
                    s_locatie = locatie.getText().toString().trim();
                    s_email = email.getText().toString();
                    String method = "register";
                    BackgroundDatabaseTask backgroundTask = new BackgroundDatabaseTask(this);
                    backgroundTask.execute(method, s_nume, s_hartuire, s_mesaj, s_locatie, s_email, s_utilizator, s_privacy);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
                }


                //Toast.makeText(getApplicationContext(), "Felicitari", Toast.LENGTH_LONG).show();


                Intent launchActivity1 = new Intent(ReportHarassmentActivity.this, MainActivity.class);
                startActivity(launchActivity1);

            }
        }

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
        getMenuInflater().inflate(R.menu.report_harassment, menu);
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


    public void addItemsOnSpinner() {
        spinner1 = (Spinner) findViewById(R.id.selectHartuire);
    }

}
