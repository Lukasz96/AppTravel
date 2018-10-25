package com.example.lukasz.apptravel.activities;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.gildaswise.horizontalcounter.HorizontalCounter;

import java.util.ArrayList;
import java.util.List;

public class AddNewPackListItemActivity extends AppCompatActivity {

   private TextInputLayout itemNameLayout;
   private EditText nameInput;
   private HorizontalCounter horizontalCounter;
   private long packListId;
   private Spinner kategoriaSpinner;
   private AppDatabase mDb;
   private Button buttonSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pack_list_item);

        mDb=AppDatabase.getInstance(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.newitemtobacktitle);

        Intent intent=getIntent();
        packListId=intent.getLongExtra("packListId",0);

        itemNameLayout=findViewById(R.id.itemtopacknamelayout);
        nameInput=findViewById(R.id.itemtopacknameinput);

        horizontalCounter=findViewById(R.id.horizontal_counter);
        horizontalCounter.setDisplayingInteger(true);

        kategoriaSpinner=findViewById(R.id.kategoriaspinner);
        buttonSubmit=findViewById(R.id.buttonAddPackItem);
        buttonSubmit.setEnabled(false);

        List<String> listaKategorii;
        listaKategorii=mDb.kategoriaDao().getAllNazwyKategorii();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listaKategorii);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategoriaSpinner.setAdapter(adapter);

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
                }
            }
        });

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

    private void validateEditText(Editable s) {
        if (TextUtils.isEmpty(s)) {
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




}
