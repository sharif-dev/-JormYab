package com.example.jormyab.datatypes;

import java.util.Date;

public class CrimeDensity implements AddableToMap {

    public static final int COLOR_NONE = 0;
    public static final int COLOR_YELLOW = 1;
    public static final int COLOR_ORANGE = 2;
    public static final int COLOR_RED = 3;
    public static final int COLOR_BLACK = 4;

    private double longitude;
    private double latitude;
    private double radius;
    private int color;

    public CrimeDensity() {
//        this.longitude = crime.getLongitude();
//        this.latitude = crime.getLatitude();
        /** to calculate appropriate amount of radius for**/
    }

    public CrimeDensity(CrimeDensity[] crimes){

    }


    private void calculateMeanCoordinates(){
        /*** to calculate appropriate amount of coordinates for ***/
    }

    private void calculateMeanColor(){
        /*** to calculate appropriate color to show in map ***/
    }

    @Override
    public void add() {

    }
}
