package com.example.dev_sec_5.network;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

public class Http3ClientConfig_ibtissam {

    public static OkHttpClient createHttp3Client_ibtissam() {
        HttpLoggingInterceptor loggingInterceptor_ibtissam = new HttpLoggingInterceptor();
        loggingInterceptor_ibtissam.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        List<Protocol> protocols_ibtissam = Arrays.asList(
                Protocol.HTTP_3,
                Protocol.HTTP_2,
                Protocol.HTTP_1_1
        );
        
        return new OkHttpClient.Builder()
                .protocols(protocols_ibtissam)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS))
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor_ibtissam)
                .proxy(Proxy.NO_PROXY)
                .build();
    }
}