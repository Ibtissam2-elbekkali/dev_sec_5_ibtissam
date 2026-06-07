package com.example.dev_sec_5.network;

import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.dnsoverhttps.DnsOverHttps;
import okhttp3.logging.HttpLoggingInterceptor;

public class DohClientConfig_ibtissam {

    public static OkHttpClient createDohClient_ibtissam() {
        HttpLoggingInterceptor logging_ibtissam = new HttpLoggingInterceptor();
        logging_ibtissam.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        OkHttpClient bootstrapClient_ibtissam = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS))
                .addInterceptor(logging_ibtissam)
                .proxy(Proxy.NO_PROXY)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        
        Dns dnsOverHttps_ibtissam = new DnsOverHttps.Builder()
                .client(bootstrapClient_ibtissam)
                .url(HttpUrl.get("https://dns.google/dns-query"))
                .bootstrapDnsHosts(Arrays.asList(getByIp_ibtissam("8.8.8.8"), getByIp_ibtissam("8.8.4.4")))
                .build();
        
        return new OkHttpClient.Builder()
                .dns(dnsOverHttps_ibtissam)
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
    }

    private static InetAddress getByIp_ibtissam(String ip_ibtissam) {
        try {
            return InetAddress.getByName(ip_ibtissam);
        } catch (UnknownHostException e_ibtissam) {
            throw new RuntimeException(e_ibtissam);
        }
    }
}