package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;

import java.util.List;

public class ThingsQuantityCalculator {

    private List<RzeczDoSpakowania> listToCalculate;
    private int numberOfDays;

    public ThingsQuantityCalculator(List<RzeczDoSpakowania> listToCalculate, int numberOfDays) {
        this.listToCalculate = listToCalculate;
        this.numberOfDays = numberOfDays;
    }

    public List<RzeczDoSpakowania> getListWithUpdatedQuantities() {

    }
}
