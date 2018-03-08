package com.example.sajagjain.bikepool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sajagjain.bikepool.adapter.RequestAdapter;
import com.example.sajagjain.bikepool.model.RequestModel;
import com.example.sajagjain.bikepool.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DriverMode extends AppCompatActivity implements RequestAdapter.RequestAdapterListener {

    DatabaseReference mDatabase,mDatabaseForUser;
    LinearLayout mainContainer;
    List<RequestModel> requestsDriverMode = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);

        mainContainer=findViewById(R.id.linear_layout_main_container);

        mDatabase = FirebaseDatabase.getInstance().getReference("requests");
        mDatabaseForUser=FirebaseDatabase.getInstance().getReference("drivers");

        Query query = mDatabase;
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int flag = 0;
                List<RequestModel> requests1 = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //TODO get the data here
                    RequestModel model = (RequestModel) postSnapshot.getValue(RequestModel.class);
                    requests1.add(model);
                }
                checkListForDeadValues(requests1);
//                requests.addAll(requests1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);


    }

    private void checkListForDeadValues(List<RequestModel> list) {

//        for(int i=0;i<list.size();i++){
//            RequestModel model=list.get(i);
//            Date date1=model.getTravelDate();
//            Date date2=new Date();
//            int val=date1.compareTo(date2);
//            if(val<0){
//                RequestModel model1=list.remove(i);
//
//            }
//        }
        Iterator<RequestModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            RequestModel model=iterator.next();
            Date date1=model.getTravelDate();
            Date date2=new Date();
            int val=date1.compareTo(date2);
            if(val<0){
                iterator.remove();
            }
        }
        if(list.size()==1){
            Date date1=list.get(0).getTravelDate();
            Date date2=new Date();
            if(date1.before(date2)){
                list=new ArrayList<>();
            }
        }

        checkListForDriversOnly(list);
    }

    private void checkListForDriversOnly(List<RequestModel> list){

//        for(int i=0;i<list.size();i++){
//            RequestModel model=list.get(i);
//            String mode=model.getMode();
//            Toast.makeText(DriverMode.this,mode+" "+i,Toast.LENGTH_LONG).show();
//            if(list.get(i).getMode().trim().equalsIgnoreCase("Passenger")){
//                list.remove(i);
//            }
//        }
        Iterator<RequestModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            RequestModel next = iterator.next();
            String mode=next.getMode();
            if(mode.equalsIgnoreCase("Passenger")){
                iterator.remove();
            }
        }
        if(list.size()==1){
            RequestModel model=list.get(0);
            String mode=model.getMode();
            if(mode.equalsIgnoreCase("Passenger")){
                list=new ArrayList<>();
            }
        }

        setAdapterForToday(list);
    }

    private void setAdapterForToday(List<RequestModel> list) {


        Collections.sort(list, new Comparator<RequestModel>() {
            public int compare(RequestModel o1, RequestModel o2) {
                if (o1.getTravelDate() == null || o2.getTravelDate() == null)
                    return 0;
                return o1.getTravelDate().compareTo(o2.getTravelDate());
            }
        });
//        Collections.reverse(list);

        RecyclerView todayRecyclerView = (RecyclerView) findViewById(R.id.driver_mode_for_today);
        todayRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));
        requestsDriverMode=new ArrayList<>();
        requestsDriverMode.addAll(list);
        todayRecyclerView.setAdapter(new RequestAdapter(requestsDriverMode, R.layout.list_item_request, getApplicationContext(), DriverMode.this));
    }


    @Override
    public void onViewDetailsButtonClick(final int position, View view) {
        Query query = mDatabaseForUser;
        ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                UserModel model=null;
                int flag=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //TODO get the data here
                    UserModel model1=(UserModel)postSnapshot.getValue(UserModel.class);
                    if(model1.getUserId().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        model=model1;
                        flag=1;
                    }

                }
                if(flag==1) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("request", requestsDriverMode.get(position));
                    bundle.putSerializable("user",model);
                    startActivity(new Intent(DriverMode.this,SeeDetailOfRequestDriver.class).putExtras(bundle));


                }else{
                    Snackbar.make(mainContainer,"Please complete the driver form to be a Driver",Snackbar.LENGTH_LONG)
                            .setAction("Fill Info", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(DriverMode.this,DriverFormWindow.class));
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

    @Override
    public void onEverythingElseClicked(int position, View view) {

    }


    public static class MyObject implements Comparable<PassengerMode.MyObject> {

        private Date dateTime;

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date datetime) {
            this.dateTime = datetime;
        }

        @Override
        public int compareTo(PassengerMode.MyObject o) {
            if (getDateTime() == null || o.getDateTime() == null)
                return 0;
            return getDateTime().compareTo(o.getDateTime());
        }
    }
}

