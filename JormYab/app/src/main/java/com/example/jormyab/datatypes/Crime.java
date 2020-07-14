package com.example.jormyab.datatypes;


import android.graphics.Picture;

import java.util.Date;

public class Crime {
    public static final int CRIME_TYPE_MOTOR_ROBBERY = 1;
    public static final int CRIME_TYPE_ROBBERY_FROM_HOUSE = 2;
    public static final int CRIME_TYPE_MURDER = 3;
    public static  final int CRIME_TYPE_OTHER_VIOLATIONS = 4;

    private double longitude;
    private double latitude;
    private int CrimeType;
    private int id; /*** id of person whom reported this crime ***/
    private Date timeOfCrime;

    /*** maybe pictureOfCrime and other properties ***/


    public Crime(double longitude, double latitude, int crimeType, int id, Date timeOfCrime) {
        this.longitude = longitude;
        this.latitude = latitude;
        CrimeType = crimeType;
        this.id = id;
        this.timeOfCrime = timeOfCrime;
    }


    public static int getCrimeTypeMotorRobbery() {
        return CRIME_TYPE_MOTOR_ROBBERY;
    }

    public static int getCrimeTypeRobberyFromHouse() {
        return CRIME_TYPE_ROBBERY_FROM_HOUSE;
    }

    public static int getCrimeTypeMurder() {
        return CRIME_TYPE_MURDER;
    }

    public static int getCrimeTypeOtherViolations() {
        return CRIME_TYPE_OTHER_VIOLATIONS;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getCrimeType() {
        return CrimeType;
    }

    public void setCrimeType(int crimeType) {
        CrimeType = crimeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeOfCrime() {
        return timeOfCrime;
    }

    public void setTimeOfCrime(Date timeOfCrime) {
        this.timeOfCrime = timeOfCrime;
    }
}
