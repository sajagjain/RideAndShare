package com.example.sajagjain.bikepool.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sajag jain on 02-03-2018.
 */

public class RequestModel implements Serializable{

    String requestId;
    Double sourceLat,SourceLng,DestinationLat,DestinationLng;
    Date travelDate;
    String sourceAddress,destinationAddress;
    String sourceName,DestinationName;
    Date requestCreationTimeStamp;
    String createdByUserId;
    String createdByUserMobileNumber;
    String createdByUserName;
    boolean expired;
    String mode;
    DailyRoute route;

    public RequestModel() {
    }

    public RequestModel(String requestId, Double sourceLat, Double sourceLng, Double destinationLat, Double destinationLng, Date travelDate, String sourceAddress, String destinationAddress, String sourceName, String destinationName, Date requestCreationTimeStamp, String createdByUserId, String createdByUserMobileNumber, String createdByUserName, boolean expired, String mode, DailyRoute route) {
        this.requestId = requestId;
        this.sourceLat = sourceLat;
        SourceLng = sourceLng;
        DestinationLat = destinationLat;
        DestinationLng = destinationLng;
        this.travelDate = travelDate;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.sourceName = sourceName;
        DestinationName = destinationName;
        this.requestCreationTimeStamp = requestCreationTimeStamp;
        this.createdByUserId = createdByUserId;
        this.createdByUserMobileNumber = createdByUserMobileNumber;
        this.createdByUserName = createdByUserName;
        this.expired = expired;
        this.mode = mode;
        this.route = route;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationName() {
        return DestinationName;
    }

    public void setDestinationName(String destinationName) {
        DestinationName = destinationName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(Double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public Double getSourceLng() {
        return SourceLng;
    }

    public void setSourceLng(Double sourceLng) {
        SourceLng = sourceLng;
    }

    public Double getDestinationLat() {
        return DestinationLat;
    }

    public void setDestinationLat(Double destinationLat) {
        DestinationLat = destinationLat;
    }

    public Double getDestinationLng() {
        return DestinationLng;
    }

    public void setDestinationLng(Double destinationLng) {
        DestinationLng = destinationLng;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Date getRequestCreationTimeStamp() {
        return requestCreationTimeStamp;
    }

    public void setRequestCreationTimeStamp(Date requestCreationTimeStamp) {
        this.requestCreationTimeStamp = requestCreationTimeStamp;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUserMobileNumber() {
        return createdByUserMobileNumber;
    }

    public void setCreatedByUserMobileNumber(String createdByUserMobileNumber) {
        this.createdByUserMobileNumber = createdByUserMobileNumber;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public DailyRoute getRoute() {
        return route;
    }

    public void setRoute(DailyRoute route) {
        this.route = route;
    }
}
