package com.example.lukasz.apptravel.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.imageCalc.BackgroundImageCalc;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb= AppDatabase.getInstance(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.button);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             mDb.podrozDao().insertPodroz(new Podroz(0,"podroz1", new Date(),new Date(),null));
            try{
                mDb.listaDoSpakowaniaDao().insertListeDoSpakowania(new ListaDoSpakowania(0,"lista1",
                        false, 1));
            } catch (SQLiteConstraintException e){
                Toast.makeText(MainActivity.this, "1 Podróż może mieć 1 listę!",
                        Toast.LENGTH_LONG).show();
            }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, Test.class);
              startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

        //        mDb.userDao().deleteAllUsers();
                Toast.makeText(MainActivity.this, "Usunieto wszystkie rekordy!",
                        Toast.LENGTH_LONG).show();
            }
        });


        Display display = getWindowManager().getDefaultDisplay();
        ConstraintLayout constraintLayout= findViewById(R.id.mainActivity);
        int backgroundImageId=R.drawable.main_menu_background;
        BackgroundImageCalc backgroundImageCalc=new BackgroundImageCalc(this.getApplicationContext());
        Drawable backgroundImage=backgroundImageCalc.getCalculatedBackroundImage(display,backgroundImageId,
                800,1200);
        constraintLayout.setBackground(backgroundImage);

    }

}
