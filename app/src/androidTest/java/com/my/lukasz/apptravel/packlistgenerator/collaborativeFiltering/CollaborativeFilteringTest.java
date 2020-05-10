package com.my.lukasz.apptravel.packlistgenerator.collaborativeFiltering;

import com.my.lukasz.apptravel.packlistgenerator.PodrozUzytkownik;
import com.my.lukasz.apptravel.packlistgenerator.RzeczDoSpakowania;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class CollaborativeFilteringTest {

    @Test
    public void justPrint() throws IOException {
        int days = 10;
        int travelType = 1;
        int transport = 2;
        int weather = 5;
        String gender = "Mężczyzna";
        int age = 26;
        CollaborativeFiltering collaborativeFiltering =
                new CollaborativeFiltering(5, new PodrozUzytkownik(days, travelType,
                        transport, weather, gender, age, 1), getInstrumentation().getTargetContext());
        List<RzeczDoSpakowania> result = collaborativeFiltering.getPackListRecommendation();
        for (RzeczDoSpakowania rzeczDoSpakowania : result) {
            System.out.println(rzeczDoSpakowania.toString());
        }
    }

}