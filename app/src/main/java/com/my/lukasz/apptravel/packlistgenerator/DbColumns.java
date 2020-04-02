package com.my.lukasz.apptravel.packlistgenerator;

public class DbColumns {

    private int numberOfDays;
    private int travelTypeId;
    private int transportTypeId;
    private int weatherTypeId;
    private String gender;
    private int age;

    public DbColumns(int numberOfDays, int travelTypeId, int transportTypeId, int weatherTypeId, String gender, int age) {
        this.numberOfDays = numberOfDays;
        this.travelTypeId = travelTypeId;
        this.transportTypeId = transportTypeId;
        this.weatherTypeId = weatherTypeId;
        this.gender = gender;
        this.age = age;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getTravelTypeId() {
        return travelTypeId;
    }

    public void setTravelTypeId(int travelTypeId) {
        this.travelTypeId = travelTypeId;
    }

    public int getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(int transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public int getWeatherTypeId() {
        return weatherTypeId;
    }

    public void setWeatherTypeId(int weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
