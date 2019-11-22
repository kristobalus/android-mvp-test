package com.example.androiddevelopertask.api;

import com.example.androiddevelopertask.api.services.AuthenticationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String baseUrl = "https://p0jtvgfrj3.execute-api.eu-central-1.amazonaws.com/test/";


    @Provides
    @Singleton
    Retrofit provideRetrofit() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(baseUrl);
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }

    @Provides
    @Singleton
    AuthenticationService provideAuthenticationService(Retrofit retrofit) {
        return retrofit.create(AuthenticationService.class);
    }

}
