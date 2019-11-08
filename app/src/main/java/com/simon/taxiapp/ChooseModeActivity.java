package com.simon.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseModeActivity extends AppCompatActivity implements View.OnClickListener {

     Button passengerButton;
     Button driverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        passengerButton = findViewById(R.id.passengerButton);
        driverButton = findViewById(R.id.driverButton);

        passengerButton.setOnClickListener(this);
        driverButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.passengerButton:
                startActivity(new Intent(ChooseModeActivity.this, PassengerActivity.class));
                break;
            case R.id.driverButton:
                startActivity(new Intent(ChooseModeActivity.this, DriverActivity.class));
        }
    }
}



