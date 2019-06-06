package com.example.serly.ghtoll.models;

public class ReceiptModel {

    private String transactionref;
    private String vehiclelicense;
    private String Transactionid;
    private Long timeStamp;
    private String Amount;
    private String MobileNumber;
    private String Status;
    private String userId;
    private String moneyNetwork;
    private String userImage;
    private String qrCode;


    public ReceiptModel(String transactionref, String vehiclelicense, String transactionid, String amount, String mobileNumber, String status, String moneyNetwork) {
        this.transactionref = transactionref;
        this.vehiclelicense = vehiclelicense;
        Transactionid = transactionid;
        Amount = amount;
        MobileNumber = mobileNumber;
        Status = status;
        this.moneyNetwork = moneyNetwork;
    }


    public ReceiptModel(String transactionref, String vehiclelicense, String amount, String mobileNumber, String status, String moneyNetwork) {
        this.transactionref = transactionref;
        this.vehiclelicense = vehiclelicense;
        Amount = amount;
        MobileNumber = mobileNumber;
        Status = status;
        this.moneyNetwork = moneyNetwork;
    }

    public ReceiptModel(String transactionref, Long timeStamp, String amount, String mobileNumber, String status, String userId, String moneyNetwork, String userImage) {
        this.transactionref = transactionref;
        this.timeStamp = timeStamp;
        Amount = amount;
        MobileNumber = mobileNumber;
        Status = status;
        this.userId = userId;
        this.moneyNetwork = moneyNetwork;
        this.userImage = userImage;
    }


    public ReceiptModel(String transactionref, Long timeStamp, String Amount, String mobileNumber, String status, String userId, String moneyNetwork) {
        this.transactionref = transactionref;
        this.timeStamp = timeStamp;
        this.Amount = Amount;
        MobileNumber = mobileNumber;
        Status = status;
        this.userId = userId;
        this.moneyNetwork = moneyNetwork;
    }


    public ReceiptModel() {
    }

    public String getTransactionref() {
        return transactionref;
    }

    public void setTransactionref(String transactionref) {
        this.transactionref = transactionref;
    }

    public String getVehiclelicense() {
        return vehiclelicense;
    }

    public void setVehiclelicense(String vehiclelicense) {
        this.vehiclelicense = vehiclelicense;
    }

    public String getTransactionid() {
        return Transactionid;
    }

    public void setTransactionid(String transactionid) {
        Transactionid = transactionid;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoneyNetwork() {
        return moneyNetwork;
    }

    public void setMoneyNetwork(String moneyNetwork) {
        this.moneyNetwork = moneyNetwork;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}


