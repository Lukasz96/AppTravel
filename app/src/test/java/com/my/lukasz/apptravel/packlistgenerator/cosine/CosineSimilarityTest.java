package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.my.lukasz.apptravel.packlistgenerator.DbRow;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CosineSimilarityTest {

    private CosineSimilarity cosineSimilarity;

    @Before
    public void setUp() {
        cosineSimilarity = new CosineSimilarity();
    }

    @Test
    public void justPrint() throws IOException {
        int days = 3;
        int travelType = 4;
        int transport = 2;
        int weather = 5;
        String gender = "Kobieta";
        int age = 26;
        DbRow newRow = new DbRow(days, travelType, transport, weather, gender, age);
        cosineSimilarity.getTopNSimilarPacksForGivenTravelData(newRow, 10);
    }
}