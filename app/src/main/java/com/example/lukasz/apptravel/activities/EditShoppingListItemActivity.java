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
import android.widget.Button;
import android.widget.EditText;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.gildaswise.horizontalcounter.HorizontalCounter;

public class EditShoppingListItemActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private long itemId;
    private long travelId;
    private EditText nameInput;
    private TextInputLayout nameInputLayout;
    private HorizontalCounter quantityInput;
    private EditText priceInput;
    private TextInputLayout priceInputLayout;
    private Button buttonSubmit;
    private long listaDoSpakowaniaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_list_item);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.edititemtopack);

        mDb= AppDatabase.getInstance(getApplicationContext());
        buttonSubmit=findViewById(R.id.buttonUpdateShopItem);
        buttonSubmit.setEnabled(true);
        nameInput=findViewById(R.id.editshopnameinput);
        nameInputLayout=findViewById(R.id.editshopitemnamelayout);
        quantityInput=findViewById(R.id.editcounterShopitem);
        priceInput=findViewById(R.id.editpriceshopiteminput);
        priceInputLayout=findViewById(R.id.editshopitempricelayout);




        Intent intent = getIntent();
        itemId=intent.getLongExtra("itemId",0);


        ElementListyDoSpakowania elementListyDoSpakowania=mDb.elementListyDoSpakowaniaDao().getElementListyDoSpakowaniaById(itemId);
        listaDoSpakowaniaId=elementListyDoSpakowania.getListaDoSpakowaniaId();
        travelId=mDb.listaDoSpakowaniaDao().getPodrozIdFromListaDoSpakowaniaId(listaDoSpakowaniaId);

        nameInput.setText(elementListyDoSpakowania.getNazwa());
        int ilosc=elementListyDoSpakowania.getIlosc();
        double iloscDouble=(double)ilosc;
        quantityInput.setCurrentValue(iloscDouble);
        quantityInput.setDisplayingInteger(true);
        if(elementListyDoSpakowania.getCena()==0){
            priceInput.setText("");
        }
        else priceInput.setText(String.valueOf(elementListyDoSpakowania.getCena()));

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateNameInput(s); }
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
                    validateNameInput(((EditText) v).getText());
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
                mDb= AppDatabase.getInstance(getApplicationContext());
                long listToPackId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();

                double dquantity=quantityInput.getCurrentValue();
                int quantity=(int)dquantity;
                double price;
                if(priceInput.getText().toString().trim().equals("")){
                    price=0;
                }
                else price=Double.parseDouble(priceInput.getText().toString());

                mDb.elementListyDoSpakowaniaDao().updateElementListyDoZakupuById(itemId, nameInput.getText().toString().trim(), quantity,price);


                Intent intent=new Intent(EditShoppingListItemActivity.this, ShoppingListActivity.class);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validatePrice(Editable s){

        if (s.toString().length()>9){
            priceInputLayout.setError(getString(R.string.maxninenumbers));
            checkIfEnableButton();
        }
        else if (".".equals(s.toString())){
            priceInputLayout.setError(getString(R.string.badpriceformat));
            checkIfEnableButton();
        }


        else if(s.toString().contains(".")) {
            int integerPlaces = s.toString().indexOf('.');
            int decimalPlaces = s.toString().length() - integerPlaces - 1;
            if (decimalPlaces > 2) {
                priceInputLayout.setError(getString(R.string.decimalplaceserror));
                checkIfEnableButton();
            }

            else if(Double.parseDouble(s.toString())<=0){
                priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
                checkIfEnableButton();
            }

            else{
                priceInputLayout.setError(null);
                checkIfEnableButton();
            }
        }
        else if(!s.toString().isEmpty() && Double.parseDouble(s.toString())<=0){
            priceInputLayout.setError(getString(R.string.pricemorethanzeroerror));
            checkIfEnableButton();
        }

        else {
            priceInputLayout.setError(null);
            checkIfEnableButton();
        }
    }

    private void validateNameInput(Editable s) {
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
            nameInputLayout.setError(getString(R.string.noshopitemerror));
            checkIfEnableButton();
        }

        else if(s.toString().length()>30){
            nameInputLayout.setError(getString(R.string.maxthirtyletters));
            checkIfEnableButton();
        }
        else {
            nameInputLayout.setError(null);
            checkIfEnableButton();
        }
    }

    public void checkIfEnableButton(){
        if(
                TextUtils.isEmpty(nameInputLayout.getError()) &&
                        TextUtils.isEmpty(priceInputLayout.getError()) &&
                        !nameInput.getText().toString().equals("")){
            buttonSubmit.setEnabled(true);
        }
        else buttonSubmit.setEnabled(false);
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
