package com.example.lukasz.apptravel.datevalidator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;

import com.example.lukasz.apptravel.R;

public class BudgetValidator {

    private void validateBudget(Editable s, TextInputLayout budgetLauout, Context context){
        if (TextUtils.isEmpty(s)) {
            budgetLauout.setError(context.getString(R.string.nobudgeterror));
            //checkIfEnableButton();
        }
        if (s.toString().length()>9){
            budgetLauout.setError(getString(R.string.maxninenumbers));
           // checkIfEnableButton();
        }
        else {
            int integerPlaces = s.toString().indexOf('.');
            int decimalPlaces = s.toString().length() - integerPlaces - 1;
            if(decimalPlaces>2){
                budgetLauout.setError(getString(R.string.decimalplaceserror));
              //  checkIfEnableButton();
            }
            else{
                budgetLauout.setError(null);
              //  checkIfEnableButton();
            }
        }
    }
}
