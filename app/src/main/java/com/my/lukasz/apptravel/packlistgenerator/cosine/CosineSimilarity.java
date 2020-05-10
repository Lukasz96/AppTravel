package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.my.lukasz.apptravel.packlistgenerator.DbCSVReader;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.ParaRowPodobienstwo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CosineSimilarity {

    public List<ParaRowPodobienstwo> getTopNSimilarPacksForGivenTravelData(PodrozUzytkownik newTravelData, int topN) throws IOException {
        DataToVectorChanger vectorChanger = new DataToVectorChanger();
        final double[] newTravelDataVector = vectorChanger.castToVector(newTravelData);
        Map<Integer, PodrozUzytkownik> podrozUzytkownikMap  = new DbCSVReader().getUserAndTravelDataAsMap();
        List<ParaRowPodobienstwo> rowAndSimilarityPairs = new ArrayList<>();
        for (PodrozUzytkownik podrozUzytkownik : podrozUzytkownikMap.values()) {
            double similarity = cosineSimilarity(newTravelDataVector, vectorChanger.castToVector(podrozUzytkownik));
            rowAndSimilarityPairs.add(new ParaRowPodobienstwo(podrozUzytkownik, similarity));
        }
        Collections.sort(rowAndSimilarityPairs, new Comparator<ParaRowPodobienstwo>() {
            @Override
            public int compare(ParaRowPodobienstwo dbRowDoublePair, ParaRowPodobienstwo t1) {
                return Double.compare(dbRowDoublePair.getSimilarity(), t1.getSimilarity());
            }
        });
        Collections.reverse(rowAndSimilarityPairs);
        return rowAndSimilarityPairs.subList(0, topN);
    }


    private double cosineSimilarity(double[] vectorA, double[] vectorB) throws IOException {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}