package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import android.content.Context;

import com.my.lukasz.apptravel.packlistgenerator.ListItemsFromDb;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.TravelsUsersFromDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.my.lukasz.apptravel.packlistgenerator.NewMethod.TravelIdToCut.TRAVEL_ID_TO_CUT;

public class KParameterCounter {

    public double getMeasureForGivenKNeighbours(int kNeighbours, Context context) throws IOException {
        double sumOfMeasures = 0;
        for (Integer travelId : TRAVEL_ID_TO_CUT) {
            List<RzeczDoSpakowania> expectedResult = ListItemsFromDb.getInstance(context).getPackLists().get(travelId);
            PodrozUzytkownik userData = TravelsUsersFromDB.getInstance(context).getPackLists().get(travelId);
            NewMethod newMethod = new NewMethod(kNeighbours, context, userData);
            List<RzeczDoSpakowania> resultFromMethod = newMethod.getPackListRecommendation();
            sumOfMeasures += getMeasureComparingGivenAndExpected(expectedResult, resultFromMethod);
        }
        double averageMeasure = sumOfMeasures / TRAVEL_ID_TO_CUT.size();
        return averageMeasure;
    }

    private double getMeasureComparingGivenAndExpected(List<RzeczDoSpakowania> expectedPacklist, List<RzeczDoSpakowania> packlistFromMethod) {
        double result = 0;
        List<String> thingNamesInNewList = new ArrayList<>();
        for (RzeczDoSpakowania thingInNewList : packlistFromMethod) {
            thingNamesInNewList.add(thingInNewList.getNazwa());
        }
        for (RzeczDoSpakowania expectedThing : expectedPacklist) {
            if (!thingNamesInNewList.contains(expectedThing.getNazwa())) {
                result += 1;
                result += expectedThing.getIlosc() * 0.2;
            } else {
                result += (Math.abs(expectedThing.getIlosc() - findInList(packlistFromMethod, expectedThing.getNazwa()).getIlosc()) * 0.2);
            }
        }
        return result;
    }

    private RzeczDoSpakowania findInList(List<RzeczDoSpakowania> packlistFromMethod, String thingName) {
        for (RzeczDoSpakowania rzeczDoSpakowania : packlistFromMethod) {
            if (rzeczDoSpakowania.getNazwa().equals(thingName)) return rzeczDoSpakowania;
        }
        return null;
    }

}
