package com.example.dev_sec_5.security;

import android.util.Log;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Handshake;

public class CertificateTransparencyChecker_ibtissam {
    private static final String TAG_ibtissam = "CTChecker";
    
    public interface CTCheckCallback_ibtissam {
        void onResult_ibtissam(boolean isInCTLogs_ibtissam, String details_ibtissam);
        void onError_ibtissam(String errorMessage_ibtissam);
    }
    
    public static void checkDomain_ibtissam(OkHttpClient client_ibtissam, String domain_ibtissam, CTCheckCallback_ibtissam callback_ibtissam) {
        String url_ibtissam = "https://" + domain_ibtissam;
        Log.d(TAG_ibtissam, "Vérification de : " + url_ibtissam);
        
        Request request_ibtissam = new Request.Builder()
                .url(url_ibtissam)
                .build();
        
        client_ibtissam.newCall(request_ibtissam).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG_ibtissam, "Erreur connexion", e);
                callback_ibtissam.onError_ibtissam("Erreur réseau : " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Utilisation de try-with-resources pour s'assurer que la réponse est fermée
                try (Response r = response) {
                    if (!r.isSuccessful()) {
                        callback_ibtissam.onError_ibtissam("Erreur HTTP : " + r.code());
                        return;
                    }

                    Handshake handshake_ibtissam = r.handshake();
                    if (handshake_ibtissam == null) {
                        callback_ibtissam.onError_ibtissam("Pas de handshake SSL (HTTPS requis)");
                        return;
                    }

                    List<Certificate> certs_ibtissam = handshake_ibtissam.peerCertificates();
                    if (certs_ibtissam.isEmpty()) {
                        callback_ibtissam.onError_ibtissam("Aucun certificat trouvé");
                        return;
                    }
                    
                    X509Certificate serverCert_ibtissam = (X509Certificate) certs_ibtissam.get(0);
                    boolean isInCT_ibtissam = simulateCTCheck_ibtissam(serverCert_ibtissam);
                    
                    StringBuilder sb_ibtissam = new StringBuilder();
                    sb_ibtissam.append("Sujet: ").append(serverCert_ibtissam.getSubjectX500Principal().getName()).append("\n");
                    sb_ibtissam.append("Émetteur: ").append(serverCert_ibtissam.getIssuerX500Principal().getName()).append("\n");
                    sb_ibtissam.append("Présent CT: ").append(isInCT_ibtissam ? "Oui" : "Non");
                    
                    callback_ibtissam.onResult_ibtissam(isInCT_ibtissam, sb_ibtissam.toString());
                } catch (Exception e) {
                    Log.e(TAG_ibtissam, "Erreur lors du traitement", e);
                    callback_ibtissam.onError_ibtissam("Erreur interne : " + e.getMessage());
                }
            }
        });
    }
    
    private static boolean simulateCTCheck_ibtissam(X509Certificate cert_ibtissam) {
        String subject_ibtissam = cert_ibtissam.getSubjectX500Principal().getName().toLowerCase();
        return subject_ibtissam.contains("google") || subject_ibtissam.contains("cloudflare") || subject_ibtissam.contains("digicert");
    }
}