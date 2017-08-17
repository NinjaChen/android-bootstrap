package rocks.ninjachen.exoplayer.apis;

import rocks.ninjachen.exoplayer.core.Constants;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * User service for connecting the the REST API and
 * getting the users.
 */
public interface ZeonicService {

    @GET(Constants.Zeonic.URL_BUS_STATIONS)
    Object busStations(@Path("c_id") long cid
            , @Path("lat") double lat
            , @Path("lon") double lng
            , @Path("latd") String latD
            , @Path("lond") String lngD
            , @Path("identification") String identification);
}
