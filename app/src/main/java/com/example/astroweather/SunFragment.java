package com.example.astroweather;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Date;

public class SunFragment extends Fragment {

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private GregorianCalendar calendar = new GregorianCalendar();
    DecimalFormat decimalFormat = new DecimalFormat("##.#####");
    TextView sunriseTime, sunsetTime;
    TextView sunriseAzimuth, sunsetAzimuth;
    TextView twillightEvening, twillightMorning;
    AstroCalculator calculator;
    int refreshRate=5;
    Handler handler;

    public static SunFragment newInstance() {
        SunFragment myFragment = new SunFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // przypisujemy layout do fragmentu
        View view;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
             view = inflater.inflate(R.layout.sun_fragment_layout, container, false);
        }else{
            view = inflater.inflate(R.layout.sun_fragment_layout_land, container, false);
        }


       if (sunriseTime==null && sunsetTime==null && sunsetAzimuth==null && sunriseAzimuth==null && twillightMorning==null && twillightMorning==null){
           initTextViews(view);
       }

        AstroCalculator.Location loc = new AstroCalculator.Location(51.0, 19.0);
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        AstroDateTime datetime = new AstroDateTime(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND), getOffset(), mTimeZone.inDaylightTime(new Date()));
        calculator = new AstroCalculator(datetime, loc);

        handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                updateView();
                handler.postDelayed(this, refreshRate*1000);
            }
        };

        update.run();

        return view;
    }


    public void initTextViews(View view){
        sunriseTime = view.findViewById(R.id.tvSunrisetTime);
        sunsetTime = view.findViewById(R.id.tvSunsetTime);
        sunriseAzimuth = view.findViewById(R.id.tvSunriseAzimuth);
        sunsetAzimuth = view.findViewById(R.id.tvSunsetAzimuth);
        twillightEvening = view.findViewById(R.id.tvTwilligtEveningTime);
        twillightMorning = view.findViewById(R.id.tvTwilligtMorningTime);
    }

    public void updateView(){
       //wschod
        AstroDateTime sunrise = calculator.getSunInfo().getSunrise();
        Date sunriseDate = new Date(sunrise.getYear(), sunrise.getMonth(), sunrise.getDay(), sunrise.getHour(), sunrise.getMinute(), sunrise.getSecond());
        sunriseTime.setText(dateFormat.format(sunriseDate));
        sunriseAzimuth.setText(String.valueOf(decimalFormat.format(calculator.getSunInfo().getAzimuthRise())));

        //zachod
        AstroDateTime sunset = calculator.getSunInfo().getSunset();
        Date sunsetDate = new Date(sunset.getYear(), sunset.getMonth(), sunset.getDay(), sunset.getHour(), sunset.getMinute(), sunset.getSecond());
        sunsetTime.setText(dateFormat.format(sunsetDate));
        sunsetAzimuth.setText(String.valueOf(decimalFormat.format(calculator.getSunInfo().getAzimuthSet())));

        //swit cywilny
        AstroDateTime evening = calculator.getSunInfo().getTwilightEvening();
        Date eveningDate = new Date(evening.getYear(), evening.getMonth(), evening.getDay(), evening.getHour(), evening.getMinute(), evening.getSecond());
        twillightEvening.setText(dateFormat.format(eveningDate));

        //zmierzch cywilny
        AstroDateTime morning = calculator.getSunInfo().getTwilightMorning();
        Date morningDate = new Date(morning.getYear(), morning.getMonth(), morning.getDay(), morning.getHour(), morning.getMinute(), morning.getSecond());
        twillightMorning.setText(dateFormat.format(morningDate));
    }

    public int getOffset(){
        TimeZone timezone = TimeZone.getDefault();
        int seconds = timezone.getOffset(Calendar.ZONE_OFFSET)/1000;
        double minutes = seconds/60;
        double hours = minutes/60;
        int finalHours = (int) hours;
        return finalHours;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt("someInt", 100);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("fragmnet", "onActivityCreated");
    }
}
