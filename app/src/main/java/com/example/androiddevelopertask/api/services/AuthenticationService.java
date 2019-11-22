package com.example.androiddevelopertask.api.services;

import com.example.androiddevelopertask.api.entities.AuthenticationRequest;
import com.example.androiddevelopertask.api.entities.AuthenticationResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {

    @POST("authenticate")
    Single<Response<AuthenticationResponse>> authenticate(@Body() AuthenticationRequest request);

}
