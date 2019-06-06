package com.example.serly.ghtoll.models;

public class User {
    private String UserId, firstName,lastName,email,mobileNumber,fullName,occupation,about;

    public User(String userId, String firstName, String lastName, String email, String mobileNumber, String fullName) {
        UserId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
    }


    public User(String userId, String email, String mobileNumber, String fullName) {
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
    }



    public User(String userId, String firstName, String lastName, String mobileNumber, String fullName, String occupation, String about) {
        UserId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.about = about;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}

