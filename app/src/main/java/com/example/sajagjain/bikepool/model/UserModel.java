package com.example.sajagjain.bikepool.model;

import java.io.Serializable;

/**
 * Created by sajag jain on 02-03-2018.
 */

public class UserModel implements Serializable {
    String name,mobilenumber,drivingLicenseNumber,bikeName,bikeNumber,userId,registrationCertificate,vehicleNumber;

    public UserModel() {
    }

    public UserModel(String name, String mobilenumber, String drivingLicenseNumber, String bikeName, String bikeNumber, String userId, String registrationCertificate, String vehicleNumber) {
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.bikeName = bikeName;
        this.bikeNumber = bikeNumber;
        this.userId = userId;
        this.registrationCertificate = registrationCertificate;
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getRegistrationCertificate() {
        return registrationCertificate;
    }

    public void setRegistrationCertificate(String registrationCertificate) {
        this.registrationCertificate = registrationCertificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(String bikeNumber) {
        bikeNumber = bikeNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
