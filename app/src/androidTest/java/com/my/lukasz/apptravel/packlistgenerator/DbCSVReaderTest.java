package com.my.lukasz.apptravel.packlistgenerator;

import org.junit.Test;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class DbCSVReaderTest {

    @Test
    public void test() throws IOException {
        DbCSVReader reader = new DbCSVReader(getInstrumentation().getTargetContext());
        int size = reader.getUserAndTravelDataAsMap().size();
        assertEquals(107, size);
    }

}