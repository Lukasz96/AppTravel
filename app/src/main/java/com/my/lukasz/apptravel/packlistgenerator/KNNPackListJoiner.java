package com.my.lukasz.apptravel.packlistgenerator;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNPackListJoiner {

    private Map<String, Integer> thingsAndAppearancesNumber;
    private List<RzeczDoSpakowania> allThingsFromNeighbours;
    private int nNearestNeighbours;
    private List<ParaRowPodobienstwo> similarUsers;
    private Context context;

    public KNNPackListJoiner(List<RzeczDoSpakowania> allThingsFromNeighbours, int nNearestNeighbours, List<ParaRowPodobienstwo> similarUsers, Context context) {
        this.allThingsFromNeighbours = allThingsFromNeighbours;
        this.nNearestNeighbours = nNearestNeighbours;
        this.similarUsers = similarUsers;
        thingsAndAppearancesNumber = new HashMap<>();
        this.context = context;
    }

    public List<RzeczDoSpakowania> getJoinedList() throws IOException {
        setThingsWithAppearancesToMap();
        List<RzeczDoSpakowania> result = new ArrayList<>();
        int minimumNumberOfAppearances = nNearestNeighbours / 2 + 1;
        for (String thing : thingsAndAppearancesNumber.keySet()) {
            if (thingsAndAppearancesNumber.get(thing) >= minimumNumberOfAppearances) {
                result.add(getThingWithCoorectNumberAndCategory(thing));
            }
        }
        return  result;
    }

    private void setThingsWithAppearancesToMap() {
        for (RzeczDoSpakowania rzeczDoSpakowania : allThingsFromNeighbours) {
            String key = rzeczDoSpakowania.getNazwa();
            if (thingsAndAppearancesNumber.containsKey(key)) {
                thingsAndAppearancesNumber.put(key, thingsAndAppearancesNumber.get(key) + 1);
            } else {
                thingsAndAppearancesNumber.put(key, 1);
            }
        }
    }

    private RzeczDoSpakowania getThingWithCoorectNumberAndCategory(String thingName) throws IOException {
        for (ParaRowPodobienstwo user: similarUsers) {
            int travelId = user.getPodrozUzytkownik().getTravelId();
            List<RzeczDoSpakowania> listaRzeczyUsera = ListItemsFromDb.getInstance(context).getPackLists().get(travelId);
            for (RzeczDoSpakowania rzecz : listaRzeczyUsera) {
                if (rzecz.getNazwa().equals(thingName)) {
                    return rzecz;
                }
            }
        }
        return null;
    }
}
