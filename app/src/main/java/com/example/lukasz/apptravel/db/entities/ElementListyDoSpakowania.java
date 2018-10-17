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
    private int ilosc;
    private int idKategorii;
    private String uwaga;

    public ElementListyDoSpakowania(int id, int listaDoSpakowaniaId, @NonNull String nazwa, boolean czySpakowane, int ilosc, int idKategorii, String uwaga) {
        this.id = id;
        this.listaDoSpakowaniaId = listaDoSpakowaniaId;
        this.nazwa = nazwa;
        this.czySpakowane = czySpakowane;
        this.ilosc = ilosc;
        this.idKategorii = idKategorii;
        this.uwaga = uwaga;
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
}
