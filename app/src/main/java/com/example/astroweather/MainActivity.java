package com.example.astroweather;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    TextView tvTimer;
    Handler handler;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment moonFragment, sunFragment;
    ImageView settings;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            settings=findViewById(R.id.settings);
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                ViewPager viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                sunFragment = SunFragment.newInstance();
                moonFragment = MoonFragment.newInstance();

                fragmentTransaction.replace(R.id.sun_fragment, sunFragment);
                fragmentTransaction.replace(R.id.moon_fragment, moonFragment);
                fragmentTransaction.commit();
            }


            handler = new Handler();
            tvTimer = findViewById(R.id.tvTimer);

            Runnable time = new Runnable() {
                @Override
                public void run() {
                    GregorianCalendar calendar = new GregorianCalendar();
                    tvTimer.setText(dateFormat.format(calendar.getTime()));
                    //calendar.add(Calendar.SECOND, 1);
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

}