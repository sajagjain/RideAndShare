package com.example.sajagjain.bikepool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sajagjain.bikepool.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverFormWindow extends AppCompatActivity {

    EditText name,mobileNumber,drivingLicenseNumber,registrationCardNumber,modelNameNumber,vehicleNumber;
    Button registerAsDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_form_window);

        //Linking Views
        name=findViewById(R.id.driver_name);
        mobileNumber=findViewById(R.id.driver_mobile_number);
        drivingLicenseNumber=findViewById(R.id.driver_driver_license_number);
        registrationCardNumber=findViewById(R.id.driver_rc_card);
        modelNameNumber=findViewById(R.id.driver_makers_class);
        vehicleNumber=findViewById(R.id.driver_vehicle_number);
        registerAsDriver=findViewById(R.id.driver_register_as_driver);

        registerAsDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().isEmpty()){
                    name.setError("Please Enter a Valid Name");
                }else if(!checkIfMobileNumber(mobileNumber.getText().toString().trim())){
                    mobileNumber.setError("Please Enter a Valid Mobile Number");
                }else if(drivingLicenseNumber.getText().toString().trim().isEmpty()||drivingLicenseNumber.getText().toString().trim().length()<8){
                    drivingLicenseNumber.setError("Please Enter a Valid Driving License Number");
                }else if(registrationCardNumber.getText().toString().trim().isEmpty()||registrationCardNumber.getText().toString().trim().length()<5){
                    registrationCardNumber.setError("Please Enter a Valid RC Number");
                }else if(modelNameNumber.getText().toString().trim().isEmpty()){
                    modelNameNumber.setError("Please Enter a Valid Model Number");
                }else if(vehicleNumber.getText().toString().trim().isEmpty()||vehicleNumber.getText().toString().trim().length()<8){
                    vehicleNumber.setError("Please Enter a Valid Vehicle Number");
                }else{
                    DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("drivers");
                    mDatabase.child(mDatabase.push().getKey()).setValue(new UserModel(name.getText().toString()
                            ,mobileNumber.getText().toString()
                            ,drivingLicenseNumber.getText().toString()
                            ,modelNameNumber.getText().toString()
                            ,vehicleNumber.getText().toString()
                            , FirebaseAuth.getInstance().getUid()
                            ,registrationCardNumber.getText().toString()
                            ,vehicleNumber.getText().toString()));

                    Toast.makeText(DriverFormWindow.this,"Registration Complete",Toast.LENGTH_LONG).show();
                    finishAfterTransition();
                }
            }
        });





    }
    private boolean checkIfMobileNumber(String mobileNumber){
        if(TextUtils.isDigitsOnly(mobileNumber)){
            if(mobileNumber.length()==10) {
                return true;
            }
        }else{
            return false;
        }
        return false;
    }
}
