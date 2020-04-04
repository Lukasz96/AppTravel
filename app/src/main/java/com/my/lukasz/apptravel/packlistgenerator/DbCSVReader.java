package com.my.lukasz.apptravel.packlistgenerator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbCSVReader {

    public List<DbRow> getDataFromCsv() throws IOException {
        InputStreamReader is;
        ClassLoader object = getClass().getClassLoader();
        is = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("dane_do_kodu.csv"));

        List<DbRow> result = new ArrayList<>();

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

            result.add(new DbRow(numberOfDays, travelType, transportTypeId, weatherTypeId, gender, age));
        }
        return result;
    }
}


