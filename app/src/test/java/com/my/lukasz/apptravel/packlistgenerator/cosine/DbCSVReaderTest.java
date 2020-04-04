package com.my.lukasz.apptravel.packlistgenerator.cosine;

import com.my.lukasz.apptravel.packlistgenerator.DbCSVReader;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DbCSVReaderTest {

    @Test
    public void test() throws IOException {
        DbCSVReader reader = new DbCSVReader();
        int size = reader.getDataFromCsv().size();
        assertEquals(107, size);
    }

}