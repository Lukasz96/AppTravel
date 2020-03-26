package com.my.lukasz.apptravel.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.AppDatabase;
import com.my.lukasz.apptravel.db.entities.Podroz;
import com.my.lukasz.apptravel.statsTools.AxisMonthFormatter;
import com.my.lukasz.apptravel.statsTools.PercentValueFormatter;
import com.my.lukasz.apptravel.statsTools.ToIntAxisFormatter;
import com.my.lukasz.apptravel.statsTools.ToIntValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GlobalStatsActivity extends AppCompatActivity {

    private TextView numberTravelsTv;
    private TextView numberdaysTv;
    private TextView numberridesTv;
    private TextView numbercurrenciesTv;
    private TextView numberIlePodrozy;
    private AppDatabase mDb;
    private TextView numberIleDni;
    private TextView numberileprzejazdow;
    private TextView numberilewlaut;
    private BarChart ileRazywMiesWykres;
    private PieChart jakieTransportyWykres;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_stats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.statsgloballabel);

        mDb=AppDatabase.getInstance(this);

        numberTravelsTv=findViewById(R.id.travelscoveredlabel);
        numberdaysTv=findViewById(R.id.travelsdays);
        numberridesTv=findViewById(R.id.travelsrideslabel);
        numbercurrenciesTv=findViewById(R.id.travelscurrencies);
        numberIlePodrozy=findViewById(R.id.numberilepodrozy);
        numberIleDni=findViewById(R.id.numberiledni);
        numberileprzejazdow=findViewById(R.id.numberileprzejazdow);
        numberilewlaut=findViewById(R.id.numberilewlaut);
        ileRazywMiesWykres=findViewById(R.id.wykresileRazyWMies);
        jakieTransportyWykres=findViewById(R.id.wykresrodzajetransportu);



        int howMuchTravels=mDb.podrozDao().getPodroze().size();
        int howMuchRides=mDb.przejazdDao().getAllPrzejazdy().size();

        int howMuchDays=0;
        for(Podroz podroz:mDb.podrozDao().getPodroze()){
            int ileDni=getDifferenceDays(podroz.getDataOd(),podroz.getDataDo());
            howMuchDays+=ileDni;
        }

        List<String> waluty=new ArrayList<>();
        for(Podroz podroz:mDb.podrozDao().getPodroze()){
            long travelId=podroz.getId();
            long listaId=0;
            if(mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId)!=null) listaId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();
            List<String>walutyShopping=mDb.elementListyDoSpakowaniaDao().getWalutySpakowaneKupione(listaId,true,true);
            List<String>walutyPrzejazdy=mDb.przejazdDao().getWalutyPrzejazdy(travelId);
            List<String>walutyWydatki=mDb.wydatekDao().getWalutyWydatki(travelId);

            for(String waluta:walutyShopping){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
            for(String waluta:walutyPrzejazdy){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
            for(String waluta:walutyWydatki){
                if(!waluty.contains(waluta)) waluty.add(waluta);
            }
        }

        int howMuchCurrencies=waluty.size();
        numberIlePodrozy.append(howMuchTravels+" ");
        numberTravelsTv.append(getResources().getString(R.string.daneIlePodrozy));
        numberIleDni.append(howMuchDays+" ");
        numberdaysTv.append(getResources().getString(R.string.daneiledni));
        numberilewlaut.append(howMuchCurrencies+" ");
        numbercurrenciesTv.append(getResources().getString(R.string.daneilewalut));
        numberileprzejazdow.append(howMuchRides+" ");
        numberridesTv.append(getResources().getString(R.string.daneileprzejazdów));


        //////////////// WYKRES ILE RAZY W JAKIM MIES ////////////////////////////////////////////

        List<Long> podrozeIds=new ArrayList<>();
        podrozeIds=mDb.podrozDao().getAllPodrozeId();

        List<String>miesiace=new ArrayList<>();

        for(long id:podrozeIds){
            System.out.println("ILE PODROZ  "+podrozeIds.size());
            LocalDate date1 = convertToLocalDate(mDb.podrozDao().getDateOdByTravelId(id));
            LocalDate date2 = convertToLocalDate(mDb.podrozDao().getDateDoByTravelId(id));
            while(date1.isBefore(date2) || date1.isEqual(date2)){
                System.out.print("MIES : ");
                System.out.println(date1.toString("MM"));
                miesiace.add(date1.toString("MM"));
                date1 = date1.plus(Period.months(1));
            }
        }

       // Set<String> uniqueMonths = new HashSet<String>(miesiace);


        ArrayList<BarEntry> entryArrayListInnaWaluta= new ArrayList<>();
        entryArrayListInnaWaluta.add(new BarEntry(0, Collections.frequency(miesiace, "01")));
        entryArrayListInnaWaluta.add(new BarEntry(1, Collections.frequency(miesiace, "02")));
        entryArrayListInnaWaluta.add(new BarEntry(2, Collections.frequency(miesiace, "03")));
        entryArrayListInnaWaluta.add(new BarEntry(3, Collections.frequency(miesiace, "04")));
        entryArrayListInnaWaluta.add(new BarEntry(4, Collections.frequency(miesiace, "05")));
        entryArrayListInnaWaluta.add(new BarEntry(5, Collections.frequency(miesiace, "06")));
        entryArrayListInnaWaluta.add(new BarEntry(6, Collections.frequency(miesiace, "07")));
        entryArrayListInnaWaluta.add(new BarEntry(7, Collections.frequency(miesiace, "08")));
        entryArrayListInnaWaluta.add(new BarEntry(8, Collections.frequency(miesiace, "09")));
        entryArrayListInnaWaluta.add(new BarEntry(9, Collections.frequency(miesiace, "10")));
        entryArrayListInnaWaluta.add(new BarEntry(10, Collections.frequency(miesiace, "11")));
        entryArrayListInnaWaluta.add(new BarEntry(11, Collections.frequency(miesiace, "12")));

        BarDataSet set1= new BarDataSet(entryArrayListInnaWaluta,"");
        set1.setColors(new int[]{Color.parseColor("#ffcc66"), Color.parseColor("#23a00c")
                , Color.parseColor("#117ddb"),Color.parseColor("#cccc00"), Color.parseColor("#e67300"),
                Color.parseColor("#cc6699"),Color.parseColor("#96d8dc")});
        set1.setDrawValues(true);
        set1.setValueTextSize(16);
        set1.setValueFormatter(new ToIntValueFormatter());
        BarData barData= new BarData(set1);

        ileRazywMiesWykres.getAxisRight().setEnabled(false);
        //ileRazywMiesWykres.getLegend().setCustom(Arrays.asList(zakupy,noclegi,jedzenie,zwiedzanie,transport,inne));

        ileRazywMiesWykres.getLegend().setEnabled(false);
        ileRazywMiesWykres.getDescription().setEnabled(false);
        ileRazywMiesWykres.getAxisLeft().setTextSize(16);


        ileRazywMiesWykres.getXAxis().setValueFormatter(new AxisMonthFormatter(this));
        ileRazywMiesWykres.getXAxis().setLabelRotationAngle(-45);
        ileRazywMiesWykres.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        ileRazywMiesWykres.getAxisLeft().setValueFormatter(new ToIntAxisFormatter());
        ileRazywMiesWykres.getAxisLeft().setGranularity(1f);

        ileRazywMiesWykres.getXAxis().setLabelCount(12);
        ileRazywMiesWykres.getXAxis().setTextSize(12);
        ileRazywMiesWykres.getXAxis().setDrawAxisLine(false);
        ileRazywMiesWykres.getXAxis().setDrawGridLines(false);
        ileRazywMiesWykres.animateXY(650,650);
        ileRazywMiesWykres.setData(barData);


        //////////////////////////////////////////////////
        //////////// WYKRES JAKIE ŚRODKI TRANSPORTU ///////////////

        jakieTransportyWykres=findViewById(R.id.wykresrodzajetransportu);
        jakieTransportyWykres.setUsePercentValues(true);
        jakieTransportyWykres.setDrawHoleEnabled(true);
        jakieTransportyWykres.getDescription().setEnabled(false);
        jakieTransportyWykres.setTransparentCircleRadius(55f);
        jakieTransportyWykres.setDragDecelerationFrictionCoef(0.95f);
        jakieTransportyWykres.getLegend().setTextSize(19);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues=getTransportData();
        PieDataSet dataSet = new PieDataSet(yValues,"");


        dataSet.setColors(new int[]{Color.parseColor("#248f24"), Color.parseColor("#0099ff")
                , Color.parseColor("#cc3300"),Color.parseColor("#e68a00"), Color.parseColor("#669999")});
        dataSet.setValueFormatter(new PercentValueFormatter());
        dataSet.setValueTextColor(Color.rgb(255, 255, 255));
        dataSet.setValueTextSize(16);
        PieData data1=new PieData(dataSet);
        data1.setValueTextSize(16f);

        jakieTransportyWykres.setData(data1);
        jakieTransportyWykres.animateX(800);

        MobileAds.initialize(this, "ca-app-pub-9758633376103774~4671280518");
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    LocalDate convertToLocalDate(Date date) {
        if(date == null) return null;
        return new LocalDate(date);
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
    }

    private ArrayList<PieEntry> getTransportData(){

        ArrayList<PieEntry> values = new ArrayList<>();

        float cenyAut=(float)mDb.przejazdDao().getPrzejazdyByCategory(1);
        float cenySamolotow=(float)mDb.przejazdDao().getPrzejazdyByCategory(2);
        float cenyPociagu=(float)mDb.przejazdDao().getPrzejazdyByCategory(3);
        float cenyStatku=(float)mDb.przejazdDao().getPrzejazdyByCategory(4);
        float cenyRoweru=(float)mDb.przejazdDao().getPrzejazdyByCategory(5);
        float cenyPieszo=(float)mDb.przejazdDao().getPrzejazdyByCategory(6);
        float cenyInne=(float)mDb.przejazdDao().getPrzejazdyByCategory(7);

        if(cenyAut>0) values.add(new PieEntry(cenyAut,getResources().getString(R.string.carlabel)));
        if(cenySamolotow>0) values.add(new PieEntry(cenySamolotow,getResources().getString(R.string.planelabel)));
        if(cenyPociagu>0) values.add(new PieEntry(cenyPociagu,getResources().getString(R.string.trainlabel)));
        if(cenyStatku>0) values.add(new PieEntry(cenyStatku,getResources().getString(R.string.shiplabel)));
        if(cenyRoweru>0) values.add(new PieEntry(cenyRoweru,getResources().getString(R.string.bikelabel)));
        if(cenyPieszo>0) values.add(new PieEntry(cenyPieszo,getResources().getString(R.string.onfootlabel)));
        if(cenyInne>0) values.add(new PieEntry(cenyInne,getResources().getString(R.string.otherlabel)));

        return values;


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


}
