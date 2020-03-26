package com.my.lukasz.apptravel.statsTools;

import android.content.Context;

import com.my.lukasz.apptravel.R;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class AxisMonthFormatter implements IAxisValueFormatter {

    private Context context;

    public AxisMonthFormatter(Context context){
        this.context=context;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        String output;
        switch ((int)value){
            case 0:{
                output=context.getResources().getString(R.string.january);
                break;
            }case 1:{
                output=context.getResources().getString(R.string.february);
                break;
            }
            case 2:{
                output=context.getResources().getString(R.string.march);
                break;
            }
            case 3:{
                output=context.getResources().getString(R.string.april);
                break;
            }
            case 4:{
                output=context.getResources().getString(R.string.may);
                break;
            }
            case 5:{
                output=context.getResources().getString(R.string.June);
                break;
            }
            case 6:{
                output=context.getResources().getString(R.string.July);
                break;
            }
            case 7:{
                output=context.getResources().getString(R.string.August);
                break;
            }
            case 8:{
                output=context.getResources().getString(R.string.September);
                break;
            }
            case 9:{
                output=context.getResources().getString(R.string.October);
                break;
            }
            case 10:{
                output=context.getResources().getString(R.string.November);
                break;
            }
            case 11:{
                output=context.getResources().getString(R.string.Decmber);
                break;
            }
            default:output="";

        }
        return output;


    }
}
