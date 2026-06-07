package com.example.dev_sec_5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_sec_5.network.HttpClientConfig_ibtissam;
import com.example.dev_sec_5.security.CertificateTransparencyChecker_ibtissam;

import okhttp3.OkHttpClient;

public class CtActivity_ibtissam extends AppCompatActivity {
    
    private EditText etDomain_ibtissam;
    private Button btnCheck_ibtissam;
    private TextView tvResult_ibtissam;
    private OkHttpClient client_ibtissam;
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_ct_ibtissam);
        
        etDomain_ibtissam = findViewById(R.id.etDomain_ct_ibtissam);
        btnCheck_ibtissam = findViewById(R.id.btnCheck_ct_ibtissam);
        tvResult_ibtissam = findViewById(R.id.tvResult_ct_ibtissam);
        
        client_ibtissam = HttpClientConfig_ibtissam.createSecureClient_ibtissam();
        
        btnCheck_ibtissam.setOnClickListener(v_ibtissam -> checkCT_ibtissam());
    }
    
    private void checkCT_ibtissam() {
        String domain_ibtissam = etDomain_ibtissam.getText().toString().trim();
        if (domain_ibtissam.isEmpty()) return;
        
        tvResult_ibtissam.setText("Vérification...");
        CertificateTransparencyChecker_ibtissam.checkDomain_ibtissam(client_ibtissam, domain_ibtissam, new CertificateTransparencyChecker_ibtissam.CTCheckCallback_ibtissam() {
            @Override
            public void onResult_ibtissam(boolean isInCTLogs_ibtissam, String details_ibtissam) {
                runOnUiThread(() -> {
                    String status_ibtissam = isInCTLogs_ibtissam ? "✅ Présent dans CT" : "❌ Absent de CT";
                    tvResult_ibtissam.setText(status_ibtissam + "\n\n" + details_ibtissam);
                });
            }
            
            @Override
            public void onError_ibtissam(String error_ibtissam) {
                runOnUiThread(() -> tvResult_ibtissam.setText("Erreur: " + error_ibtissam));
            }
        });
    }
}