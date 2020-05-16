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

import static com.my.lukasz.apptravel.packlistgenerator.NewMethod.TravelIdToCut.TRAVEL_ID_TO_CUT;

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
        Map<RzeczDoSpakowania, Integer> thingsWithDays = joiner.getDaysOfTravelForEachThing();
        ThingsQuantityCalculator calculator = new ThingsQuantityCalculator(joinedList, newUserData.getNumberOfDays(), thingsWithDays);
        return calculator.getListWithUpdatedQuantities();
    }

    private void setThingsToListByTravelId(int[] travelId) throws IOException {
         Map<Integer, List<RzeczDoSpakowania>> allThingsFromDb = ListItemsFromDb.getInstance(context).getPackLists();
        // TODO dla testow można odkomentowac
//        Map<Integer, List<RzeczDoSpakowania>> allThingsFromDb = ListItemsFromDb.getInstance(context).getPackListsWithoutGivenTravels(
//                TRAVEL_ID_TO_CUT
//        );
        for (int i = 0; i < travelId.length; i++) {
            allThingsFromNNeighbours.addAll(allThingsFromDb.get(travelId[i]));
        }
    }
}
