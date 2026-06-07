package com.example.dev_sec_5.network;

import android.util.Log;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketManager_ibtissam {
    private static final String TAG_ibtissam = "WebSocketManager";
    private OkHttpClient client_ibtissam;
    private WebSocket webSocket_ibtissam;
    private WebSocketCallback_ibtissam callback_ibtissam;
    
    public interface WebSocketCallback_ibtissam {
        void onOpen_ibtissam();
        void onMessage_ibtissam(String text_ibtissam);
        void onClosing_ibtissam(int code_ibtissam, String reason_ibtissam);
        void onClosed_ibtissam(int code_ibtissam, String reason_ibtissam);
        void onFailure_ibtissam(Throwable t_ibtissam);
    }
    
    public WebSocketManager_ibtissam(WebSocketCallback_ibtissam callback_ibtissam) {
        this.callback_ibtissam = callback_ibtissam;
        
        client_ibtissam = new OkHttpClient.Builder()
                .connectionSpecs(java.util.Arrays.asList(okhttp3.ConnectionSpec.MODERN_TLS))
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .proxy(Proxy.NO_PROXY)
                .build();
    }
    
    public void connect_ibtissam(String url_ibtissam) {
        Request request_ibtissam = new Request.Builder()
                .url(url_ibtissam)
                .build();
        
        WebSocketListener listener_ibtissam = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d(TAG_ibtissam, "WebSocket ouvert");
                if (callback_ibtissam != null) {
                    callback_ibtissam.onOpen_ibtissam();
                }
            }
            
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d(TAG_ibtissam, "Message reçu: " + text);
                if (callback_ibtissam != null) {
                    callback_ibtissam.onMessage_ibtissam(text);
                }
            }
            
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                if (callback_ibtissam != null) {
                    callback_ibtissam.onClosing_ibtissam(code, reason);
                }
            }
            
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                if (callback_ibtissam != null) {
                    callback_ibtissam.onClosed_ibtissam(code, reason);
                }
            }
            
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.e(TAG_ibtissam, "Erreur WebSocket", t);
                if (callback_ibtissam != null) {
                    callback_ibtissam.onFailure_ibtissam(t);
                }
            }
        };
        
        webSocket_ibtissam = client_ibtissam.newWebSocket(request_ibtissam, listener_ibtissam);
    }
    
    public boolean sendMessage_ibtissam(String message_ibtissam) {
        if (webSocket_ibtissam != null) {
            return webSocket_ibtissam.send(message_ibtissam);
        }
        return false;
    }
    
    public boolean close_ibtissam(int code_ibtissam, String reason_ibtissam) {
        if (webSocket_ibtissam != null) {
            return webSocket_ibtissam.close(code_ibtissam, reason_ibtissam);
        }
        return false;
    }
}