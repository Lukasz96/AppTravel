package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.ParaRowPodobienstwo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

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
        PodrozUzytkownik newRow = new PodrozUzytkownik(days, travelType, transport, weather, gender, age, 1);
        List<ParaRowPodobienstwo> result = cosineSimilarity.getTopNSimilarPacksForGivenTravelData(newRow, 10);
        for (ParaRowPodobienstwo pair : result) {
            System.out.println("Similarity: " + pair.getSimilarity() + " -> " + pair.getPodrozUzytkownik().toString());
        }
    }
}