package com.my.lukasz.apptravel.packlistgenerator.NewMethod;

import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.collaborativeFiltering.CollaborativeFiltering;

import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class KParameterCounterTest {

    @Test
    public void justPrint() throws IOException {
        KParameterCounter kParameterCounter = new KParameterCounter();
        List<Double> results = new ArrayList<>();
        for (int i = 1; i < 50; i += 2) {
            System.out.println("-----------" + i);
          //  System.out.println(kParameterCounter.getMeasureForGivenKNeighbours(i, getInstrumentation().getTargetContext()));
            results.add(kParameterCounter.getMeasureForGivenKNeighbours(i, getInstrumentation().getTargetContext()));
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (double res : results) {
            System.out.println(df.format(res));
        }
    }
}