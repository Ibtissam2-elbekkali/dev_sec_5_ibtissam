package com.example.dev_sec_5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_sec_5.network.DohClientConfig_ibtissam;
import com.example.dev_sec_5.network.MtlsClientConfig_ibtissam;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FinalActivity_ibtissam extends AppCompatActivity {
    
    private static final String TAG_ibtissam = "FinalActivity";
    private static final String KEYSTORE_FILE_ibtissam = "client.p12";
    private static final String KEYSTORE_PASSWORD_ibtissam = "password";
    private static final String API_URL_ibtissam = "https://jsonplaceholder.typicode.com/";
    
    private EditText etUrl_ibtissam;
    private Button btnExecute_ibtissam;
    private TextView tvResult_ibtissam;
    
    private OkHttpClient secureClient_ibtissam;
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_final_ibtissam);
        
        etUrl_ibtissam = findViewById(R.id.etUrl_final_ibtissam);
        btnExecute_ibtissam = findViewById(R.id.btnExecute_final_ibtissam);
        tvResult_ibtissam = findViewById(R.id.tvResult_final_ibtissam);
        
        initSecureClient_ibtissam();
        
        btnExecute_ibtissam.setOnClickListener(v_ibtissam -> executeSecureRequest_ibtissam());
    }
    
    private void initSecureClient_ibtissam() {
        OkHttpClient mtlsClient_ibtissam = MtlsClientConfig_ibtissam.createMtlsClient_ibtissam(
                this, KEYSTORE_FILE_ibtissam, KEYSTORE_PASSWORD_ibtissam);
        
        if (mtlsClient_ibtissam != null) {
            secureClient_ibtissam = mtlsClient_ibtissam.newBuilder()
                    .dns(DohClientConfig_ibtissam.createDohClient_ibtissam().dns())
                    .build();
            tvResult_ibtissam.setText("Client initialisé avec mTLS et DoH");
        } else {
            secureClient_ibtissam = DohClientConfig_ibtissam.createDohClient_ibtissam();
            tvResult_ibtissam.setText("Client initialisé avec DoH uniquement (mTLS échoué)");
        }
    }
    
    private void executeSecureRequest_ibtissam() {
        String url_ibtissam = etUrl_ibtissam.getText().toString().trim();
        if (url_ibtissam.isEmpty()) {
            url_ibtissam = API_URL_ibtissam + "posts";
            etUrl_ibtissam.setText(url_ibtissam);
        }
        
        tvResult_ibtissam.setText("Exécution requête sécurisée...");
        
        Request request_ibtissam = new Request.Builder()
                .url(url_ibtissam)
                .build();
        
        secureClient_ibtissam.newCall(request_ibtissam).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e_ibtissam) {
                runOnUiThread(() -> tvResult_ibtissam.setText("Erreur: " + e_ibtissam.getMessage()));
            }
            
            @Override
            public void onResponse(Call call, Response response_ibtissam) throws IOException {
                final int code_ibtissam = response_ibtissam.code();
                final String protocol_ibtissam = response_ibtissam.protocol().toString();
                final String body_ibtissam = response_ibtissam.body() != null ? response_ibtissam.body().string() : "Corps vide";
                final String displayBody_ibtissam = body_ibtissam.substring(0, Math.min(500, body_ibtissam.length()));
                
                runOnUiThread(() -> {
                    StringBuilder sb_ibtissam = new StringBuilder();
                    sb_ibtissam.append("Protocole: ").append(protocol_ibtissam).append("\n");
                    sb_ibtissam.append("Code: ").append(code_ibtissam).append("\n\n");
                    sb_ibtissam.append("Sécurité: TLS, Pinning, DoH, mTLS\n\n");
                    sb_ibtissam.append("Réponse:\n").append(displayBody_ibtissam);
                    tvResult_ibtissam.setText(sb_ibtissam.toString());
                });
            }
        });
    }
}