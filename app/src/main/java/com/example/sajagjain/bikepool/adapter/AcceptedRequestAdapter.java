package com.example.sajagjain.bikepool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sajagjain.bikepool.R;
import com.example.sajagjain.bikepool.model.AcceptedRequestModel;
import com.example.sajagjain.bikepool.model.RequestModel;

import java.util.List;

/**
 * Created by sajag jain on 03-03-2018.
 */

public class AcceptedRequestAdapter extends RecyclerView.Adapter<AcceptedRequestAdapter.RequestViewHolder> {

    private List<AcceptedRequestModel> requests;
    private int rowLayout;
    private Context context;
    private RequestAdapterListener listener;


    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        LinearLayout requestLayout;
        TextView requestSource;
        TextView requestDestination;
        TextView requestDated;
        TextView requestSourceAddress;
        TextView requestDestinationAddress;
        Button seeDetails;

        public RequestViewHolder(View v) {
            super(v);
            requestLayout = v.findViewById(R.id.list_item_request_linear_layout);
            requestSource = v.findViewById(R.id.list_item_request_source);
            requestDestination = v.findViewById(R.id.list_item_request_destination);
            requestDated=v.findViewById(R.id.list_item_request_date);
            seeDetails=v.findViewById(R.id.list_item_request_see_details_button);
            requestSourceAddress=v.findViewById(R.id.list_item_request_source_address);
            requestDestinationAddress=v.findViewById(R.id.list_item_request_destination_address);
        }
    }

    public AcceptedRequestAdapter(List<AcceptedRequestModel> requests, int rowLayout, Context context, RequestAdapterListener listener) {
        this.requests = requests;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public AcceptedRequestAdapter.RequestViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AcceptedRequestAdapter.RequestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RequestViewHolder holder, final int position) {

        holder.requestSource.setText(requests.get(position).getModel().getSourceName());
        holder.requestDestination.setText(requests.get(position).getModel().getDestinationName());
        holder.requestDated.setText(requests.get(position).getModel().getTravelDate().toString());
        holder.requestSourceAddress.setText(requests.get(position).getModel().getSourceAddress());
        holder.requestDestinationAddress.setText(requests.get(position).getModel().getDestinationAddress());
        applyClickEvents(holder,position);
    }

    private void applyClickEvents(RequestViewHolder holder, final int position) {

        holder.requestSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.requestDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.requestDated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position,view);
            }
        });
        holder.seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onViewDetailsButtonClick(position,view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return requests.size();
    }

    public interface RequestAdapterListener{
        void onViewDetailsButtonClick(int position, View view);
        void onEverythingElseClicked(int position, View view);
    }
}
