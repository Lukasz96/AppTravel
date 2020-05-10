package com.my.lukasz.apptravel.packlistgenerator;

import android.content.Context;

import java.io.IOException;
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
}
