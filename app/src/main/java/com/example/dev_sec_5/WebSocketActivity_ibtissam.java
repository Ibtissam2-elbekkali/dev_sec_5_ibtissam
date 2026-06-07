package com.example.dev_sec_5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dev_sec_5.adapters.MessageAdapter_ibtissam;
import com.example.dev_sec_5.network.WebSocketManager_ibtissam;

import java.util.ArrayList;
import java.util.List;

public class WebSocketActivity_ibtissam extends AppCompatActivity implements WebSocketManager_ibtissam.WebSocketCallback_ibtissam {
    
    private WebSocketManager_ibtissam webSocketManager_ibtissam;
    private RecyclerView rvMessages_ibtissam;
    private EditText etMessage_ibtissam;
    private Button btnSend_ibtissam;
    private Button btnConnect_ibtissam;
    private TextView tvStatus_ibtissam;
    
    private MessageAdapter_ibtissam messageAdapter_ibtissam;
    private List<String> messages_ibtissam = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState_ibtissam) {
        super.onCreate(savedInstanceState_ibtissam);
        setContentView(R.layout.activity_websocket_ibtissam);
        
        rvMessages_ibtissam = findViewById(R.id.rvMessages_ws_ibtissam);
        etMessage_ibtissam = findViewById(R.id.etMessage_ws_ibtissam);
        btnSend_ibtissam = findViewById(R.id.btnSend_ws_ibtissam);
        btnConnect_ibtissam = findViewById(R.id.btnConnect_ws_ibtissam);
        tvStatus_ibtissam = findViewById(R.id.tvStatus_ws_ibtissam);
        
        rvMessages_ibtissam.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter_ibtissam = new MessageAdapter_ibtissam(messages_ibtissam);
        rvMessages_ibtissam.setAdapter(messageAdapter_ibtissam);
        
        webSocketManager_ibtissam = new WebSocketManager_ibtissam(this);
        
        btnConnect_ibtissam.setOnClickListener(v_ibtissam -> {
            tvStatus_ibtissam.setText("Connexion...");
            webSocketManager_ibtissam.connect_ibtissam("wss://echo.websocket.org");
        });
        
        btnSend_ibtissam.setOnClickListener(v_ibtissam -> {
            String msg_ibtissam = etMessage_ibtissam.getText().toString().trim();
            if (!msg_ibtissam.isEmpty()) {
                if (webSocketManager_ibtissam.sendMessage_ibtissam(msg_ibtissam)) {
                    messageAdapter_ibtissam.addMessage_ibtissam("Envoyé: " + msg_ibtissam);
                    etMessage_ibtissam.setText("");
                }
            }
        });
        
        btnSend_ibtissam.setEnabled(false);
    }
    
    @Override
    public void onOpen_ibtissam() {
        runOnUiThread(() -> {
            tvStatus_ibtissam.setText("Connecté");
            btnSend_ibtissam.setEnabled(true);
            btnConnect_ibtissam.setEnabled(false);
        });
    }
    
    @Override
    public void onMessage_ibtissam(String text_ibtissam) {
        runOnUiThread(() -> messageAdapter_ibtissam.addMessage_ibtissam("Reçu: " + text_ibtissam));
    }
    
    @Override
    public void onClosing_ibtissam(int code_ibtissam, String reason_ibtissam) {
        runOnUiThread(() -> tvStatus_ibtissam.setText("Fermeture..."));
    }
    
    @Override
    public void onClosed_ibtissam(int code_ibtissam, String reason_ibtissam) {
        runOnUiThread(() -> {
            tvStatus_ibtissam.setText("Déconnecté");
            btnSend_ibtissam.setEnabled(false);
            btnConnect_ibtissam.setEnabled(true);
        });
    }
    
    @Override
    public void onFailure_ibtissam(Throwable t_ibtissam) {
        runOnUiThread(() -> {
            tvStatus_ibtissam.setText("Erreur: " + t_ibtissam.getMessage());
            btnSend_ibtissam.setEnabled(false);
            btnConnect_ibtissam.setEnabled(true);
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketManager_ibtissam != null) {
            webSocketManager_ibtissam.close_ibtissam(1000, "Activite fermee");
        }
    }
}