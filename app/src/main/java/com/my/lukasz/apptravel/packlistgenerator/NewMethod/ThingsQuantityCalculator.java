package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThingsQuantityCalculator {

    private List<RzeczDoSpakowania> listToCalculate;
    private int numberOfDays;
    private Map<RzeczDoSpakowania, Integer> thingsWithDays;

    public ThingsQuantityCalculator(List<RzeczDoSpakowania> listToCalculate, int numberOfDays, Map<RzeczDoSpakowania, Integer> thingsWithDays) {
        this.listToCalculate = listToCalculate;
        this.numberOfDays = numberOfDays;
        this.thingsWithDays = thingsWithDays;
    }

    public List<RzeczDoSpakowania> getListWithUpdatedQuantities() {
        List<RzeczDoSpakowania> result = new ArrayList<>();
        for (RzeczDoSpakowania rzeczFromList : listToCalculate) {
            RzeczDoSpakowania thingToAdd = rzeczFromList;
            int daysFromThing = thingsWithDays.get(rzeczFromList);
            if (daysFromThing / rzeczFromList.getIlosc() >= 0.7 && daysFromThing / rzeczFromList.getIlosc() <= 1.25) {
                thingToAdd.setIlosc(numberOfDays);
                result.add(thingToAdd);
            } else {
                result.add(thingToAdd);
            }
        }
        return result;
    }
}
