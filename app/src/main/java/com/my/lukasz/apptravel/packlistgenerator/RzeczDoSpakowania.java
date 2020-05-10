package com.my.lukasz.apptravel.packlistgenerator;

public class RzeczDoSpakowania {

    private String nazwa;
    private int ilosc;
    private String kategoria;

    public RzeczDoSpakowania(String nazwa, int ilosc, String kategoria) {
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.kategoria = kategoria;
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

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return "RzeczDoSpakowania{" +
                "nazwa='" + nazwa + '\'' +
                ", ilosc=" + ilosc +
                ", kategoria='" + kategoria + '\'' +
                '}';
    }
}
