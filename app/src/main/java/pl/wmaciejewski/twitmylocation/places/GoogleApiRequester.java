package pl.wmaciejewski.twitmylocation.places;

        import retrofit.Callback;
        import retrofit.RestAdapter;

/**
 * Created by Wojtek M on 2014-11-29.
 */
public class GoogleApiRequester {
    private final String ENDPOINT = "https://maps.googleapis.com/maps/api";
    private final String KEY = "AIzaSyAMlzH9gtxRmZjFUV3az0O326eN8CuUeZA";

    public void rquestPlacesInCity(String placeName,
                                   String cityName,
                                   Callback<PlacesList> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                                                    .setEndpoint(ENDPOINT)
                                                    .build();

        GooglePlaceService service=restAdapter.create(GooglePlaceService.class);

        String placeParam=placeName+"+in+"+cityName;
        service.getPlacesForCity(placeParam,KEY,callback);

    }
}
