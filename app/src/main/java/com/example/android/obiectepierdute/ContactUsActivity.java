package com.example.android.obiectepierdute;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ContactUsActivity extends AppCompatActivity {

    FloatingActionButton sendMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        sendMailButton = (FloatingActionButton) findViewById(R.id.sendMail);

        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "mihaela.mangu@asociatia-anais.ro" });
                startActivity(emailIntent);
            }
        });
    }

}
