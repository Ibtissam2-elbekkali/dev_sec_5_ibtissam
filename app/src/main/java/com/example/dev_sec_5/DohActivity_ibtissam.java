package com.example.dev_sec_5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_sec_5.network.DohClientConfig_ibtissam;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DohActivity_ibtissam extends AppCompatActivity {
    
    private static final String TAG_ibtissam = "DohActivity";
    private EditText etDomain_ibtissam;
    private Button btnResolve_ibtissam;
    private Button btnTestRequest_ibtissam;
    private TextView tvResult_ibtissam;
    private OkHttpClient dohClient_ibtissam;
    private Executor executor_ibtissam = Executors.newSingleThreadExecutor();
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_doh_ibtissam);
        
        etDomain_ibtissam = findViewById(R.id.etDomain_doh_ibtissam);
        btnResolve_ibtissam = findViewById(R.id.btnResolve_doh_ibtissam);
        btnTestRequest_ibtissam = findViewById(R.id.btnTestRequest_doh_ibtissam);
        tvResult_ibtissam = findViewById(R.id.tvResult_doh_ibtissam);
        
        dohClient_ibtissam = DohClientConfig_ibtissam.createDohClient_ibtissam();
        
        btnResolve_ibtissam.setOnClickListener(v_ibtissam -> resolveDomain_ibtissam());
        btnTestRequest_ibtissam.setOnClickListener(v_ibtissam -> testRequest_ibtissam());
    }
    
    private void resolveDomain_ibtissam() {
        final String domain_ibtissam = etDomain_ibtissam.getText().toString().trim();
        if (domain_ibtissam.isEmpty()) return;
        
        tvResult_ibtissam.setText("Résolution...");
        executor_ibtissam.execute(() -> {
            try {
                List<InetAddress> addresses_ibtissam = dohClient_ibtissam.dns().lookup(domain_ibtissam);
                StringBuilder sb_ibtissam = new StringBuilder("Résultats DoH:\n");
                for (InetAddress addr_ibtissam : addresses_ibtissam) {
                    sb_ibtissam.append("- ").append(addr_ibtissam.getHostAddress()).append("\n");
                }
                runOnUiThread(() -> tvResult_ibtissam.setText(sb_ibtissam.toString()));
            } catch (UnknownHostException e_ibtissam) {
                runOnUiThread(() -> tvResult_ibtissam.setText("Erreur: " + e_ibtissam.getMessage()));
            }
        });
    }
    
    private void testRequest_ibtissam() {
        String domain_ibtissam = etDomain_ibtissam.getText().toString().trim();
        if (domain_ibtissam.isEmpty()) return;
        
        Request request_ibtissam = new Request.Builder().url("https://" + domain_ibtissam).build();
        dohClient_ibtissam.newCall(request_ibtissam).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e_ibtissam) {
                runOnUiThread(() -> tvResult_ibtissam.setText("Erreur: " + e_ibtissam.getMessage()));
            }
            @Override
            public void onResponse(Call call, Response response_ibtissam) {
                String protocol_ibtissam = response_ibtissam.protocol().toString();
                runOnUiThread(() -> tvResult_ibtissam.setText("Succès via DoH\nProtocole: " + protocol_ibtissam + "\nCode: " + response_ibtissam.code()));
            }
        });
    }
}