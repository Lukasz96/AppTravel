package com.example.lukasz.apptravel.activities;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.datevalidator.DateInputValidator;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateTravelActivity extends AppCompatActivity {

    private TextInputLayout nameTravel;
    private TextInputLayout dateFromLayout;
    private TextInputLayout dateToLayout;
    private TextInputLayout budgetLauout;
    private Button buttonSubmit;
    private ImageButton dateFromButton;
    private ImageButton dateToButton;
    private EditText dateFromInput;
    private EditText dateToInput;
    private EditText editName;
    private EditText budgetInput;
    private DateInputValidator dateInputValidator;
    Calendar calendarFrom = Calendar.getInstance();
    Calendar calendarTo = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateFrom;
    DatePickerDialog.OnDateSetListener dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_travel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nowa podróż");


        editName=findViewById(R.id.travelnameinput);
        nameTravel=findViewById(R.id.travelnamelayout);
        dateFromLayout=findViewById(R.id.datefromlayout);
        budgetLauout=findViewById(R.id.travelbudgetlayout);
        dateToLayout=findViewById(R.id.datetolayout);
        buttonSubmit=findViewById(R.id.button6);
        budgetInput=findViewById(R.id.travelbudgetinput);
        buttonSubmit.setEnabled(false);
        dateFromButton=findViewById(R.id.imageButton);
        dateFromInput=findViewById(R.id.datefrominput);
        dateToButton=findViewById(R.id.imageButton2);
        dateToInput=findViewById(R.id.datetoinput);

        ////////////// USTAWIANIE TŁA
        Display display = getWindowManager().getDefaultDisplay();
        ConstraintLayout constraintLayout= findViewById(R.id.createtravelactivity);
        int backgroundImageId=R.drawable.main_menu_background;
        BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
        Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                400,600);
        constraintLayout.setBackground(backgroundImage);
        ///////////////////////////////

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s); }
        });

        budgetInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateBudget(s); }
        });

        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                }
            }
        });
        budgetInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateBudget(((EditText) v).getText());
                }
            }
        });

        dateFromInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDateFrom(((EditText) v).getText());
                }
            }
        });

        dateToInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDateTo(((EditText) v).getText());
                }
            }
        });



        dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                Date date1=null;
                Date date2=null;
                calendarFrom.set(Calendar.YEAR, year);
                calendarFrom.set(Calendar.MONTH, monthOfYear);
                calendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendarFrom, dateFromInput, dateFromLayout);
                checkIfNotEarlierFrom();
            }
        };

        dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                Date date1=null;
                Date date2=null;

                calendarTo.set(Calendar.YEAR, year);
                calendarTo.set(Calendar.MONTH, monthOfYear);
                calendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendarTo, dateToInput, dateToLayout);
                checkIfNotEarlierTo();
            }
        };

        dateFromButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CreateTravelActivity.this, dateFrom, calendarFrom.get(Calendar.YEAR),
                        calendarFrom.get(Calendar.MONTH), calendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateToButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CreateTravelActivity.this, dateTo, calendarTo.get(Calendar.YEAR),
                        calendarTo.get(Calendar.MONTH), calendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher dateFromWatcherMask = new MaskTextWatcher(dateFromInput, smf);
        MaskTextWatcher dateToWatcherMask = new MaskTextWatcher(dateToInput, smf);
        dateFromInput.addTextChangedListener(dateFromWatcherMask);
        dateToInput.addTextChangedListener(dateToWatcherMask);

    }

    private void validateEditText(Editable s) {
        if (TextUtils.isEmpty(s)) {
            nameTravel.setError(getString(R.string.nonameerror));
            checkIfEnableButton();
        }

        else if(s.toString().length()>30){
            nameTravel.setError(getString(R.string.maxthirtyletters));
            checkIfEnableButton();
        }
        else {
            nameTravel.setError(null);
            checkIfEnableButton();
        }
    }

    private void validateDateFrom(Editable s) {

        Date date1=null;
        Date date2=null;

        dateFromLayout.setError(null);
        dateInputValidator=new DateInputValidator();

        if (TextUtils.isEmpty(s)) {
            dateFromLayout.setError(getString(R.string.nodatefromerror));
            checkIfEnableButton();
        }

        else if (!dateInputValidator.validate(s.toString())){
            dateFromLayout.setError(getString(R.string.baddateformaterror));
            checkIfEnableButton();
        }
        else checkIfNotEarlierFrom();

    }

    private void validateDateTo(Editable s) {

        Date date1=null;
        Date date2=null;

        dateToLayout.setError(null);
        dateInputValidator=new DateInputValidator();

        if (TextUtils.isEmpty(s)) {
            dateToLayout.setError(getString(R.string.nodatefromerror));
            checkIfEnableButton();
        }
        else if (!dateInputValidator.validate(s.toString())){
            dateToLayout.setError(getString(R.string.baddateformaterror));
            checkIfEnableButton();
        }

        else checkIfNotEarlierTo();


    }

    private void updateLabel(Calendar calendar, EditText dateInput, TextInputLayout textInputLayout ) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String data=sdf.format(calendar.getTime()).toString();
        Date date=null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

        dateInput.setText(fmtOut.format(date));
        textInputLayout.setError(null);
        checkIfEnableButton();
    }

    private void checkIfNotEarlierTo(){
        Date date1=null;
        Date date2=null;
        if (TextUtils.isEmpty(dateFromLayout.getError()) && !dateFromInput.getText().toString().equals("")){
            try {
                date1=new SimpleDateFormat("dd/MM/yyyy").parse(dateFromInput.getText().toString());
                date2=new SimpleDateFormat("dd/MM/yyyy").parse(dateToInput.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("POROWNANIE -------- "+date1.compareTo(date2));
            if(date1.compareTo(date2)>0){
                dateToLayout.setError(getString(R.string.datetosmallererror));
                checkIfEnableButton();
            }
            else{
                dateFromLayout.setError(null);
                dateToLayout.setError(null);
                checkIfEnableButton();
            }
        }
    }
    private void checkIfNotEarlierFrom(){
        Date date1=null;
        Date date2=null;
        if ((TextUtils.isEmpty(dateToLayout.getError()) ||
                dateToLayout.getError().toString().equals(getString(R.string.datetosmallererror)) )
                && !dateToInput.getText().toString().equals("")){
            try {
                date1=new SimpleDateFormat("dd/MM/yyyy").parse(dateFromInput.getText().toString());
                date2=new SimpleDateFormat("dd/MM/yyyy").parse(dateToInput.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(date1.compareTo(date2)>0){
                dateToLayout.setError(getString(R.string.datetosmallererror));
                checkIfEnableButton();
            }
            else{
                dateFromLayout.setError(null);
                dateToLayout.setError(null);
                checkIfEnableButton();
            }
        }
    }

    private void validateBudget(Editable s){
        if (TextUtils.isEmpty(s)) {
            budgetLauout.setError(getString(R.string.nobudgeterror));
            checkIfEnableButton();
        }
        if (s.toString().length()>9){
            budgetLauout.setError(getString(R.string.maxninenumbers));
            checkIfEnableButton();
        }
        else {
            int integerPlaces = s.toString().indexOf('.');
            int decimalPlaces = s.toString().length() - integerPlaces - 1;
            if(decimalPlaces>2){
                budgetLauout.setError(getString(R.string.decimalplaceserror));
                checkIfEnableButton();
            }
            else{
                budgetLauout.setError(null);
                checkIfEnableButton();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkIfEnableButton(){
        if(
                TextUtils.isEmpty(dateToLayout.getError()) &&
                TextUtils.isEmpty(dateFromLayout.getError()) &&
                TextUtils.isEmpty(budgetLauout.getError()) &&
                TextUtils.isEmpty(nameTravel.getError()) &&
                !dateToInput.getText().toString().equals("") &&
                !dateFromInput.getText().toString().equals("") &&
                !budgetInput.getText().toString().equals("") &&
                !editName.getText().toString().equals("")){
            buttonSubmit.setEnabled(true);
        }
        else buttonSubmit.setEnabled(false);
    }
}
