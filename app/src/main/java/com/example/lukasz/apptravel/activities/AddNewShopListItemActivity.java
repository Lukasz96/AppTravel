package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lukasz.apptravel.R;

public class AddNewShopListItemActivity extends AppCompatActivity {

    private long travelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shop_list_item);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
    }
}
