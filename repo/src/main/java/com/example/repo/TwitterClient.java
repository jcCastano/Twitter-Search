package com.example.repo;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitterClient {

    private static final String API_BASE_URL = "https://api.twitter.com";

    public static <T> T makeService(final Class<T> service) {
        OkHttpClient client = getOkClient();
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(service);
    }

    private static OkHttpClient getOkClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Accept-Language", "en")
                        .addHeader("Content-Type", "application/json")
                        .build();

                return chain.proceed(request);
            }
        });

        return client.build();
    }

}
