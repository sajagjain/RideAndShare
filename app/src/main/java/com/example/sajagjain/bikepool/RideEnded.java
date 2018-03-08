package com.example.sajagjain.bikepool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RideEnded extends AppCompatActivity {

    TextView cost,driverMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_ended);

        cost=findViewById(R.id.ride_ended_cost);
        driverMobileNumber=findViewById(R.id.ride_ended_driver_mobile_number);

        String costOfRide=getIntent().getStringExtra("cost");
        String mobileNumberOfDriver=getIntent().getStringExtra("driverphonenumber");

        cost.setText(costOfRide);
        driverMobileNumber.setText(mobileNumberOfDriver);

        driverMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(driverMobileNumber.getText().toString(),driverMobileNumber.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(RideEnded.this,"Copied to Clipboard",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
