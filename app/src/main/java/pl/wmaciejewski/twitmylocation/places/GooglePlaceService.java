package pl.wmaciejewski.twitmylocation.places;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Wojtek M on 2014-11-29.
 */
public interface GooglePlaceService {
    /*
    *
    * https://maps.googleapis.com/maps/api
    * /place/textsearch/json?query=restaurants+in+Sydney&key=AIzaSyCpolzbNq_XcAF78ofElBSU7zR-b0sYmfc
    * */

    @GET("/place/textsearch/json")
    public void getPlacesForCity(@Query("query") String questionText,@Query("key") String key,
                                 Callback<PlacesList> callback);

    @GET("/place/nearbysearch/json")
    public void getPlacesForLocation(@Query("location") String locationLatLng,@Query("radius") int radius
           , @Query("name") String name ,@Query("key") String key,
                                 Callback<PlacesList> callback);

}
