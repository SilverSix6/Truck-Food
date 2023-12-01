package com.example.truck_food.View;

public class Maps {

    private static final int EARTH_RADIUS = 6371;

    //
    // Calculates the lat lon distance using haversine formula
    //
    public static double distanceLatLong(double lat1, double lon1, double lat2, double lon2) {
        double dlat = Math.toRadians((lat2 - lat1));
        double dlon = Math.toRadians((lon2 - lon1));

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = haversine(dlat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dlon);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
    }

    private static double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
