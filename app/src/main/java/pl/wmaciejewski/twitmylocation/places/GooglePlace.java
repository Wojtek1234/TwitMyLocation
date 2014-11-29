package pl.wmaciejewski.twitmylocation.places;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Wojtek M on 2014-11-29.
 */
public class GooglePlace {
    @SerializedName("formatted_address")
    private String adress;


    private Geometry geometry;
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


    /* {
         "formatted_address" : "44 Macleay St, Potts Point NSW 2010, Australia",
         "geometry" : {
            "location" : {
               "lat" : -33.87087,
               "lng" : 151.225459
            }
         },
         "icon" : "http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png",*/
}
