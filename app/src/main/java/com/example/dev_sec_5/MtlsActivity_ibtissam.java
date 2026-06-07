package com.example.dev_sec_5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_sec_5.network.MtlsClientConfig_ibtissam;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MtlsActivity_ibtissam extends AppCompatActivity {
    
    private static final String TAG_ibtissam = "MtlsActivity";
    private static final String MTLS_TEST_URL_ibtissam = "https://mtls-test.example.com/";
    private static final String KEYSTORE_FILE_ibtissam = "client.p12";
    private static final String KEYSTORE_PASSWORD_ibtissam = "password";
    
    private TextView tvResult_ibtissam;
    private Button btnTestMtls_ibtissam;
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_mtls_ibtissam);
        
        tvResult_ibtissam = findViewById(R.id.tvResult_mtls_ibtissam);
        btnTestMtls_ibtissam = findViewById(R.id.btnTestMtls_ibtissam);
        
        btnTestMtls_ibtissam.setOnClickListener(v_ibtissam -> testMtls_ibtissam());
    }
    
    private void testMtls_ibtissam() {
        tvResult_ibtissam.setText("Test mTLS en cours...");
        
        OkHttpClient mtlsClient_ibtissam = MtlsClientConfig_ibtissam.createMtlsClient_ibtissam(
                this, KEYSTORE_FILE_ibtissam, KEYSTORE_PASSWORD_ibtissam);
        
        if (mtlsClient_ibtissam == null) {
            tvResult_ibtissam.setText("Erreur configuration client mTLS");
            return;
        }
        
        Request request_ibtissam = new Request.Builder()
                .url(MTLS_TEST_URL_ibtissam)
                .build();
        
        mtlsClient_ibtissam.newCall(request_ibtissam).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e_ibtissam) {
                runOnUiThread(() -> tvResult_ibtissam.setText("Erreur: " + e_ibtissam.getMessage()));
            }
            
            @Override
            public void onResponse(Call call, Response response_ibtissam) throws IOException {
                final String body_ibtissam = response_ibtissam.body() != null ? response_ibtissam.body().string() : "Corps vide";
                final int code_ibtissam = response_ibtissam.code();
                
                runOnUiThread(() -> {
                    StringBuilder sb_ibtissam = new StringBuilder();
                    sb_ibtissam.append("Code: ").append(code_ibtissam).append("\n\n");
                    sb_ibtissam.append("Réponse:\n").append(body_ibtissam);
                    tvResult_ibtissam.setText(sb_ibtissam.toString());
                });
            }
        });
    }
}