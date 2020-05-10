package com.my.lukasz.apptravel.packlistgenerator;

public class ParaRowPodobienstwo {

    private PodrozUzytkownik podrozUzytkownik;
    private double similarity;

    public ParaRowPodobienstwo(PodrozUzytkownik podrozUzytkownik, double similarity) {
        this.podrozUzytkownik = podrozUzytkownik;
        this.similarity = similarity;
    }

    public PodrozUzytkownik getPodrozUzytkownik() {
        return podrozUzytkownik;
    }

    public void setPodrozUzytkownik(PodrozUzytkownik podrozUzytkownik) {
        this.podrozUzytkownik = podrozUzytkownik;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "ParaRowPodobienstwo{" +
                "podrozUzytkownik=" + podrozUzytkownik +
                ", similarity=" + similarity +
                '}';
    }
}
