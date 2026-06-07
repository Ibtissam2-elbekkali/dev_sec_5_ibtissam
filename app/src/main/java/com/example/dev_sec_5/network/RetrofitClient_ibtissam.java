package com.example.dev_sec_5.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient_ibtissam {
    private static final String BASE_URL_ibtissam = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit_ibtissam = null;
    
    public static Retrofit getClient_ibtissam() {
        if (retrofit_ibtissam == null) {
            OkHttpClient client_ibtissam = HttpClientConfig_ibtissam.createSecureClient_ibtissam();
            
            retrofit_ibtissam = new Retrofit.Builder()
                    .baseUrl(BASE_URL_ibtissam)
                    .client(client_ibtissam)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit_ibtissam;
    }
    
    public static <T> T createService_ibtissam(Class<T> serviceClass_ibtissam) {
        return getClient_ibtissam().create(serviceClass_ibtissam);
    }
}