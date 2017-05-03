package com.example.android.obiectepierdute;

/**
 * Created by Mircel on 10.04.2017.
 */

public class Report {

    private String nume, tip_obiect, obiect, descriere, locatie, email, nr_tel, latitude, longitude;

    public Report(String nume, String tip_obiect, String obiect, String descriere, String locatie, String email, String nr_tel, String latitude, String longitude) {
        this.setNume(nume);
        this.setTip(tip_obiect);
        this.setObiect(obiect);
        this.setDescriere(descriere);
        this.setLocatie(locatie);
        this.setEmail(email);
        this.setNrTel(nr_tel);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTip() {
        return tip_obiect;
    }

    public void setTip(String tip_obiect) {
        this.tip_obiect = tip_obiect;
    }

    public String getObiect() {
        return obiect;
    }

    public void setObiect(String obiect) {
        this.obiect = obiect;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
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

    public String getNrTel() {
        return nr_tel;
    }

    public void setNrTel(String nr_tel) {
        this.nr_tel = nr_tel;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
