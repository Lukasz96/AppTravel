package com.my.lukasz.apptravel.packlistgenerator.collaborativeFiltering;

import com.my.lukasz.apptravel.packlistgenerator.KNNPackListJoiner;
import com.my.lukasz.apptravel.packlistgenerator.ListItemsFromDb;
import com.my.lukasz.apptravel.packlistgenerator.ParaRowPodobienstwo;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.cosine.CosineSimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollaborativeFiltering {

    private final int nNearestNeighbours;
    private PodrozUzytkownik newUserData;
    private List<RzeczDoSpakowania> allThingsFromNNeighbours;

    public CollaborativeFiltering(int nNearestNeighbours, PodrozUzytkownik newUserData) {
        this.nNearestNeighbours = nNearestNeighbours;
        this.newUserData = newUserData;
        allThingsFromNNeighbours = new ArrayList<>();
    }

    public List<RzeczDoSpakowania> getPackListRecommendation() throws IOException {
        CosineSimilarity similarity = new CosineSimilarity();
        List<ParaRowPodobienstwo> similarUsers =
                similarity.getTopNSimilarPacksForGivenTravelData(newUserData, nNearestNeighbours);
        int[] travelId = new int[nNearestNeighbours];
        for (int i = 0; i < similarUsers.size(); i++) {
            travelId[i] = similarUsers.get(i).getPodrozUzytkownik().getTravelId();
        }
        setThingsToListByTravelId(travelId);
        KNNPackListJoiner joiner = new KNNPackListJoiner(allThingsFromNNeighbours, nNearestNeighbours, similarUsers);
        return joiner.getJoinedList();
    }

    private void setThingsToListByTravelId(int[] travelId) throws IOException {
        Map<Integer, List<RzeczDoSpakowania>> allThingsFromDb = ListItemsFromDb.getInstance().getPackLists();
        for (int i = 0; i < travelId.length; i++) {
            allThingsFromNNeighbours.addAll(allThingsFromDb.get(travelId[i]));
        }
    }
}
