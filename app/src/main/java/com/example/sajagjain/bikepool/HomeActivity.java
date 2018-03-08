package com.example.sajagjain.bikepool;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sajagjain.bikepool.model.RequestModel;
import com.example.sajagjain.bikepool.model.UserModel;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Double sourceLat,sourceLng,destinationLat,destinationLng;
    String sourceAddress,destinationAddress;

    private DatabaseReference mDatabase;

    TextView source,destination,dateValue;
    TextView modeText;
    Button driverToggle,passengerToggle,generateAlert;
    Switch dailyRouteSwitch;
    RelativeLayout containerMain;

    int height;
    DrawerLayout dr;
    NavigationView nv;
    View navHeader;
    LinearLayout navHeaderBackground;
    TextView appUserEmailID,appUserName;
    ImageView appUserImageView;

    RequestModel requestModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference("drivers");

        height=getWindowManager().getDefaultDisplay().getHeight();
        dr=(DrawerLayout)findViewById(R.id.drawer_layout);
        nv=(NavigationView)findViewById(R.id.nav_view);
        navHeader=nv.getHeaderView(0);
        navHeaderBackground=(LinearLayout)navHeader.findViewById(R.id.nav_view_background);
        appUserEmailID=(TextView)navHeader.findViewById(R.id.app_user_email);
        appUserName=(TextView)navHeader.findViewById(R.id.app_user_name);
        appUserImageView=(ImageView)navHeader.findViewById(R.id.app_user_profile_picture);


        appUserEmailID.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        appUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Linking Views
        source=findViewById(R.id.content_main_source);
        destination=findViewById(R.id.content_main_destination);
        dateValue=findViewById(R.id.content_main_date);
        modeText=findViewById(R.id.content_main_mode_text);
        driverToggle=findViewById(R.id.content_main_mode_driver_toggle);
        passengerToggle=findViewById(R.id.content_main_mode_passenger_toggle);
        generateAlert=findViewById(R.id.content_main_generate_alert);
        dailyRouteSwitch=findViewById(R.id.content_main_daily_route_switch);
        containerMain=findViewById(R.id.content_main_relative_layout);

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source.setBackground(getResources().getDrawable(R.drawable.rounded_border_activated));
                try{
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setCountry("IN")
                            .build();
                    Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(HomeActivity.this);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivityForResult(intent, 16);
                } catch (GooglePlayServicesRepairableException e) {

                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination.setBackground(getResources().getDrawable(R.drawable.rounded_border_activated));
                try{
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setCountry("IN")
                            .build();
                    Intent intent=new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(HomeActivity.this);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivityForResult(intent, 17);
                } catch (GooglePlayServicesRepairableException e) {

                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        dateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateValue.setBackground(getResources().getDrawable(R.drawable.rounded_border_activated));
                new SingleDateAndTimePickerDialog.Builder(HomeActivity.this)
                        .bottomSheet()
                        //.curved()
                        .minutesStep(5)
                        .mustBeOnFuture()
                        .titleTextColor(getResources().getColor(R.color.colorPrimary))
                        .mainColor(getResources().getColor(R.color.colorPrimary))
                        //.displayHours(false)
                        //.displayMinutes(false)

                        //.todayText("aujourd'hui")

                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                //retrieve the SingleDateAndTimePicker
                            }
                        })

                        .title("Pick Date and Time")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                dateValue.setText(date.toString());
                            }
                        }).display();
            }
        });

        passengerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeText.setText("Passenger");
                passengerToggle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                driverToggle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        driverToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = mDatabase;
                ValueEventListener valueEventListener = new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        int flag=0;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            //TODO get the data here
                            UserModel model=(UserModel)postSnapshot.getValue(UserModel.class);
                            if(model.getUserId().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                flag=1;
                            }

                        }
                        if(flag==1) {
                            modeText.setText("Driver");
                            driverToggle.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            passengerToggle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }else{
                            Snackbar.make(containerMain,"Please complete the driver form to be a Driver",Snackbar.LENGTH_LONG)
                                    .setAction("Fill Info", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(HomeActivity.this,DriverFormWindow.class));
                                        }
                                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                };
                query.addValueEventListener(valueEventListener);


            }
        });

        generateAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestModel=new RequestModel();

                String sourceName,destinationName,dateString=null,mode;
                Date travelDateSelected=null;
                sourceName=source.getText().toString();
                destinationName=destination.getText().toString();
                SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
                dateString=dateValue.getText().toString();
                if(dateString!=null){
                    try {
                        travelDateSelected = (input.parse(dateString));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(HomeActivity.this,"Please Enter Date",Toast.LENGTH_LONG).show();
                }
                mode=modeText.getText().toString().trim();
                Date requestCreationTimeStamp=new Date();
                String createdByUserId=FirebaseAuth.getInstance().getUid();
                String createdByUserMobileNumber=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                String createdByUserName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                boolean expired=false;

                if(sourceName.trim().isEmpty()){
                    Toast.makeText(HomeActivity.this,"Please Enter Source",Toast.LENGTH_LONG).show();
                }else if(destinationName.trim().isEmpty()){
                    Toast.makeText(HomeActivity.this,"Please Enter Destination",Toast.LENGTH_LONG).show();
                }else if(travelDateSelected.before(new Date())){
                    Toast.makeText(HomeActivity.this,"Please Enter Future Date",Toast.LENGTH_LONG).show();
                }else if(dateString.isEmpty()){
                    Toast.makeText(HomeActivity.this,"Please Enter Date",Toast.LENGTH_LONG).show();
                }else if(!(mode.equalsIgnoreCase("Passenger")||mode.equalsIgnoreCase("Driver"))){
                    Toast.makeText(HomeActivity.this,"Please Choose Mode",Toast.LENGTH_LONG).show();
                }else{
                    requestModel.setMode(mode);
                    requestModel.setCreatedByUserId(createdByUserId);
                    requestModel.setCreatedByUserMobileNumber(createdByUserMobileNumber);
                    requestModel.setCreatedByUserName(createdByUserName);
                    requestModel.setRequestCreationTimeStamp(requestCreationTimeStamp);
                    requestModel.setDestinationAddress(destinationAddress);
                    requestModel.setSourceAddress(sourceAddress);
                    requestModel.setExpired(expired);
                    requestModel.setTravelDate(travelDateSelected);
                    requestModel.setSourceLat(sourceLat);
                    requestModel.setSourceLng(sourceLng);
                    requestModel.setDestinationLat(destinationLat);
                    requestModel.setDestinationLng(destinationLng);
                    requestModel.setSourceName(sourceName);
                    requestModel.setDestinationName(destinationName);
                    String key=mDatabase.push().getKey();
                    requestModel.setRequestId(key);
                    DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("requests");
                    mDatabase.child(key).setValue(requestModel);
                    Toast.makeText(HomeActivity.this,"Added",Toast.LENGTH_LONG).show();

                    source.setText("");
                    destination.setText("");
                    dateValue.setText(new Date().toString());
                    modeText.setText("Choose Mode");
                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 16) {
            if (resultCode == RESULT_OK) {
                Dialog dialog=new Dialog(HomeActivity.this);
                dialog.setTitle("loading");
                dialog.show();
                Place place = PlaceAutocomplete.getPlace(this, data);
                String placeName=place.getName().toString();
                float placeRating=place.getRating();
                String placeAddress=place.getAddress().toString();
                LatLng placeLatLng=place.getLatLng();
                String placeId=place.getId();
                source.setText(placeName);
                sourceLat=place.getLatLng().latitude;
                sourceLng=place.getLatLng().longitude;
                sourceAddress=place.getAddress().toString();

                dialog.dismiss();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                source.setBackground(getDrawable(R.drawable.rounded_border_color_dark));
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(HomeActivity.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                source.setBackground(getDrawable(R.drawable.rounded_border_color_dark));
                Toast.makeText(HomeActivity.this,"User Cancelled the Operation",Toast.LENGTH_LONG).show();
            }

        }
        if (requestCode == 17) {
            if (resultCode == RESULT_OK) {
                Dialog dialog=new Dialog(HomeActivity.this);
                dialog.setTitle("loading");
                dialog.show();
                Place place = PlaceAutocomplete.getPlace(this, data);
                String placeName=place.getName().toString();
                float placeRating=place.getRating();
                String placeAddress=place.getAddress().toString();
                LatLng placeLatLng=place.getLatLng();
                String placeId=place.getId();
                Toast.makeText(HomeActivity.this,"Opening "+placeName ,Toast.LENGTH_LONG).show();
                destination.setText(placeName);
                destinationLat=place.getLatLng().latitude;
                destinationLng=place.getLatLng().longitude;
                destinationAddress=place.getAddress().toString();

                dialog.dismiss();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                destination.setBackground(getDrawable(R.drawable.rounded_border_color_dark));
                Toast.makeText(HomeActivity.this,status.getStatusMessage(),Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                destination.setBackground(getDrawable(R.drawable.rounded_border_color_dark));
                Toast.makeText(HomeActivity.this,"User Cancelled the Operation",Toast.LENGTH_LONG).show();
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this,IntroductionActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(HomeActivity.this,PassengerMode.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(HomeActivity.this,DriverMode.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(HomeActivity.this,MyRidesAsPassenger.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(HomeActivity.this,MyRidesAsDriver.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
