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

import org.w3c.dom.Text;

public class ReportHarassmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnSubmit;
    private EditText nume, locatie,obiect,nr_tel,mail;
    private TextView descriere;
    private RadioGroup tip_obj;
    private String s_nume, s_tip_obj, s_obiect , s_descriere, s_locatie ,s_mail ,s_nr_tel;

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
        tip_obj =(RadioGroup)findViewById(R.id.tip_obiect);
        obiect = (EditText)findViewById(R.id.numeObiect);
        descriere = (TextView)findViewById(R.id.descriereObiect);
        locatie = (EditText)findViewById(R.id.location);
        mail = (EditText)findViewById(R.id.email);
        nr_tel = (EditText)findViewById(R.id.nrTelefon);

        //addItemsOnSpinner();
    }

    public void OnReport(View view){

       if (nume.getText().toString().trim().equals("")) {

            nume.setError("Numele si prenumele sunt obligatorii!");
            //Toast.makeText(getApplicationContext(), "Numele si prenumele sunt obligatorii!", Toast.LENGTH_LONG).show();

        }else if (tip_obj.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getApplicationContext(), "Alegeti tipul de raportare!", Toast.LENGTH_LONG).show();

        }else if (obiect.getText().toString().trim().equals("")){

            Toast.makeText(getApplicationContext(), "Numiti obiectul gasit / pierdut!", Toast.LENGTH_LONG).show();

        }else if (mail.getText().toString().trim().equals("")){

           Toast.makeText(getApplicationContext(), "Adaugati mail-ul", Toast.LENGTH_LONG).show();

       }else if (nr_tel.getText().toString().trim().equals("")){

            Toast.makeText(getApplicationContext(), "Adaugati un numar de telefon!", Toast.LENGTH_LONG).show();

        }else {
            if (locatie.getText().toString().trim().equals("")) {

                Toast.makeText(getApplicationContext(), "Introducerea locatiei e obligatorie!", Toast.LENGTH_LONG).show();
            } else {
                //Introduc in DB
                try {
                    RadioButton radio = (RadioButton) tip_obj.findViewById(tip_obj.getCheckedRadioButtonId());
                    s_tip_obj = (radio.getText().equals("Obiect pierdut") ) ? "Obiect pierdut" : "Obiect gasit";
                    s_nume = nume.getText().toString().trim();
                    s_obiect = obiect.getText().toString().trim();
                    s_descriere = descriere.getText().toString();
                    s_locatie = locatie.getText().toString().trim();
                    s_mail = mail.getText().toString().trim();
                    s_nr_tel = nr_tel.getText().toString().trim();
                    String method = "register";
                    BackgroundDatabaseTask backgroundTask = new BackgroundDatabaseTask(this);
                    backgroundTask.execute(method, s_nume, s_tip_obj, s_obiect , s_descriere, s_locatie, s_mail, s_nr_tel);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
                    TextView textView = (TextView) findViewById(R.id.textTest);
                    textView.setText(e.getMessage());
                }

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
        } else if (id == R.id.nav_about_us) {
            Intent page = new Intent(this, HelpMainActivity.class);
            startActivity(page);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*public void addItemsOnSpinner() {
        spinner1 = (Spinner) findViewById(R.id.selectHartuire);
    }*/

}
