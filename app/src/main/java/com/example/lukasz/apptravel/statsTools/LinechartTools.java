package com.example.lukasz.apptravel.statsTools;

import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LinechartTools {

    public LineData getLineData(List<ElementListyDoSpakowania> elementListyDoSpakowaniaList){
        List<Entry> entries = new ArrayList<Entry>();
        for (ElementListyDoSpakowania data : elementListyDoSpakowaniaList) {

            if(data.getCena()!=0) entries.add(new Entry(1, (float)data.getCena()));

        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataSet);

        return lineData;
    }
}
