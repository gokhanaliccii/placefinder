package com.gokhanaliccii.placefinder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gokhanaliccii.placefinder.App.BASE_URL;

/**
 * Created by gokhan on 29/09/17.
 */

public class Interactor {

    private static RestInteractor restInteractor;

    public static RestInteractor restInteractor() {
        if (restInteractor == null) {
            restInteractor = new RestInteractor();
        }

        return restInteractor;
    }

    public static class RestInteractor {

        private Retrofit retrofit;

        public RestInteractor() {
            init();
        }

        private void init() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public Retrofit retrofit() {
            return retrofit;
        }

    }
}
