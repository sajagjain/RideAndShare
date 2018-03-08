package com.example.sajagjain.bikepool;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.sajagjain.bikepool.adapter.AcceptedRequestAdapter;
import com.example.sajagjain.bikepool.adapter.RequestAdapter;
import com.example.sajagjain.bikepool.model.AcceptedRequestModel;
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

public class MyRidesAsPassenger extends AppCompatActivity implements AcceptedRequestAdapter.RequestAdapterListener {

    DatabaseReference mDatabase;
    List<AcceptedRequestModel> requests=new ArrayList<>();
    ConstraintLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides_as_passenger);

        mainContainer=findViewById(R.id.contraint_layout_activity_my_rides_as_passenger);

        //Getting data from accepted request
        mDatabase=FirebaseDatabase.getInstance().getReference("acceptedrequests");
        Query query=mDatabase;
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int flag=0;
                List<AcceptedRequestModel> requests1=new ArrayList<>();
                requests.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //TODO get the data here
                    AcceptedRequestModel model1=postSnapshot.getValue(AcceptedRequestModel.class);
                    if(model1.getPassengersUserId().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                        requests.add(model1);
                        requests1.add(model1);
                    }

                }
                checkListForDeadValues(requests1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(listener);

        //Setting data to recycler view


    }

    private void checkListForDeadValues(List<AcceptedRequestModel> list) {

//        for(int i=0;i<list.size();i++){
//            AcceptedRequestModel model=list.get(i);
//            Date date1=model.getModel().getTravelDate();
//            Date date2=new Date();
//            int val=date1.compareTo(date2);
//            if(val<0){
//                AcceptedRequestModel model1=list.remove(i);
//            }
//        }
        Iterator<AcceptedRequestModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            AcceptedRequestModel model=iterator.next();
            Date date1=model.getModel().getTravelDate();
            Date date2=new Date();
            int val=date1.compareTo(date2);
            if(val<0){
                iterator.remove();
            }
        }
        if(list.size()==1){
            Date date1=list.get(0).getModel().getTravelDate();
            Date date2=new Date();
            if(date1.before(date2)){
                list.clear();
            }
        }
        setAdapterForToday(list);
    }


    private void setAdapterForToday(List<AcceptedRequestModel> list) {


        Collections.sort(list, new Comparator<AcceptedRequestModel>() {
            public int compare(AcceptedRequestModel o1, AcceptedRequestModel o2) {
                if (o1.getModel().getRequestCreationTimeStamp() == null || o2.getModel().getRequestCreationTimeStamp() == null)
                    return 0;
                return o1.getModel().getRequestCreationTimeStamp().compareTo(o2.getModel().getRequestCreationTimeStamp());
            }
        });
//        Collections.reverse(list);

        RecyclerView todayRecyclerView = (RecyclerView) findViewById(R.id.my_rides_as_passenger_recycler_view);
        todayRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));
        requests=new ArrayList<>();
        requests.addAll(list);
        todayRecyclerView.setAdapter(new AcceptedRequestAdapter(requests, R.layout.list_item_request, getApplicationContext(), MyRidesAsPassenger.this));
    }

    @Override
    public void onViewDetailsButtonClick(final int position, View view) {
        Query query = FirebaseDatabase.getInstance().getReference("drivers");
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
                    if(model1.getUserId().equalsIgnoreCase(requests.get(position).getDriversUserId())) {
                        model=model1;
                        flag=1;
                    }

                }
                if(flag==1) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("request", requests.get(position));
                    bundle.putSerializable("user",model);
                    startActivity(new Intent(MyRidesAsPassenger.this,SeeDetailsOfMyRidesPassenger.class).putExtras(bundle));


                }else{
                    Snackbar.make(mainContainer,"Please complete the driver form to be a Driver",Snackbar.LENGTH_LONG)
                            .setAction("Fill Info", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(MyRidesAsPassenger.this,DriverFormWindow.class));
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

    public static class MyObject implements Comparable<MyObject> {

        private Date dateTime;

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date datetime) {
            this.dateTime = datetime;
        }

        @Override
        public int compareTo(MyObject o) {
            if (getDateTime() == null || o.getDateTime() == null)
                return 0;
            return getDateTime().compareTo(o.getDateTime());
        }
    }




}
