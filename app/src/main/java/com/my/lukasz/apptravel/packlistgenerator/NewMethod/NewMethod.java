package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import android.content.Context;

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

public class NewMethod {

    private Context context;
    private PodrozUzytkownik newUserData;
    private final int nNearestNeighbours;
    private List<RzeczDoSpakowania> allThingsFromNNeighbours;

    public NewMethod(int nNearestNeighbours, Context context, PodrozUzytkownik newUserData) {
        this.nNearestNeighbours = nNearestNeighbours;
        this.context = context;
        this.newUserData = newUserData;
        allThingsFromNNeighbours = new ArrayList<>();
    }

    public List<RzeczDoSpakowania> getPackListRecommendation() throws IOException {
        CosineSimilarity similarity = new CosineSimilarity(context);
        List<ParaRowPodobienstwo> similarUsers =
                similarity.getTopNSimilarPacksForGivenTravelDataNewMethod(newUserData, nNearestNeighbours);

        int[] travelId = new int[nNearestNeighbours];
        for (int i = 0; i < similarUsers.size(); i++) {
            travelId[i] = similarUsers.get(i).getPodrozUzytkownik().getTravelId();
        }
        setThingsToListByTravelId(travelId);
        KNNPackListJoiner joiner = new KNNPackListJoiner(allThingsFromNNeighbours, nNearestNeighbours, similarUsers, context);
        List<RzeczDoSpakowania> joinedList =  joiner.getJoinedList();
        /////////// TODO jak wyliczać te ilości rzeczy ?
        ThingsQuantityCalculator calculator = new ThingsQuantityCalculator(joinedList, numberOfDays);
        return calculator.getListWithUpdatedQuantities();
    }

    private void setThingsToListByTravelId(int[] travelId) throws IOException {
        Map<Integer, List<RzeczDoSpakowania>> allThingsFromDb = ListItemsFromDb.getInstance(context).getPackLists();
        for (int i = 0; i < travelId.length; i++) {
            allThingsFromNNeighbours.addAll(allThingsFromDb.get(travelId[i]));
        }
    }
}
