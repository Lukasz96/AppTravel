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
import android.widget.Toast;


import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreateTravelActivity extends AppCompatActivity {

    private TextInputLayout nameTravel;
    private Button buttonSubmit;
    private ImageButton dateFromButton;
    private EditText dateFromInput;
    Calendar calendarFrom = Calendar.getInstance();
    Calendar calendarTo = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_travel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nowa podróż");

        Display display = getWindowManager().getDefaultDisplay();
        ConstraintLayout constraintLayout= findViewById(R.id.createtravelactivity);
        int backgroundImageId=R.drawable.main_menu_background;
        BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
        Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                400,600);
        constraintLayout.setBackground(backgroundImage);

        EditText editName=findViewById(R.id.travelnameinput);
        nameTravel=findViewById(R.id.travelnamelayout);
        buttonSubmit=findViewById(R.id.button6);
        dateFromButton=findViewById(R.id.imageButton);
        dateFromInput=findViewById(R.id.datefrominput);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(s); }
        });

        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                }
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                
                calendarFrom.set(Calendar.YEAR, year);
                calendarFrom.set(Calendar.MONTH, monthOfYear);
                calendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateFromButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CreateTravelActivity.this,date, calendarFrom.get(Calendar.YEAR), calendarFrom.get(Calendar.MONTH), calendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void validateEditText(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            nameTravel.setError(null);
            buttonSubmit.setEnabled(true);
        }
        else{
            nameTravel.setError(getString(R.string.nonameerror));
            buttonSubmit.setEnabled(false);
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        String data=sdf.format(calendarFrom.getTime()).toString();
        Date date=null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
        }
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

        dateFromInput.setText(fmtOut.format(date));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
