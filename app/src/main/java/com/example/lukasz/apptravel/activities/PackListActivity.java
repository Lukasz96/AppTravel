package com.example.lukasz.apptravel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.lukasz.apptravel.R;
import com.example.lukasz.apptravel.db.AppDatabase;
import com.example.lukasz.apptravel.fragments.PackListClothesFragment;
import com.example.lukasz.apptravel.fragments.PackListDocumentsFragment;
import com.example.lukasz.apptravel.fragments.PackListHygieneFragment;
import com.example.lukasz.apptravel.fragments.PackListOthersFragment;
import com.example.lukasz.apptravel.packlisttools.CustomTabLayout;

public class PackListActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private long travelId;
    private long packListId;
    private long categoryId;
    AppDatabase mDb;
    private Bundle bundlePackListId;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_activity_pack_list);

        Intent intent=getIntent();
        travelId=intent.getLongExtra("travelId",0);
        categoryId=intent.getLongExtra("categoryId",-1);
    //    travelId=intent.getLongExtra("travelId",0);
        mDb=AppDatabase.getInstance(getApplicationContext());
        packListId=mDb.listaDoSpakowaniaDao().getListaDoSpakowaniaByTravelId(travelId).getId();

        bundlePackListId = new Bundle();
        bundlePackListId.putLong("bundlePackListId",packListId);
        //bundlePackListId.putLong("travelId",travelId);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        CustomTabLayout tabLayout =  findViewById(R.id.tabs);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if(categoryId!=-1){
            CustomTabLayout.Tab tab = tabLayout.getTabAt((int)categoryId-1);
            tab.select();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PackListActivity.this, AddNewPackListItemActivity.class);
                intent.putExtra("packListId",packListId);
                intent.putExtra("travelId",travelId);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pack_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home){
            onBackPressed();
            return true;
        }
        if (id == R.id.deletepacklistbutton) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.cautionlabel))
                    .setMessage(getString(R.string.deletepacklistquestion))
                    .setIcon(getResources().getDrawable(R.drawable.deleteicon))
                    .setPositiveButton(getString(R.string.yeslabel), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            mDb.listaDoSpakowaniaDao().deleteListaDoSpakowaniaByTravelId(travelId);
                            onBackPressed();
                            Toast.makeText(PackListActivity.this, getString(R.string.deletedinfo), Toast.LENGTH_LONG).show();
                        }})
                    .setNegativeButton(getString(R.string.nolabel), null).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;

                    rootView = inflater.inflate(R.layout.packlistclothesfragmentlayout, container, false);


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position){
                case 0:
                    fragment=new PackListClothesFragment();
                    fragment.setArguments(bundlePackListId);
                   break;
                case 1:
                    fragment=new PackListHygieneFragment();
                    fragment.setArguments(bundlePackListId);
                    break;
                case 2:
                    fragment=new PackListDocumentsFragment();
                    fragment.setArguments(bundlePackListId);
                    break;
                case 3:
                    fragment=new PackListOthersFragment();
                    fragment.setArguments(bundlePackListId);
                    break;
                default:
                    fragment=null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mDb.kategoriaDao().getAllKategoria().size();
        }


    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(PackListActivity.this,TravelMainMenuActivity.class);
        intent.putExtra("travelId", travelId);
        startActivity(intent);
        finish();
    }
}
