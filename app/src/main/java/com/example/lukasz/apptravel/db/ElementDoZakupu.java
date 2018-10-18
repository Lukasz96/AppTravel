package com.example.lukasz.apptravel.db;

public class ElementDoZakupu {

    private String nazwa;
    private int ilosc;
    private double cena;
    private boolean czyKupione;
    private String nazwaKategorii;
    private  String uwaga;

    public ElementDoZakupu(String nazwa, int ilosc, double cena, boolean czyKupione, String nazwaKategorii, String uwaga) {
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.cena = cena;
        this.czyKupione = czyKupione;
        this.nazwaKategorii = nazwaKategorii;
        this.uwaga = uwaga;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public boolean isCzyKupione() {
        return czyKupione;
    }

    public void setCzyKupione(boolean czyKupione) {
        this.czyKupione = czyKupione;
    }

    public String getNazwaKategorii() {
        return nazwaKategorii;
    }

    public void setKategoria(String nazwaKategorii) {
        this.nazwaKategorii = nazwaKategorii;
    }

    public String getUwaga() {
        return uwaga;
    }

    public void setUwaga(String uwaga) {
        this.uwaga = uwaga;
    }
}
