package com.example.lukasz.apptravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import com.example.lukasz.apptravel.db.User;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent i =getIntent();
        List<User>list=(List<User>)i.getSerializableExtra("users");
        final TextView textView = findViewById(R.id.textView2);

        for(User user:list){
            textView.append(user.getUserId()+", "+user.getFirstName()+", "+user.getAge()+"\n");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // perform your action here

    }
}
