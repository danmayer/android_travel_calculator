package com.mayerdan.travel_app.services;

import com.mayerdan.travel_app.model.AuthResponse;
import com.mayerdan.travel_app.model.Cost;
import com.mayerdan.travel_app.model.Destination;
import com.mayerdan.travel_app.model.Trip;
import com.mayerdan.travel_app.model.UserObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by danmayer on 2/25/15.
 */
public interface ApiService {
    @POST("/api/session")
    AuthResponse loginUser(@Body UserObject user_object);

    @POST("/api/registrations")
    AuthResponse registerUser(@Body UserObject user_object);

    @GET("/api/destinations")
    List<Destination> listDestinations(@Query("auth_token") String authToken);

    @GET("/api/trips")
    void listTrips(@Query("auth_token") String authToken, Callback<List<Trip>> cb);

    @POST("/api/trips")
    void createTrip(@Body Trip trip, @Query("auth_token") String authToken, Callback<Trip> cb);

    @DELETE("/api/trips/{id}")
    void deleteTrip(@Path("id") int id, @Query("auth_token") String authToken, Callback<Void> cb);

    @GET("/api/trips/{trip_id}/costs")
    void tripCosts(@Path("trip_id") int tripId, @Query("auth_token") String authToken, Callback<List<Cost>> cb);

    @DELETE("/api/trips/{trip_id}/costs/{id}")
    void deleteCost(@Path("trip_id") int tripId, @Path("id") int id, @Query("auth_token") String authToken, Callback<Void> cb);
}