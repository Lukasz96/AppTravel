package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import java.util.ArrayList;
import java.util.List;

public class TravelIdToCut {

    public static final List<Integer> TRAVEL_ID_TO_CUT = fill();

    private static List<Integer> fill() {
        List<Integer> list  = new ArrayList<>();
        for (int i = 90; i < 100; i++) {
            list.add(i);
        }
        return list;
    }
}
