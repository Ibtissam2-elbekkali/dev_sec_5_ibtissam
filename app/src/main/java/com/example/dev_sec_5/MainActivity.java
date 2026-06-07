package com.example.dev_sec_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_ibtissam), (v_ibtissam, insets_ibtissam) -> {
            Insets systemBars_ibtissam = insets_ibtissam.getInsets(WindowInsetsCompat.Type.systemBars());
            v_ibtissam.setPadding(systemBars_ibtissam.left, systemBars_ibtissam.top, systemBars_ibtissam.right, systemBars_ibtissam.bottom);
            return insets_ibtissam;
        });

        Button btnHttp3_ibtissam = findViewById(R.id.btnHttp3_ibtissam);
        Button btnWebSocket_ibtissam = findViewById(R.id.btnWebSocket_ibtissam);
        Button btnMtls_ibtissam = findViewById(R.id.btnMtls_ibtissam);
        Button btnDoh_ibtissam = findViewById(R.id.btnDoh_ibtissam);
        Button btnCt_ibtissam = findViewById(R.id.btnCt_ibtissam);
        Button btnFinal_ibtissam = findViewById(R.id.btnFinal_ibtissam);

        btnHttp3_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Http3Activity_ibtissam.class)));
        btnWebSocket_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WebSocketActivity_ibtissam.class)));
        btnMtls_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MtlsActivity_ibtissam.class)));
        btnDoh_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DohActivity_ibtissam.class)));
        btnCt_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CtActivity_ibtissam.class)));
        btnFinal_ibtissam.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FinalActivity_ibtissam.class)));
    }
}
