package com.my.lukasz.apptravel.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;


import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.datevalidator.DateInputValidator;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.Podroz;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.my.lukasz.apptravel.db.entities.User;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateTravelActivity extends AppCompatActivity {

    private TextInputLayout nameTravel;
    private TextInputLayout dateFromLayout;
    private TextInputLayout dateToLayout;
    private TextInputLayout budgetLauout;
    private TextInputLayout ageLauout;
    private Spinner categoryHolidaysDropdown;
    private Spinner categoryTransportDropdown;
    private Spinner categoryWeatherDropdown;
    private Spinner categoryPlecDropdown;
    private EditText ageInput;
    private Button buttonSubmit;
    private ImageButton dateFromButton;
    private ImageButton dateToButton;
    private EditText dateFromInput;
    private EditText dateToInput;
    private EditText editName;
    private EditText budgetInput;
    private EditText waluta;
    private String walutaInput;
    private DateInputValidator dateInputValidator;
    private AppDatabase mDb;
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
        actionBar.setTitle(getString(R.string.newtravellabel));
        mDb= AppDatabase.getInstance(getApplicationContext());

        editName=findViewById(R.id.travelnameinput);
        nameTravel=findViewById(R.id.travelnamelayout);
        dateFromLayout=findViewById(R.id.datefromlayout);
        budgetLauout=findViewById(R.id.travelbudgetlayout);
        dateToLayout=findViewById(R.id.datetolayout);
        buttonSubmit=findViewById(R.id.button6);
        budgetInput=findViewById(R.id.travelbudgetinput);
        ageLauout=findViewById(R.id.ageinputlayout);
        categoryHolidaysDropdown=findViewById(R.id.holidayType);
        categoryTransportDropdown=findViewById(R.id.transportCategory);
        categoryWeatherDropdown=findViewById(R.id.weatherCategory);
        categoryPlecDropdown=findViewById(R.id.plecCategory);
        ageInput=findViewById(R.id.agetinput);
        buttonSubmit.setEnabled(false);
        dateFromButton=findViewById(R.id.imageButton);
        dateFromInput=findViewById(R.id.datefrominput);
        dateToButton=findViewById(R.id.imageButton2);
        dateToInput=findViewById(R.id.datetoinput);
        waluta=findViewById(R.id.walutainput);
        waluta.setShowSoftInputOnFocus(false);
        walutaInput=waluta.getText().toString();

        ScrollView scrollView= findViewById(R.id.createtravelactivity);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            scrollView.setBackgroundResource(R.drawable.main_menu_background);
        }
        else {
            scrollView.setBackgroundResource(R.drawable.main_menu_background_landscape);
        }

        List<String> kategorieWakacji;
        kategorieWakacji=mDb.kategoriaWakacjiDao().getAllNazwyKategoriiWakacji();

        ArrayAdapter<String> adapterTypWakacj = new ArrayAdapter<String>(this,
                R.layout.spinner_item, kategorieWakacji);
        adapterTypWakacj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryHolidaysDropdown.setAdapter(adapterTypWakacj);
        categoryHolidaysDropdown.setSelection(0);
        /////////////////////////////////////////////////////////////////////////////////////////
        List<String> kategorieTranportu;
        kategorieTranportu=mDb.kategoriaTransportDao().getAllNazwyKategoriiTransportu();

        ArrayAdapter<String> adapterTypTranportu = new ArrayAdapter<String>(this,
                R.layout.spinner_item, kategorieTranportu);
        adapterTypTranportu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryTransportDropdown.setAdapter(adapterTypTranportu);
        categoryTransportDropdown.setSelection(0);
        /////////////////////////////////////////////////////////////////////////////////////////
        List<String> kategoriePogody;
        kategoriePogody=mDb.kategoriaPogodyDao().getAllNazwyKategoriiPogody();

        ArrayAdapter<String> adapterTypPogody = new ArrayAdapter<String>(this,
                R.layout.spinner_item, kategoriePogody);
        adapterTypPogody.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryWeatherDropdown.setAdapter(adapterTypPogody);
        categoryWeatherDropdown.setSelection(0);
        /////////////////////////////////////////////////////////////////////////////////////////
        List<String> plci;
        plci=mDb.plecDao().getAllNazwyPlci();

        ArrayAdapter<String> adapterPlci = new ArrayAdapter<String>(this,
                R.layout.spinner_item, plci);
        adapterPlci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryPlecDropdown.setAdapter(adapterPlci);
        categoryPlecDropdown.setSelection(0);

        waluta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");

                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                        waluta.setText(code);
                        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                        walutaInput=code;
                        picker.dismiss();

                    }
                });
            }

        });

        waluta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
                    picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");

                    picker.setListener(new CurrencyPickerListener() {
                        @Override
                        public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                            waluta.setText(code);
                            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                            walutaInput=code;
                            picker.dismiss();
                        }
                    });
                }
            }
        });

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

        ageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateAge(s); }
        });

        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        ageInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateAge(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        budgetInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateBudget(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        dateFromInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDateFrom(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        dateToInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDateTo(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String travelName=editName.getText().toString().trim();
                Date date1 = null;
                Date date2 = null;
                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(dateFromInput.getText().toString());
                    date2=new SimpleDateFormat("dd/MM/yyyy").parse(dateToInput.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                double budget=Double.parseDouble(budgetInput.getText().toString());

                long plecId = mDb.plecDao().getIdPlciOdNazwy(categoryPlecDropdown.getSelectedItem().toString());
                long userId = mDb.userDao().insertUser(new User(0, plecId, Integer.parseInt(ageInput.getText().toString())));
                long kategoriaTransportuId = mDb.kategoriaTransportDao().getIdTransportuOdNazwy(categoryTransportDropdown.getSelectedItem().toString());
                long kategoriaWakacjiId = mDb.kategoriaWakacjiDao().getIdKatWakacjiOdNazwy(categoryHolidaysDropdown.getSelectedItem().toString());
                long kategoriaPogodyId = mDb.kategoriaPogodyDao().getIdPogodyOdNazwy(categoryWeatherDropdown.getSelectedItem().toString());


                long travelId=mDb.podrozDao().insertPodroz(new Podroz(0, userId, kategoriaTransportuId, kategoriaWakacjiId, kategoriaPogodyId, travelName,date1,date2,budget,walutaInput));
                Intent intent=new Intent(CreateTravelActivity.this, TravelMainMenuActivity.class);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
            }
        });



        dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

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
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
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

        dateToLayout.setError(null);
        dateInputValidator=new DateInputValidator();

        if (TextUtils.isEmpty(s)) {
            dateToLayout.setError(getString(R.string.nodatetoerror));
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

    private void validateAge(Editable s) {
        if (TextUtils.isEmpty(s)) {
            ageLauout.setError(getString(R.string.noageerror));
            checkIfEnableButton();
        }

        else if(!tryParseInt(s.toString())) {
            ageLauout.setError(getString(R.string.typeinteger));
            checkIfEnableButton();
        }
        else if (s.toString().length()>3){
            ageLauout.setError(getString(R.string.maxthreenumbers));
            checkIfEnableButton();
        }
        else if(tryParseInt(s.toString())) {
            int age = Integer.parseInt(s.toString());
            if (age < 1 || age > 140) {
                ageLauout.setError(getString(R.string.typecorrectage));
                checkIfEnableButton();
            }
            else {
                ageLauout.setError(null);
                checkIfEnableButton();
            }
        }
        else {
            ageLauout.setError(null);
            checkIfEnableButton();
        }
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void validateBudget(Editable s){
        if (TextUtils.isEmpty(s)) {
            budgetLauout.setError(getString(R.string.nobudgeterror));
            checkIfEnableButton();
        }
        else if (s.toString().length()>9){
            budgetLauout.setError(getString(R.string.maxninenumbers));
            checkIfEnableButton();
        }
        else if (".".equals(s.toString())){
            budgetLauout.setError(getString(R.string.nobudgeterror));
            checkIfEnableButton();
        }

        else if(Double.parseDouble(s.toString())<=0){
            budgetLauout.setError(getString(R.string.budgethigherzerooerror));
            checkIfEnableButton();
        }
        else if(s.toString().contains(".")) {
                int integerPlaces = s.toString().indexOf('.');
                int decimalPlaces = s.toString().length() - integerPlaces - 1;
                if (decimalPlaces > 2) {
                    budgetLauout.setError(getString(R.string.decimalplaceserror));
                    checkIfEnableButton();
                }
                else{
                    budgetLauout.setError(null);
                    checkIfEnableButton();
                }
            }
        else {
             budgetLauout.setError(null);
             checkIfEnableButton();
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
                TextUtils.isEmpty(ageLauout.getError()) &&
                TextUtils.isEmpty(nameTravel.getError()) &&
                !dateToInput.getText().toString().equals("") &&
                !dateFromInput.getText().toString().equals("") &&
                !budgetInput.getText().toString().equals("") &&
                !ageInput.getText().toString().equals("") &&
                !editName.getText().toString().equals("")){
            buttonSubmit.setEnabled(true);
        }
        else buttonSubmit.setEnabled(false);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
