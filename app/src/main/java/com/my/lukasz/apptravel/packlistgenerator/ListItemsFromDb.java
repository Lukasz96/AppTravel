package com.my.lukasz.apptravel.packlistgenerator;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListItemsFromDb {

    private static ListItemsFromDb INSTANCE;
    private Map<Integer, List<RzeczDoSpakowania>> packLists;

    private ListItemsFromDb(Context context) throws IOException {
        DbCSVReader reader = new DbCSVReader(context);
        packLists = reader.getPackListsAsMap();
    }

    public static ListItemsFromDb getInstance(Context context) throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ListItemsFromDb(context);
        }
        return INSTANCE;
    }

    public Map<Integer, List<RzeczDoSpakowania>> getPackLists() {
        return packLists;
    }
}
