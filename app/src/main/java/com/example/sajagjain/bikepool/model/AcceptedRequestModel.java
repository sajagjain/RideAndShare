package com.example.sajagjain.bikepool.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sajag jain on 04-03-2018.
 */

public class AcceptedRequestModel implements Serializable {

    RequestModel model;
    String driversUserId;
    String passengersUserId;
    boolean startRide;
    boolean endRide;
    String acceptedRequestModelUid;

    public AcceptedRequestModel() {
    }

    public AcceptedRequestModel(RequestModel model, String driversUserId, String passengersUserId, boolean startRide, boolean endRide, String acceptedRequestModelUid) {
        this.model = model;
        this.driversUserId = driversUserId;
        this.passengersUserId = passengersUserId;
        this.startRide = startRide;
        this.endRide = endRide;
        this.acceptedRequestModelUid = acceptedRequestModelUid;
    }

    public String getAcceptedRequestModelUid() {
        return acceptedRequestModelUid;
    }

    public void setAcceptedRequestModelUid(String acceptedRequestModelUid) {
        this.acceptedRequestModelUid = acceptedRequestModelUid;
    }

    public boolean isStartRide() {
        return startRide;
    }

    public void setStartRide(boolean startRide) {
        this.startRide = startRide;
    }

    public boolean isEndRide() {
        return endRide;
    }

    public void setEndRide(boolean endRide) {
        this.endRide = endRide;
    }

    public String getDriversUserId() {
        return driversUserId;
    }

    public void setDriversUserId(String driversUserId) {
        this.driversUserId = driversUserId;
    }

    public String getPassengersUserId() {
        return passengersUserId;
    }

    public void setPassengersUserId(String passengersUserId) {
        this.passengersUserId = passengersUserId;
    }

    public RequestModel getModel() {
        return model;
    }

    public void setModel(RequestModel model) {
        this.model = model;
    }


}