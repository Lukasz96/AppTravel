package com.my.lukasz.apptravel.packlistgenerator.decisionTree;

import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;
import com.my.lukasz.apptravel.packlistgenerator.collaborativeFiltering.CollaborativeFiltering;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class DecisionTreeTest {

    @Test
    public void justPrint() throws IOException {
        int days = 6;
        int travelType = 1;
        int transport = 2;
        int weather = 3;
        String gender = "Mężczyzna";
        int age = 26;
        DecisionTree decisionTree =
                new DecisionTree(getInstrumentation().getTargetContext(), new PodrozUzytkownik(days, travelType,
                        transport, weather, gender, age, 1));
        List<RzeczDoSpakowania> result = decisionTree.getPackListRecommendation();
    }

}