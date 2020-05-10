package com.my.lukasz.apptravel.packlistgenerator;

import java.io.IOException;
import java.util.Map;

public class TravelsUsersFromDB {

    private static TravelsUsersFromDB INSTANCE;
    private Map<Integer, PodrozUzytkownik> travels;

    private TravelsUsersFromDB() throws IOException {
        DbCSVReader reader = new DbCSVReader();
        travels = reader.getUserAndTravelDataAsMap();
    }

    public static TravelsUsersFromDB getInstance() throws IOException {
        if (INSTANCE == null) {
            return new TravelsUsersFromDB();
        }
        return INSTANCE;
    }

    public Map<Integer, PodrozUzytkownik> getPackLists() {
        return travels;
    }
}
