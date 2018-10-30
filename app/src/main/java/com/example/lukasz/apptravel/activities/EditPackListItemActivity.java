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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.gildaswise.horizontalcounter.HorizontalCounter;

import java.util.List;

public class EditPackListItemActivity extends AppCompatActivity {

    private long itemId;
    private AppDatabase mDb;
    private TextInputLayout itemNameLayout;
    private EditText nameInput;
    private HorizontalCounter horizontalCounter;
    private Spinner kategoriaSpinner;
    private Button buttonSubmit;
    private CheckBox isToBuyCheckbox;
    private long listaDoSpakowaniaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pack_list_item);

        Intent intent=getIntent();
        itemId=intent.getLongExtra("itemId",0);

        mDb= AppDatabase.getInstance(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.edititemtopack);

        itemNameLayout=findViewById(R.id.edititemtopacknamelayout);
        nameInput=findViewById(R.id.edititemtopacknameinput);
        horizontalCounter=findViewById(R.id.edithorizontal_counter);
        kategoriaSpinner=findViewById(R.id.editkategoriaspinner);
        buttonSubmit=findViewById(R.id.buttonAddEditedPackItem);
        isToBuyCheckbox=findViewById(R.id.editistobuycheckobox);

        ElementListyDoSpakowania elementListyDoSpakowania=mDb.elementListyDoSpakowaniaDao().getElementListyDoSpakowaniaById(itemId);

        listaDoSpakowaniaId=elementListyDoSpakowania.getListaDoSpakowaniaId();
        nameInput.setText(elementListyDoSpakowania.getNazwa());
        int ilosc=elementListyDoSpakowania.getIloscDoSpakowania();
        double iloscDouble=(double)ilosc;
        horizontalCounter.setCurrentValue(iloscDouble);
        horizontalCounter.setDisplayingInteger(true);

        isToBuyCheckbox.setChecked(elementListyDoSpakowania.isCzyPrzekazanoDoZakupu());

        List<String> listaKategorii;
        listaKategorii=mDb.kategoriaDao().getAllNazwyKategorii();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listaKategorii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriaSpinner.setAdapter(adapter);
        kategoriaSpinner.setSelection((int)elementListyDoSpakowania.getIdKategorii()-1);

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s); }
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

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDb= AppDatabase.getInstance(getApplicationContext());
                String packItemName=nameInput.getText().toString().trim();
                double dquantity = horizontalCounter.getCurrentValue();
                int quantity= (int)dquantity;
                long categoryId=mDb.kategoriaDao().getIdKategoriiOdNazwy(kategoriaSpinner.getSelectedItem().toString());

                boolean isToBuy=isToBuyCheckbox.isChecked();

                mDb.elementListyDoSpakowaniaDao().updateEditItemToPack(itemId, packItemName, quantity, isToBuy, categoryId);
                long travelId=mDb.listaDoSpakowaniaDao().getPodrozIdFromListaDoSpakowaniaId(listaDoSpakowaniaId);
                Intent intent = new Intent(EditPackListItemActivity.this, PackListActivity.class);
                intent.putExtra("travelId",travelId);
                intent.putExtra("categoryId",categoryId);

                startActivity(intent);
            }
        });

    }
    private void validateEditText(Editable s) {
        if (TextUtils.isEmpty(s) || s.toString().trim().equals("")) {
            itemNameLayout.setError(getString(R.string.nonameitemtopackerror));
            checkIfEnableButton();
        }

        else if(s.toString().length()>30){
            itemNameLayout.setError(getString(R.string.maxthirtyletters));
            checkIfEnableButton();
        }
        else {
            itemNameLayout.setError(null);
            checkIfEnableButton();
        }
    }

    public void checkIfEnableButton(){
        if(
                TextUtils.isEmpty(itemNameLayout.getError()) &&
                        !nameInput.getText().toString().equals("")
                ){
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
