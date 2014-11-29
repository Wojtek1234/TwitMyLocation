package pl.wmaciejewski.twitmylocation.places;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Wojtek M on 2014-11-29.
 */
public class PlacesList {
    public List<GooglePlace> getPlaces() {
        return places;
    }

    @SerializedName("results")
    List<GooglePlace> places;
}
