package com.example.astroweather;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText longitudeET, latitudeET, refreshRateET;
    Button saveBtn, cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();

        double longitude = bundle.getDouble("longitude");
        double latitude = bundle.getDouble("latitude");
        int refreshRate = bundle.getInt("refreshRate");

        longitudeET = findViewById(R.id.longitudeET);
        latitudeET = findViewById(R.id.latitudeET);
        refreshRateET = findViewById(R.id.refreshRateET);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        latitudeET.setText(String.valueOf(latitude));
        longitudeET.setText(String.valueOf(longitude));
        refreshRateET.setText(String.valueOf(refreshRate));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    intent.putExtra("refreshRate", Integer.parseInt(refreshRateET.getText().toString()));
                    intent.putExtra("longitude", Double.parseDouble(longitudeET.getText().toString()));
                    intent.putExtra("latitude", Double.parseDouble(latitudeET.getText().toString()));
                    startActivity(intent);
                }catch (NumberFormatException e){
                    makeToast("Niepoprawny format!");
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
