package com.example.lukasz.apptravel.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Spinner;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.datevalidator.DateInputValidator;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditPrzejazdItemActivity extends AppCompatActivity {

    private long travelId;
    private long przejazdId;
    private AppDatabase mDb;
    private TextInputLayout nameLayout;
    private EditText nameInput;
    private TextInputLayout dateLayout;
    private EditText dateInput;
    private ImageButton dateButton;
    private TextInputLayout timeLayout;
    private EditText timeInput;
    private Spinner categorySpinner;
    private TextInputLayout priceLayout;
    private EditText priceInput;
    private Button buttonSubmit;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateOdPodrozy;
    private Date dateDoPodrozy;
    private TextInputLayout walutaLayout;
    private EditText walutaInput;
    private String waluta;
    DatePickerDialog.OnDateSetListener dateFrom;
    Calendar calendarFrom = Calendar.getInstance();
    private Przejazd przejazd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_przejazd_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.editridelabel);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        przejazdId=intent.getLongExtra("przejazdId",0);
        mDb= AppDatabase.getInstance(getApplicationContext());

        nameLayout=findViewById(R.id.editprzejazdnamelayout);
        nameInput=findViewById(R.id.editprzejazdnameinput);
        dateLayout=findViewById(R.id.editprzejazddatefromlayout);
        dateInput=findViewById(R.id.editprzejazddatefrominput);
        dateButton=findViewById(R.id.editprzejazddateimageButton);
        timeLayout=findViewById(R.id.editprzejazdtimelayout);
        timeInput=findViewById(R.id.edittimeinput);
        categorySpinner=findViewById(R.id.editprzejazdkategoriaspinner);
        priceLayout=findViewById(R.id.editprzejazdpricelayout);
        priceInput=findViewById(R.id.editprzejazdpriceinput);
        buttonSubmit=findViewById(R.id.editaddprzejazdbutton);
        buttonSubmit.setEnabled(true);
        walutaLayout=findViewById(R.id.editprzejazdwalutalayout);
        walutaInput=findViewById(R.id.editprzejazdwalutainput);
        waluta=mDb.przejazdDao().getPrzejazdById(przejazdId).getWaluta();
        walutaInput.setText(waluta);

        przejazd = mDb.przejazdDao().getPrzejazdById(przejazdId);

        nameInput.setText(przejazd.getNazwa());
        if(przejazd.getKoszt().toString()!="0.0") priceInput.setText(przejazd.getKoszt().toString());

        Date data=przejazd.getDataOd();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
        String dateFormated=dt.format(data);
        String cutSeconds=dateFormated.substring(0,18);
        String dateCalendar=dateFormated.substring(0,10);
        String dateTime=dateFormated.substring(13,18);

        dateInput.setText(dateCalendar);
        timeInput.setText(dateTime);

        dateOdPodrozy=mDb.podrozDao().getDateOdByTravelId(travelId);
        dateDoPodrozy=mDb.podrozDao().getDateDoByTravelId(travelId);

        List<String> listaKategorii;
        listaKategorii=mDb.kategoriaPrzejazduDao().getAllNazwyKategoriiPrzejazdu();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listaKategorii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection((int)przejazd.getKategoriaPrzejazduId()-1);


        walutaInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");

                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                        walutaInput.setText(code);
                        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                        waluta=code;
                        picker.dismiss();

                    }
                });
            }

        });

        walutaInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
                    picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");

                    picker.setListener(new CurrencyPickerListener() {
                        @Override
                        public void onSelectCurrency(String name, String code, String dialCode, int flagDrawableResID) {
                            walutaInput.setText(code);
                            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(picker.getView().getWindowToken(), 0);
                            waluta=code;
                            picker.dismiss();
                        }
                    });
                }
            }
        });


        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s); }
        });

        timeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateTime(s); }
        });

        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateDateFrom(s); }
        });

        nameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        timeInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateTime(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        dateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDateFrom(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });
        priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePrice(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                calendarFrom.set(Calendar.YEAR, year);
                calendarFrom.set(Calendar.MONTH, monthOfYear);
                calendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(calendarFrom, dateInput, dateLayout);
                checkIfNotEarlierTo();
            }
        };

        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(EditPrzejazdItemActivity.this, dateFrom, calendarFrom.get(Calendar.YEAR),
                        calendarFrom.get(Calendar.MONTH), calendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher dateFromWatcherMask = new MaskTextWatcher(dateInput, smf);
        dateInput.addTextChangedListener(dateFromWatcherMask);

        SimpleMaskFormatter smf1 = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher timeWatcherMask = new MaskTextWatcher(timeInput, smf1);
        timeInput.addTextChangedListener(timeWatcherMask);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nazwa= nameInput.getText().toString();
                Date date1 = null;
                String date= dateInput.getText().toString();
                date+=(" ");
                date+=(timeInput.getText().toString());
                date+=(":00");
                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long categoryId=mDb.kategoriaPrzejazduDao().getIdKategoriiPrzejazduOdNazwy(categorySpinner.getSelectedItem().toString());
                double price;
                if(priceInput.getText().toString().trim().equals("")){
                    price=0;
                }
                else price=Double.parseDouble(priceInput.getText().toString());

                mDb.przejazdDao().updatePrzejazd(przejazdId,categoryId,nazwa,date1,price,waluta);

                Intent intent=new Intent(EditPrzejazdItemActivity.this, PrzejazdyActivity.class);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
                finish();
            }
        });


    }

    private void validateEditText(Editable s) {
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
            nameLayout.setError(getString(R.string.noridenameerror));
            checkIfEnableButton();
        }

        else if(s.toString().length()>60){
            nameLayout.setError(getString(R.string.maxsixtylettererror));
            checkIfEnableButton();
        }
        else {
            nameLayout.setError(null);
            checkIfEnableButton();
        }
    }

    private void validateTime(Editable s){
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
            timeLayout.setError(getString(R.string.notimeerror));
            checkIfEnableButton();
            return;
        }
        if(s.toString().length()<5){
            timeLayout.setError(getString(R.string.badtimeformaterror));
            checkIfEnableButton();
            return;
        }
        if(Integer.parseInt(s.toString().substring(0,2))>23 || Integer.parseInt(s.toString().substring(3,5))>59){
            timeLayout.setError(getString(R.string.badtimeformaterror));
            checkIfEnableButton();
        }
        else{
            timeLayout.setError(null);
            checkIfEnableButton();
        }


    }

    public void checkIfEnableButton(){
        if(
                TextUtils.isEmpty(nameLayout.getError()) &&
                        TextUtils.isEmpty(dateLayout.getError()) &&
                        TextUtils.isEmpty(priceLayout.getError()) &&
                        TextUtils.isEmpty(timeLayout.getError()) &&
                        !nameInput.getText().toString().equals("") &&
                        !dateInput.getText().toString().equals("") &&
                        !timeInput.getText().toString().equals(""))
        {
            buttonSubmit.setEnabled(true);
        }
        else buttonSubmit.setEnabled(false);
    }

    private void validatePrice(Editable s){

        if (s.toString().length()>9){
            priceLayout.setError(getString(R.string.maxninenumbers));
            checkIfEnableButton();
        }
        else if (".".equals(s.toString())){
            priceLayout.setError(getString(R.string.badpriceformat));
            checkIfEnableButton();
        }


        else if(s.toString().contains(".")) {
            int integerPlaces = s.toString().indexOf('.');
            int decimalPlaces = s.toString().length() - integerPlaces - 1;
            if (decimalPlaces > 2) {
                priceLayout.setError(getString(R.string.decimalplaceserror));
                checkIfEnableButton();
            }

            else if(Double.parseDouble(s.toString())<=0){
                priceLayout.setError(getString(R.string.pricemorethanzeroerror));
                checkIfEnableButton();
            }

            else{
                priceLayout.setError(null);
                checkIfEnableButton();
            }
        }
        else if(!s.toString().isEmpty() && Double.parseDouble(s.toString())<=0){
            priceLayout.setError(getString(R.string.pricemorethanzeroerror));
            checkIfEnableButton();
        }

        else {
            priceLayout.setError(null);
            checkIfEnableButton();
        }
    }

    private void checkIfNotEarlierTo(){
        Date dateWpisana=null;
        Date formateedDateOdPodrozy=null;
        Date formatedDateDoPodrozy=null;


        if (TextUtils.isEmpty(dateLayout.getError()) && !dateInput.getText().toString().equals("")){
            try {
                dateWpisana=new SimpleDateFormat("dd/MM/yyyy").parse(dateInput.getText().toString());
                formateedDateOdPodrozy=new SimpleDateFormat("dd/MM/yyyy").parse(dateFormat.format(dateOdPodrozy));
                formatedDateDoPodrozy=new SimpleDateFormat("dd/MM/yyyy").parse(dateFormat.format(dateDoPodrozy));

                String a=dateInput.getText().toString();
                String b=dateFormat.format(dateOdPodrozy);
                String c=dateFormat.format(dateDoPodrozy);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(dateWpisana.before(formateedDateOdPodrozy) || dateWpisana.after(formatedDateDoPodrozy)){
                dateLayout.setError(getString(R.string.ridenotbetweentraveldateerror));
                checkIfEnableButton();
            }
            else{
                dateLayout.setError(null);
                checkIfEnableButton();
            }
        }
    }

    private void validateDateFrom(Editable s) {

        dateLayout.setError(null);
        DateInputValidator dateInputValidator=new DateInputValidator();

        if (TextUtils.isEmpty(s)) {
            dateLayout.setError(getString(R.string.noprzejazddateerror));
            checkIfEnableButton();
        }

        else if (!dateInputValidator.validate(s.toString())){
            dateLayout.setError(getString(R.string.baddateformaterror));
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditPrzejazdItemActivity.this, PrzejazdyActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
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
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
