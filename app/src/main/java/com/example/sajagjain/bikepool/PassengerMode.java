package com.example.sajagjain.bikepool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sajagjain.bikepool.adapter.RequestAdapter;
import com.example.sajagjain.bikepool.model.RequestModel;
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

public class PassengerMode extends AppCompatActivity implements RequestAdapter.RequestAdapterListener {

    DatabaseReference mDatabase;
    List<RequestModel> requests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_mode);

        mDatabase = FirebaseDatabase.getInstance().getReference("requests");


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
                list.clear();
            }
        }
        checkListForPassengersOnly(list);
    }

    private void checkListForPassengersOnly(List<RequestModel> list){

//        for(int i=0;i<list.size();i++){
//            RequestModel model=list.get(i);
//            String mode=model.getMode();
//            if(mode.equalsIgnoreCase("Driver")){
//                list.remove(i);
//            }
//        }
        Iterator<RequestModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            RequestModel next = iterator.next();
            String mode=next.getMode();
            if(mode.equalsIgnoreCase("Driver")){
                iterator.remove();
            }
        }
        if(list.size()==1){
            RequestModel model=list.get(0);
            String mode=model.getMode();
            if(mode.equalsIgnoreCase("Driver")){
                list.clear();
            }
        }
        setAdapterForToday(list);
    }

    private void setAdapterForToday(List<RequestModel> list) {


        Collections.sort(list, new Comparator<RequestModel>() {
            public int compare(RequestModel o1, RequestModel o2) {
                if (o1.getRequestCreationTimeStamp() == null || o2.getRequestCreationTimeStamp() == null)
                    return 0;
                return o1.getRequestCreationTimeStamp().compareTo(o2.getRequestCreationTimeStamp());
            }
        });
        Collections.reverse(list);

        RecyclerView todayRecyclerView = (RecyclerView) findViewById(R.id.passenger_mode_for_today);
        todayRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this));
        requests=new ArrayList<>();
        requests.addAll(list);
        todayRecyclerView.setAdapter(new RequestAdapter(requests, R.layout.list_item_request, getApplicationContext(), PassengerMode.this));
    }


    @Override
    public void onViewDetailsButtonClick(int position, View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("request", requests.get(position));
        startActivity(new Intent(PassengerMode.this,SeeDetailOfRequestPassenger.class).putExtras(bundle));

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
