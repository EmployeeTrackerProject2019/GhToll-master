package com.example.serly.ghtoll.models;

public class Vehicle {
   private String userName;
    private Long timeStamp;
    private String vehicleChasisNumber;
    private String userImage;
    private String vehicleNumber;
    private String vehicleLicenseNumber;
    private String userId;

    public Vehicle() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVehicleChasisNumber() {
        return vehicleChasisNumber;
    }

    public void setVehicleChasisNumber(String vehicleChasisNumber) {
        this.vehicleChasisNumber = vehicleChasisNumber;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleLicenseNumber() {
        return vehicleLicenseNumber;
    }

    public void setVehicleLicenseNumber(String vehicleLicenseNumber) {
        this.vehicleLicenseNumber = vehicleLicenseNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Vehicle(String userName, Long timeStamp, String vehicleChasisNuber, String userImage, String vehicleNumber, String vehicleLicenseNumber, String userId) {
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.vehicleChasisNumber = vehicleChasisNuber;
        this.userImage = userImage;
        this.vehicleNumber = vehicleNumber;
        this.vehicleLicenseNumber = vehicleLicenseNumber;
        this.userId = userId;
    }
}
