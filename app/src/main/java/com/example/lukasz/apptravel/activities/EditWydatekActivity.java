package com.example.lukasz.apptravel.activities;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.Wydatek;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.util.List;

public class EditWydatekActivity extends AppCompatActivity {

    private long wydatekId;
    private long travelId;
    private TextInputLayout nameLayout;
    private EditText nameInput;
    private Spinner categorySpinner;
    private TextInputLayout priceLayout;
    private EditText priceInput;
    private AppDatabase mDb;
    private Button buttonSubmit;
    private Wydatek wydatek;
    private TextInputLayout walutaLayout;
    private EditText walutaInput;
    private String waluta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wydatek);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.editwydateklabel);
        mDb= AppDatabase.getInstance(getApplicationContext());
        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        wydatekId=intent.getLongExtra("wydatekId",0);

        wydatek=mDb.wydatekDao().getWydatekById(wydatekId);

        nameLayout=findViewById(R.id.editwydateknamelayout);
        nameInput=findViewById(R.id.editwydatekdnameinput);
        categorySpinner=findViewById(R.id.editwydatekkategoriaspinner);
        priceLayout=findViewById(R.id.editwydatekpricelayout);
        priceInput=findViewById(R.id.editwydatekpriceinput);
        buttonSubmit=findViewById(R.id.editaddwydatekbutton);
        buttonSubmit.setEnabled(true);
        walutaLayout=findViewById(R.id.editwydatekwalutalayout);
        walutaInput=findViewById(R.id.editwydatekwalutainput);
        waluta=mDb.wydatekDao().getWydatekById(wydatekId).getWaluta();
        walutaInput.setText(waluta);



        List<String> listaKategorii;
        listaKategorii=mDb.kategoriaWydatkuDao().getAllNazwyKategoriiWydatku();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listaKategorii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);



        nameInput.setText(wydatek.getNazwa());
        priceInput.setText(wydatek.getKoszt().toString());
        categorySpinner.setSelection((int)wydatek.getKategoriaWydatkuId()-1);

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

        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validatePrice(s); }
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

        priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePrice(((EditText) v).getText());
                    hideKeyboard(v);
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nazwa= nameInput.getText().toString();
                long categoryId=mDb.kategoriaWydatkuDao().getIdKategoriiWydatkuOdNazwy(categorySpinner.getSelectedItem().toString());
                double price;
                if(priceInput.getText().toString().trim().equals("")){
                    price=0;
                }
                else price=Double.parseDouble(priceInput.getText().toString());

                mDb.wydatekDao().updateWydatekById(wydatekId,nazwa,price,waluta,categoryId);

                Intent intent=new Intent(EditWydatekActivity.this, WydatkiActivity.class);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
                finish();
            }});

    }

    private void validatePrice(Editable s){

        if(s.toString().equals("")){
            priceLayout.setError(getString(R.string.nopriceerror));
            checkIfEnableButton();
        }

        else if (s.toString().length()>9){
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

    private void validateEditText(Editable s) {
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
            nameLayout.setError(getString(R.string.noridenameerror));
            checkIfEnableButton();
        }

        else if(s.toString().length()>30){
            nameLayout.setError(getString(R.string.maxthirtyletters));
            checkIfEnableButton();
        }
        else {
            nameLayout.setError(null);
            checkIfEnableButton();
        }
    }

    public void checkIfEnableButton(){
        if(
                TextUtils.isEmpty(nameLayout.getError()) &&

                        TextUtils.isEmpty(priceLayout.getError()) &&

                        !nameInput.getText().toString().equals("") &&
                        !priceInput.getText().toString().equals("")
                )
        {
            buttonSubmit.setEnabled(true);
        }
        else buttonSubmit.setEnabled(false);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditWydatekActivity.this, WydatkiActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }
}
