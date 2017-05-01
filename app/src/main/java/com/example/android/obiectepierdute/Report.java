package com.example.android.obiectepierdute;

/**
 * Created by Mircel on 10.04.2017.
 */

public class Report {

    private String nume,hartuire,mesaj,locatie,email,utilizator,privacy;

    public Report(String nume,String hartuire,String mesaj,String locatie,String email,String utilizator,String privacy) {
        this.setNume(nume);
        this.setHartuire(hartuire);
        this.setMesaj(mesaj);
        this.setLocatie(locatie);
        this.setEmail(email);
        this.setUtilizator(utilizator);
        this.setPrivacy(privacy);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getHartuire() {
        return hartuire;
    }

    public void setHartuire(String hartuire) {
        this.hartuire = hartuire;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
