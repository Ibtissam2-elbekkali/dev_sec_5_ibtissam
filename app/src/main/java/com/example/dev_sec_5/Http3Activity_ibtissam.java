package com.example.dev_sec_5;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_sec_5.model.Post_ibtissam;
import com.example.dev_sec_5.network.ApiService_ibtissam;
import com.example.dev_sec_5.network.Http3ClientConfig_ibtissam;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Http3Activity_ibtissam extends AppCompatActivity {
    
    private static final String TAG_ibtissam = "Http3Activity";
    private static final String BASE_URL_ibtissam = "https://cloudflare-quic.com/";
    private TextView tvResult_ibtissam;
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_http3_ibtissam);
        
        tvResult_ibtissam = findViewById(R.id.tvResult_http3_ibtissam);
        testHttp3_ibtissam();
    }
    
    private void testHttp3_ibtissam() {
        OkHttpClient http3Client_ibtissam = Http3ClientConfig_ibtissam.createHttp3Client_ibtissam();
        
        Retrofit retrofit_ibtissam = new Retrofit.Builder()
                .baseUrl(BASE_URL_ibtissam)
                .client(http3Client_ibtissam)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        ApiService_ibtissam apiService_ibtissam = retrofit_ibtissam.create(ApiService_ibtissam.class);
        
        Call<List<Post_ibtissam>> call_ibtissam = apiService_ibtissam.getPosts_ibtissam();
        call_ibtissam.enqueue(new Callback<List<Post_ibtissam>>() {
            @Override
            public void onResponse(Call<List<Post_ibtissam>> call, Response<List<Post_ibtissam>> response_ibtissam) {
                String protocol_ibtissam = response_ibtissam.raw().protocol().toString();
                
                StringBuilder result_ibtissam = new StringBuilder();
                result_ibtissam.append("Protocole utilisé: ").append(protocol_ibtissam).append("\n\n");
                
                if (response_ibtissam.isSuccessful()) {
                    result_ibtissam.append("Requête réussie\n");
                    result_ibtissam.append("HTTP/3 ").append(protocol_ibtissam.equals("h3") ? "supporté ✓" : "non supporté ✗");
                } else {
                    result_ibtissam.append("Erreur: ").append(response_ibtissam.code());
                }
                
                tvResult_ibtissam.setText(result_ibtissam.toString());
            }
            
            @Override
            public void onFailure(Call<List<Post_ibtissam>> call, Throwable t_ibtissam) {
                tvResult_ibtissam.setText("Échec: " + t_ibtissam.getMessage());
            }
        });
    }
}