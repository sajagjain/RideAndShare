package com.example.sajagjain.bikepool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sajagjain.bikepool.model.AcceptedRequestModel;
import com.example.sajagjain.bikepool.model.RequestModel;
import com.example.sajagjain.bikepool.model.UserModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeeDetailOfRequestDriver extends AppCompatActivity implements OnMapReadyCallback {

    TextView requestPassengerName;
    TextView requestPassengerMobileNumber;
    TextView requestSource;
    TextView requestDestination;
    TextView requestDated;
    TextView requestSourceAddress;
    TextView requestDestinationAddress;
    TextView requestBikeName,requestBikeNumber,requestBikeRC,requestUserDrivingLicense;
    TextView requestDriversCost;
    Button acceptRide;

    private GoogleMap mMap;
    private LatLng latLngSource,latLngDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_detail_of_request_driver);

        Bundle bundle = getIntent().getExtras();


        final RequestModel request=(RequestModel) bundle.getSerializable("request");
        UserModel model=(UserModel)bundle.getSerializable("user");


        //Linking Views
        requestPassengerName=findViewById(R.id.list_item_request_name);
        requestPassengerMobileNumber = findViewById(R.id.list_item_request_mobile_number);
        requestSource = findViewById(R.id.list_item_request_source);
        requestDestination = findViewById(R.id.list_item_request_destination);
        requestDated=findViewById(R.id.list_item_request_date);
        acceptRide=findViewById(R.id.list_item_request_accept_ride);
        requestSourceAddress=findViewById(R.id.list_item_request_source_address);
        requestDestinationAddress=findViewById(R.id.list_item_request_destination_address);
        requestBikeName=findViewById(R.id.list_item_request_bike_name);
        requestBikeNumber=findViewById(R.id.list_item_request_bike_numberr);
        requestBikeRC=findViewById(R.id.list_item_request_bike_rc_number);
        requestUserDrivingLicense=findViewById(R.id.list_item_request_driving_license_number);
        requestDriversCost=findViewById(R.id.request_driver_cost);

        //Setting Daata to Views
        requestPassengerName.setText(request.getCreatedByUserName());
        requestPassengerMobileNumber.setText(request.getCreatedByUserMobileNumber());
        requestSource.setText(request.getSourceName());
        requestDestination.setText(request.getDestinationName());
        requestSourceAddress.setText(request.getSourceAddress());
        requestDestinationAddress.setText(request.getDestinationAddress());
        requestDated.setText(request.getTravelDate().toString());
        requestBikeName.setText(model.getBikeName());
        requestBikeNumber.setText(model.getVehicleNumber());
        requestBikeRC.setText(model.getRegistrationCertificate());
        requestUserDrivingLicense.setText(model.getDrivingLicenseNumber());

        latLngSource=new LatLng(request.getSourceLat(),request.getSourceLng());
        latLngDestination=new LatLng(request.getDestinationLat(),request.getDestinationLng());

        Double dis=distance(latLngSource.latitude,latLngSource.longitude,latLngDestination.latitude,latLngDestination.longitude,"K");
        dis=Math.ceil(dis*4.5);
        requestDriversCost.setText(dis+"");

        Query query = FirebaseDatabase.getInstance().getReference("acceptedrequests");
        ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                AcceptedRequestModel model=null;
                int flag=0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //TODO get the data here
                    AcceptedRequestModel model1=postSnapshot.getValue(AcceptedRequestModel.class);
                    boolean one=model1.getModel().getMode().equals(request.getMode());
                    boolean two=model1.getModel().getDestinationAddress().equalsIgnoreCase(request.getDestinationAddress());
                    boolean three=model1.getModel().getSourceAddress().equalsIgnoreCase(request.getSourceAddress());
                    boolean four=(model1.getModel().getRequestCreationTimeStamp().compareTo(request.getRequestCreationTimeStamp())==0);
                    boolean five=(model1.isStartRide());
                    if(one&&two&&three&&four)
                    {
                        model=model1;
                        flag=1;
                    }

                }
                if(flag==1) {

                    acceptRide.setText("Already Accepted");
                    acceptRide.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        query.addValueEventListener(valueEventListener);


        acceptRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("acceptedrequests");
                String key=mDatabase.push().getKey();
                AcceptedRequestModel acceptedRequest=new AcceptedRequestModel(request,request.getCreatedByUserId(),FirebaseAuth.getInstance().getUid(),false,false,key);
                mDatabase.child(key).setValue(acceptedRequest);
                Toast.makeText(SeeDetailOfRequestDriver.this,"Request Accepted",Toast.LENGTH_LONG).show();
                acceptRide.setText("Already Accepted");
                acceptRide.setEnabled(false);

            }
        });


        initApp();

    }
    private void initApp(){
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    public static final int LOCATION_REQUEST = 500;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(latLngSource).title("Source"));
        mMap.addMarker(new MarkerOptions().position(latLngDestination).title("Destination"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLngSource).zoom(8.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Toast.makeText(getApplicationContext(), "Please Wait While We Create Route", Toast.LENGTH_SHORT).show();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLngSource);
                builder.include(latLngDestination);
                LatLngBounds bounds = builder.build();
                int padding = 200; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.moveCamera(cu);

                //Check Route
                List<LatLng> latLngList = new ArrayList<>();
                latLngList.add(latLngSource);
                latLngList.add(latLngDestination);
                if (latLngList.size() >= 2) {
                    String url = getRequestUrl(latLngList);
                    TaskRequestDirection taskRequestDirection = new TaskRequestDirection();
                    taskRequestDirection.execute(url);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Enough Points" + latLngList.size(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private String getRequestUrl(List<LatLng> points) {
        String str_origin = "origin=" + points.get(0).latitude + "," + points.get(0).longitude;
        String str_dest = "destination=" + points.get(points.size() - 1).latitude + "," + points.get(points.size() - 1).longitude;
        String sensor = "sensor=false";
        String mode = "mode=road";

        String params = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + params;

        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {

            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            responseString = buffer.toString();
            reader.close();
            isr.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);

        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsJSONParser djp = new DirectionsJSONParser();
                routes = djp.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> point : path) {

                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));

                    points.add(new LatLng(lat, lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.BLACK);
                polylineOptions.geodesic(true);
                if (polylineOptions != null) {
                    mMap.addPolyline(polylineOptions);
                } else {
                    Toast.makeText(getApplicationContext(), "Directions not found", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
