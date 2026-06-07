package com.example.dev_sec_5.network;

import java.net.Proxy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientConfig_ibtissam {

    public static OkHttpClient createSecureClient_ibtissam() {
        HttpLoggingInterceptor loggingInterceptor_ibtissam = new HttpLoggingInterceptor();
        loggingInterceptor_ibtissam.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        return new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS) // Timeout court pour test
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor_ibtissam)
                .proxy(Proxy.NO_PROXY) // Force l'absence de proxy
                .build();
    }
}