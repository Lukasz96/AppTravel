package com.my.lukasz.apptravel.packlistgenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListItemsFromDb {

    private static ListItemsFromDb INSTANCE;
    private Map<Integer, List<RzeczDoSpakowania>> packLists;

    private ListItemsFromDb() throws IOException {
        DbCSVReader reader = new DbCSVReader();
        packLists = reader.getPackListsAsMap();
    }

    public static ListItemsFromDb getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ListItemsFromDb();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public Map<Integer, List<RzeczDoSpakowania>> getPackLists() {
        return packLists;
    }
}
