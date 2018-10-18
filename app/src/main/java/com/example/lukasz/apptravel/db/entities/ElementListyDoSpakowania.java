package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = { @ForeignKey(entity=ListaDoSpakowania.class,
        parentColumns = "id",
        childColumns = "listaDoSpakowaniaId",
        onDelete = CASCADE),
        @ForeignKey(entity = Kategoria.class,
        parentColumns = "id",
        childColumns = "idKategorii",
        onDelete = CASCADE)})
public class ElementListyDoSpakowania {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int listaDoSpakowaniaId;
    @NonNull
    private String nazwa;
    private boolean czySpakowane;
    @NonNull
    private boolean czyPrzekazanoDoZakupu;
    private int ilosc;
    private double cena;
    private boolean czyKupione;
    private int idKategorii;
    private String uwaga;


    public ElementListyDoSpakowania(int id, int listaDoSpakowaniaId, @NonNull String nazwa, boolean czySpakowane,
                                    @NonNull boolean czyPrzekazanoDoZakupu, int ilosc, double cena, boolean czyKupione,
                                    int idKategorii, String uwaga) {
        this.id = id;
        this.listaDoSpakowaniaId = listaDoSpakowaniaId;
        this.nazwa = nazwa;
        this.czySpakowane = czySpakowane;
        this.czyPrzekazanoDoZakupu = czyPrzekazanoDoZakupu;
        this.ilosc = ilosc;
        this.cena = cena;
        this.czyKupione = czyKupione;
        this.idKategorii = idKategorii;
        this.uwaga = uwaga;
    }

    public boolean isCzyPrzekazanoDoZakupu() {
        return czyPrzekazanoDoZakupu;
    }

    public void setCzyPrzekazanoDoZakupu(boolean czyPrzekazanoDoZakupu) {
        this.czyPrzekazanoDoZakupu = czyPrzekazanoDoZakupu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListaDoSpakowaniaId() {
        return listaDoSpakowaniaId;
    }

    public void setListaDoSpakowaniaId(int listaDoSpakowaniaId) {
        this.listaDoSpakowaniaId = listaDoSpakowaniaId;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean isCzySpakowane() {
        return czySpakowane;
    }

    public void setCzySpakowane(boolean czySpakowane) {
        this.czySpakowane = czySpakowane;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
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
}
