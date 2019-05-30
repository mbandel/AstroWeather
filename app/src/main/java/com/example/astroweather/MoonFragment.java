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

public class MoonFragment extends Fragment {

    AstroCalculator calculator;
    TextView moonriseTV, moonsetTV, fullMoonTV, newMoonTV, moonAgeTV, moonIlluminationTV;
    Handler handler;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("##.##");


    public interface OverviewFragmentActivityListener {
        void onItemSelected(String msg);
    }

    public static MoonFragment newInstance() {
        MoonFragment myFragment = new MoonFragment();
        return myFragment;
    }

    public interface MySecondFragmentActivityListener {
        void onItemSelected(String msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // przypisujemy layout do fragmentu
        View view;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.moon_fragment_layout, container, false);
        }else{
            view = inflater.inflate(R.layout.moon_fragment_layout_land, container, false);
        }


        AstroCalculator.Location loc = new AstroCalculator.Location(19.28, 51.47);
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        AstroDateTime datetime = new AstroDateTime(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND), getOffset(), mTimeZone.inDaylightTime(new Date()));
        calculator = new AstroCalculator(datetime, loc);


        initTextViews(view);
        updateView();

        return view;
    }

    public void initTextViews(View view){
        moonriseTV= view.findViewById(R.id.tvMoonriseTime);
        moonsetTV = view.findViewById(R.id.tvMoonsetTime);
        fullMoonTV = view.findViewById(R.id.tvFullMoonTime);
        newMoonTV= view.findViewById(R.id.tvNewMoonTime);
        moonAgeTV = view.findViewById(R.id.tvMoonAgeTime);
        moonIlluminationTV = view.findViewById(R.id.tvMoonIlluminationInfo);
    }

    public void updateView(){

            AstroDateTime moonrise = calculator.getMoonInfo().getMoonrise();
            Date moonriseDate = new Date(moonrise.getYear()-1900, moonrise.getMonth()-1, moonrise.getDay(), moonrise.getHour(), moonrise.getMinute(), moonrise.getSecond());
            moonriseTV.setText(timeFormat.format(moonriseDate));

            AstroDateTime moonset = calculator.getMoonInfo().getMoonset();
            Date moonsetDate = new Date(moonset.getYear()-1900, moonset.getMonth()-1, moonset.getDay(), moonset.getHour(), moonset.getMinute(), moonset.getSecond());
            moonsetTV.setText(timeFormat.format(moonsetDate));

            AstroDateTime fullMoon = calculator.getMoonInfo().getNextFullMoon();
            Date fullMoonDate = new Date(fullMoon.getYear()-1900, fullMoon.getMonth()-1, fullMoon.getDay(), fullMoon.getHour(), fullMoon.getMinute(), fullMoon.getSecond());
            fullMoonTV.setText(dateFormat.format(fullMoonDate));

            AstroDateTime newMoon = calculator.getMoonInfo().getNextNewMoon();
            Date newMoonDate = new Date(newMoon.getYear()-1900, newMoon.getMonth()-1, newMoon.getDay(), newMoon.getHour(), newMoon.getMinute(), newMoon.getSecond());
            newMoonTV.setText(dateFormat.format(newMoonDate));

            moonAgeTV.setText(decimalFormat.format(calculator.getMoonInfo().getAge())+"dni");
            moonIlluminationTV.setText(decimalFormat.format(calculator.getMoonInfo().getIllumination()*100)+"%");

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
