package com.my.lukasz.apptravel.packlistgenerator;

import android.content.Context;

import com.my.lukasz.apptravel.db.entities.Podroz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TravelsUsersFromDB {

    private static TravelsUsersFromDB INSTANCE;
    private Map<Integer, PodrozUzytkownik> travels;

    private TravelsUsersFromDB(Context context) throws IOException {
        DbCSVReader reader = new DbCSVReader(context);
        travels = reader.getUserAndTravelDataAsMap();
    }

    public static TravelsUsersFromDB getInstance(Context context) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new TravelsUsersFromDB(context);
        }
        return INSTANCE;
    }

    public Map<Integer, PodrozUzytkownik> getPackLists() {
        return travels;
    }

    public Map<Integer, PodrozUzytkownik> getPackListsWithoutGivenTravels(List<Integer> travelIdsToCut) {
        Map<Integer, PodrozUzytkownik> result = new HashMap<>(travels);
        Iterator<Map.Entry<Integer, PodrozUzytkownik>> iterator = result.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, PodrozUzytkownik> entry = iterator.next();
            if (travelIdsToCut.contains(entry.getKey())) {
                iterator.remove();
            }
        }
        return result;
    }
}
