package it.unimi.di.sweng.eventfinderbot.model;

/**
 * Created by istopped on 02/06/16.
 */
public class Location {

    private double longitude;
    private double latitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
