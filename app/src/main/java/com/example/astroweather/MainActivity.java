package com.example.astroweather;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    TextView tvTimer, tvLongitude, tvLatitude;
    Handler handler;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment moonFragment, sunFragment;
    Toolbar toolbar;
    Runnable time;
    public static int refreshRate;
    public static double latitude;
    public static double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            //tablet i orientacja pionowa
            if (isTablet(this.getApplicationContext()) && getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
                setContentView(R.layout.tablet_layout);
                sunFragment = SunFragment.newInstance();
                moonFragment = MoonFragment.newInstance();
                fragmentTransaction.replace(R.id.sun_fragment, sunFragment);
                fragmentTransaction.replace(R.id.moon_fragment, moonFragment);
                fragmentTransaction.commit();
            }

            //telefon i orientacja pozioma
            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && isTablet(this.getApplicationContext())==false) {
                //setContentView(R.layout.activity_main);
                ViewPager viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

                //orientacja pozioma telefon i tablet
            }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // setContentView(R.layout.activity_main);
                sunFragment = SunFragment.newInstance();
                moonFragment = MoonFragment.newInstance();

                fragmentTransaction.replace(R.id.sun_fragment, sunFragment);
                fragmentTransaction.replace(R.id.moon_fragment, moonFragment);
                fragmentTransaction.commit();
            }

            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            tvLongitude=findViewById(R.id.tvLongitude);
            tvLatitude=findViewById(R.id.tvLatitude);
            latitude = 51.75;
            refreshRate=15;
            longitude = 19.46;

            Bundle bundle = getIntent().getExtras();

            if(bundle != null) {
                latitude = bundle.getDouble("latitude");
                longitude =  bundle.getDouble("longitude");
                refreshRate = bundle.getInt("refreshRate");
            }

            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));





            handler = new Handler();
            tvTimer = findViewById(R.id.tvTimer);

             time = new Runnable() {
                @Override
                public void run() {
                    GregorianCalendar calendar = new GregorianCalendar();
                    tvTimer.setText(dateFormat.format(calendar.getTime()));
                    //makeToast(dateFormat.format(calendar.getTime()));
                    handler.postDelayed(this, 1000);
                }
            };

            time.run();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("exception: ", e.toString());
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        time.run();
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacks(time);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settingsItem:
                onSettingsItemClicked();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void onSettingsItemClicked(){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        intent.putExtra("refreshRate", refreshRate);
        startActivity(intent);

    }

}