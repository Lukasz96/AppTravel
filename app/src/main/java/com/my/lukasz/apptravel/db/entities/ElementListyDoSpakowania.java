package com.my.lukasz.apptravel.db.entities;

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
    private long id;
    private long listaDoSpakowaniaId;
    @NonNull
    private String nazwa;
    private boolean czyDoSpakowania;
    private boolean czySpakowane;
    @NonNull
    private boolean czyPrzekazanoDoZakupu;
    private int iloscDoSpakowania;
    private int iloscDoZakupu;
    private double cena;
    private String waluta;
    private boolean czyKupione;
    private long idKategorii;


    public ElementListyDoSpakowania(long id, long listaDoSpakowaniaId, @NonNull String nazwa, boolean czyDoSpakowania,
                                    boolean czySpakowane,
                                    @NonNull boolean czyPrzekazanoDoZakupu, int iloscDoSpakowania, int iloscDoZakupu,
                                    double cena, String waluta, boolean czyKupione,
                                    long idKategorii) {
        this.id = id;
        this.listaDoSpakowaniaId = listaDoSpakowaniaId;
        this.nazwa = nazwa;
        this.czyDoSpakowania=czyDoSpakowania;
        this.czySpakowane = czySpakowane;
        this.czyPrzekazanoDoZakupu = czyPrzekazanoDoZakupu;
        this.iloscDoSpakowania = iloscDoSpakowania;
        this.iloscDoZakupu=iloscDoZakupu;
        this.cena = cena;
        this.waluta = waluta;
        this.czyKupione = czyKupione;
        this.idKategorii = idKategorii;
    }

    public boolean isCzyPrzekazanoDoZakupu() {
        return czyPrzekazanoDoZakupu;
    }

    public void setCzyPrzekazanoDoZakupu(boolean czyPrzekazanoDoZakupu) {
        this.czyPrzekazanoDoZakupu = czyPrzekazanoDoZakupu;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getListaDoSpakowaniaId() {
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

    public boolean isCzyDoSpakowania() {
        return czyDoSpakowania;
    }

    public void setCzyDoSpakowania(boolean czyDoSpakowania) {
        this.czyDoSpakowania = czyDoSpakowania;
    }

    public boolean isCzySpakowane() {
        return czySpakowane;
    }

    public void setCzySpakowane(boolean czySpakowane) {
        this.czySpakowane = czySpakowane;
    }

    public int getIloscDoSpakowania() {
        return iloscDoSpakowania;
    }

    public void setIloscDoSpakowania(int iloscDoSpakowania) {
        this.iloscDoSpakowania = iloscDoSpakowania;
    }

    public int getIloscDoZakupu() {
        return iloscDoZakupu;
    }

    public void setIloscDoZakupu(int iloscDoZakupu) {
        this.iloscDoZakupu = iloscDoZakupu;
    }

    public long getIdKategorii() {
        return idKategorii;
    }

    public void setIdKategorii(int idKategorii) {
        this.idKategorii = idKategorii;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public boolean isCzyKupione() {
        return czyKupione;
    }

    public void setCzyKupione(boolean czyKupione) {
        this.czyKupione = czyKupione;
    }
}
