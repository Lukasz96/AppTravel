package com.my.lukasz.apptravel.packlistgenerator.cosine;


import com.google.android.gms.common.internal.Preconditions;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;

public class DataToVectorChanger {

    private static final int arraySize = 28;

    public double[] castToVector(PodrozUzytkownik rowFromDbQuery) {
        double[] result = new double[arraySize];
        castDaysToVector(result, rowFromDbQuery);
        castGenderToVector(result, rowFromDbQuery);
        castTransportToVector(result, rowFromDbQuery);
        castTravelTypeToVector(result, rowFromDbQuery);
        castWeatherToVector(result, rowFromDbQuery);
        castAgeToVector(result, rowFromDbQuery);
        return result;
    }

    private void castDaysToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int days = rowFromDbQuery.getNumberOfDays();
        Preconditions.checkArgument(days > 0);
        if (days == 1) result[0] = 1;
        else if (days > 1 && days <= 3) result[1] = 1;
        else if (days > 3 && days <= 6) result[2] = 1;
        else if (days > 6 && days <= 10) result[3] = 1;
        else if (days > 10) result[4] = 1;
    }

    private void castGenderToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        String gender = rowFromDbQuery.getGender();
        Preconditions.checkArgument(gender != null && !gender.isEmpty());
        if (gender.equals("Kobieta")) result[5] = 1;
        else result[6] = 1;
    }

    private void castTransportToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int transportType = rowFromDbQuery.getTransportTypeId();
        Preconditions.checkArgument(transportType > 0 && transportType < 7);
        if (transportType == 1) result[7] = 1;
        else if (transportType == 2) result[8] = 1;
        else if (transportType == 3) result[9] = 1;
        else if (transportType == 4) result[10] = 1;
        else if (transportType == 5) result[11] = 1;
        else if (transportType == 6) result[12] = 1;
    }

    private void castTravelTypeToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int travelType = rowFromDbQuery.getTravelTypeId();
        Preconditions.checkArgument(travelType > 0 && travelType < 5);
        if (travelType == 1) result[13] = 1;
        else if (travelType == 2) result[14] = 1;
        else if (travelType == 3) result[15] = 1;
        else if (travelType == 4) result[16] = 1;
    }

    private void castWeatherToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int weatherType = rowFromDbQuery.getWeatherTypeId();
        Preconditions.checkArgument(weatherType > 0 && weatherType < 6);
        if (weatherType == 1) result[17] = 1;
        else if (weatherType == 2) result[18] = 1;
        else if (weatherType == 3) result[19] = 1;
        else if (weatherType == 4) result[20] = 1;
        else if (weatherType == 5) result[21] = 1;
    }

    private void castAgeToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int age = rowFromDbQuery.getAge();
        Preconditions.checkArgument(age > 0 && age < 120);
        if (age > 0 && age <= 10) result[22] = 1;
        else if (age > 10 && age <= 17) result[23] = 1;
        else if (age > 17 && age <= 35) result[24] = 1;
        else if (age > 35 && age <= 55) result[25] = 1;
        else if (age > 55 && age <= 69) result[26] = 1;
        else if (age > 69 && age <= 119) result[27] = 1;
    }

}
