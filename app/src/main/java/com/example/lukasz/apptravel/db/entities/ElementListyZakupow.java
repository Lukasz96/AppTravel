package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = { @ForeignKey(entity=ListaDoZakupu.class,
        parentColumns = "id",
        childColumns = "listaDoZakupuId",
        onDelete = CASCADE),
        @ForeignKey(entity = Kategoria.class,
        parentColumns = "id",
        childColumns = "idKategorii",
        onDelete = CASCADE)})
public class ElementListyZakupow {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int listaDoZakupuId;
    @NonNull
    private String nazwa;
    @NonNull
    private int ilosc;
    private int idKategorii;
    private String uwaga;
    @NonNull
    private boolean czyKupione;
    private double cena;

    public ElementListyZakupow(int id, int listaDoZakupuId, @NonNull String nazwa, @NonNull int ilosc,
                               int idKategorii, String uwaga, @NonNull boolean czyKupione, double cena) {
        this.id = id;
        this.listaDoZakupuId = listaDoZakupuId;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.idKategorii = idKategorii;
        this.uwaga = uwaga;
        this.czyKupione = czyKupione;
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListaDoZakupuId() {
        return listaDoZakupuId;
    }

    public void setListaDoZakupuId(int listaDoZakupuId) {
        this.listaDoZakupuId = listaDoZakupuId;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }

    @NonNull
    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(@NonNull int ilosc) {
        this.ilosc = ilosc;
    }

    public int getIdKategorii() {
        return idKategorii;
    }

    public void setIdKategorii(int idKategorii) {
        this.idKategorii = idKategorii;
    }

    public String getUwaga() {
        return uwaga;
    }

    public void setUwaga(String uwaga) {
        this.uwaga = uwaga;
    }

    @NonNull
    public boolean isCzyKupione() {
        return czyKupione;
    }

    public void setCzyKupione(@NonNull boolean czyKupione) {
        this.czyKupione = czyKupione;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
}
