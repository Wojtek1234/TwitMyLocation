package pl.wmaciejewski.twitmylocation.places;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wojtek M on 2014-11-29.
 */
public class GooglePlace {
    @SerializedName("formatted_address")
    private String adress;


    private Geometry geometry;

    private String name;

    public String getName() {
        return name;
    }

    class Geometry{
        private GLocation location;
    }

    class GLocation{
        double lat;
        double lng;
    }

    public String getAdress() {
        return adress;
    }

    public double getLocationLat(){
        return geometry.location.lat;
    }
    public double getLocationLong(){

        return geometry.location.lng;
    }



}
