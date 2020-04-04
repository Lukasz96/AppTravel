package com.my.lukasz.apptravel.packlistgenerator;

public class ParaRowPodobienstwo {

    private DbRow dbRow;
    private double similarity;

    public ParaRowPodobienstwo(DbRow dbRow, double similarity) {
        this.dbRow = dbRow;
        this.similarity = similarity;
    }

    public DbRow getDbRow() {
        return dbRow;
    }

    public void setDbRow(DbRow dbRow) {
        this.dbRow = dbRow;
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
                "dbRow=" + dbRow +
                ", similarity=" + similarity +
                '}';
    }
}
