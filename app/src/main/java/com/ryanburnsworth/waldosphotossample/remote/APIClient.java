package com.ryanburnsworth.waldosphotossample.remote;

import com.apollographql.apollo.ApolloClient;
import com.ryanburnsworth.waldosphotossample.util.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIClient {
    private static ApolloClient apolloClient;

    public static ApolloClient getApiClient() {
        if (apolloClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request newRequest;

                            newRequest = request.newBuilder()
                                    .addHeader("Cookie", Config.COOKIE)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            apolloClient = ApolloClient.builder()
                    .okHttpClient(okHttpClient)
                    .serverUrl(Config.BASE_URL)
                    .build();
        }

        return apolloClient;
    }
}
