package com.example.dev_sec_5.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MtlsClientConfig_ibtissam {
    private static final String TAG_ibtissam = "MtlsClientConfig";
    
    public static OkHttpClient createMtlsClient_ibtissam(Context context_ibtissam, String keyStoreFile_ibtissam, String keyStorePassword_ibtissam) {
        try {
            KeyStore keyStore_ibtissam = KeyStore.getInstance("PKCS12");
            InputStream inputStream_ibtissam = context_ibtissam.getAssets().open(keyStoreFile_ibtissam);
            keyStore_ibtissam.load(inputStream_ibtissam, keyStorePassword_ibtissam.toCharArray());
            inputStream_ibtissam.close();
            
            KeyManagerFactory kmf_ibtissam = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf_ibtissam.init(keyStore_ibtissam, keyStorePassword_ibtissam.toCharArray());
            KeyManager[] keyManagers_ibtissam = kmf_ibtissam.getKeyManagers();
            
            TrustManagerFactory tmf_ibtissam = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf_ibtissam.init(keyStore_ibtissam);
            TrustManager[] trustManagers_ibtissam = tmf_ibtissam.getTrustManagers();
            
            SSLContext sslContext_ibtissam = SSLContext.getInstance("TLS");
            sslContext_ibtissam.init(keyManagers_ibtissam, trustManagers_ibtissam, new SecureRandom());
            
            X509TrustManager x509TrustManager_ibtissam = null;
            for (TrustManager tm_ibtissam : trustManagers_ibtissam) {
                if (tm_ibtissam instanceof X509TrustManager) {
                    x509TrustManager_ibtissam = (X509TrustManager) tm_ibtissam;
                    break;
                }
            }
            
            if (x509TrustManager_ibtissam == null) {
                throw new NullPointerException("X509TrustManager non trouvé");
            }
            
            HttpLoggingInterceptor logging_ibtissam = new HttpLoggingInterceptor();
            logging_ibtissam.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext_ibtissam.getSocketFactory(), x509TrustManager_ibtissam)
                    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS))
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(logging_ibtissam)
                    .proxy(Proxy.NO_PROXY)
                    .build();
            
        } catch (Exception e_ibtissam) {
            Log.e(TAG_ibtissam, "Erreur mTLS", e_ibtissam);
            return null;
        }
    }
}