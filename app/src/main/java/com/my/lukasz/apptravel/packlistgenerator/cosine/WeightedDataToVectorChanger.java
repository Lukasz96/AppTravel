package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.google.android.gms.common.internal.Preconditions;
import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;

public class WeightedDataToVectorChanger implements ChangerToVector {

    private static final int arraySize = 85;

    @Override
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
        if (days == 1) {
            result[0] = 1;
            result[1] = 1;
            result[2] = 1;
        }
        else if (days > 1 && days <= 3) {
            result[3] = 1;
            result[4] = 1;
            result[5] = 1;
        }
        else if (days > 3 && days <= 6) {
            result[6] = 1;
            result[7] = 1;
            result[8] = 1;
        }
        else if (days > 6 && days <= 10) {
            result[9] = 1;
            result[10] = 1;
            result[11] = 1;
        }
        else if (days > 10) {
            result[12] = 1;
            result[13] = 1;
            result[14] = 1;
        }
    }

    private void castGenderToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        String gender = rowFromDbQuery.getGender();
        Preconditions.checkArgument(gender != null && !gender.isEmpty());
        if (gender.equals("Kobieta")) {
            result[15] = 1;
            result[16] = 1;
            result[17] = 1;
            result[18] = 1;
            result[19] = 1;
            result[20] = 1;
        }
        else {
            result[21] = 1;
            result[22] = 1;
            result[23] = 1;
            result[24] = 1;
            result[25] = 1;
            result[26] = 1;
        }
    }

    private void castTransportToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int transportType = rowFromDbQuery.getTransportTypeId();
        Preconditions.checkArgument(transportType > 0 && transportType < 7);
        if (transportType == 1) {
            result[27] = 1;
            result[28] = 1;
        }
        else if (transportType == 2) {
            result[29] = 1;
            result[30] = 1;
        }
        else if (transportType == 3) {
            result[31] = 1;
            result[32] = 1;
        }
        else if (transportType == 4) {
            result[33] = 1;
            result[34] = 1;
        }
        else if (transportType == 5) {
            result[35] = 1;
            result[36] = 1;
        }
        else if (transportType == 6) {
            result[37] = 1;
            result[38] = 1;
        }
    }

    private void castTravelTypeToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int travelType = rowFromDbQuery.getTravelTypeId();
        Preconditions.checkArgument(travelType > 0 && travelType < 5);
        if (travelType == 1) {
            result[39] = 1;
            result[40] = 1;
            result[41] = 1;
            result[42] = 1;
            result[43] = 1;
        }
        else if (travelType == 2) {
            result[44] = 1;
            result[45] = 1;
            result[46] = 1;
            result[47] = 1;
            result[48] = 1;
        }
        else if (travelType == 3) {
            result[49] = 1;
            result[50] = 1;
            result[51] = 1;
            result[52] = 1;
            result[53] = 1;
        }
        else if (travelType == 4) {
            result[54] = 1;
            result[55] = 1;
            result[56] = 1;
            result[57] = 1;
            result[58] = 1;
        }
    }

    private void castWeatherToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int weatherType = rowFromDbQuery.getWeatherTypeId();
        Preconditions.checkArgument(weatherType > 0 && weatherType < 6);
        if (weatherType == 1) {
            result[59] = 1;
            result[60] = 1;
            result[61] = 1;
            result[62] = 1;
        }
        else if (weatherType == 2) {
            result[63] = 1;
            result[64] = 1;
            result[65] = 1;
            result[66] = 1;
        }
        else if (weatherType == 3) {
            result[67] = 1;
            result[68] = 1;
            result[69] = 1;
            result[70] = 1;
        }
        else if (weatherType == 4) {
            result[71] = 1;
            result[72] = 1;
            result[73] = 1;
            result[74] = 1;
        }
        else if (weatherType == 5) {
            result[75] = 1;
            result[76] = 1;
            result[77] = 1;
            result[78] = 1;
        }
    }

    private void castAgeToVector(double[] result, PodrozUzytkownik rowFromDbQuery) {
        int age = rowFromDbQuery.getAge();
        Preconditions.checkArgument(age > 0 && age < 120);
        if (age > 0 && age <= 10) result[79] = 1;
        else if (age > 10 && age <= 17) result[80] = 1;
        else if (age > 17 && age <= 35) result[81] = 1;
        else if (age > 35 && age <= 55) result[82] = 1;
        else if (age > 55 && age <= 69) result[83] = 1;
        else if (age > 69 && age <= 119) result[84] = 1;
    }
}
