package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;

public interface ChangerToVector {

    double[] castToVector(PodrozUzytkownik rowFromDbQuery);
}
