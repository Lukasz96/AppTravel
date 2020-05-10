package com.my.lukasz.apptravel.packlistgenerator;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbCSVReader {

    private Context context;

    public DbCSVReader(Context context) {
        this.context = context;
    }

    public Map<Integer, PodrozUzytkownik> getUserAndTravelDataAsMap() throws IOException {
        InputStreamReader is;
        is = new InputStreamReader(context.getAssets().open("dane_do_kodu.csv"));

        Map<Integer, PodrozUzytkownik> result = new HashMap<>();

        BufferedReader reader = new BufferedReader(is);
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] dbRow = line.split(",");

            int numberOfDays = Integer.parseInt(dbRow[0]);
            int travelType = Integer.parseInt(dbRow[1]);
            int transportTypeId = Integer.parseInt(dbRow[2]);
            int weatherTypeId = Integer.parseInt(dbRow[3]);
            String gender = dbRow[4];
            int age = Integer.parseInt(dbRow[5]);
            int podrozId = Integer.parseInt(dbRow[6]);

            result.put(podrozId, new PodrozUzytkownik(numberOfDays, travelType, transportTypeId, weatherTypeId, gender, age, podrozId));
        }
        return result;
    }

    public Map<Integer, List<RzeczDoSpakowania>> getPackListsAsMap() throws IOException {
        InputStreamReader is;
        is = new InputStreamReader(context.getAssets().open("rzeczy_spakowane_do_kodu.csv"));
        Map<Integer, List<RzeczDoSpakowania>> result = new HashMap<>();
        BufferedReader reader = new BufferedReader(is);
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] dbRow = line.split(",");

            String nazwa = dbRow[0];
            int ilosc = Integer.parseInt(dbRow[1]);
            String kategoria = dbRow[2];
            int podrozId = Integer.parseInt(dbRow[3]);

            if (result.containsKey(podrozId)) {
                List<RzeczDoSpakowania> rzeczyDotychczas = result.get(podrozId);
                rzeczyDotychczas.add(new RzeczDoSpakowania(nazwa, ilosc, kategoria));
                result.put(podrozId, rzeczyDotychczas);
            }
            else {
                result.put(podrozId, new ArrayList<>(Arrays.asList(new RzeczDoSpakowania(nazwa, ilosc, kategoria))));
            }
        }
        return result;
    }
}


