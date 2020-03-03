package com.dailyyoga.image;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2019/9/6 14:48
 * @description:
 */
@GlideModule
public class OkHttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        SSLSocketFactory sslSocketFactory = SSLSocketClient.getSSLSocketFactory();
        if (sslSocketFactory != null) {
            client.sslSocketFactory(sslSocketFactory, SSLSocketClient.ignoreTrustManager())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        }

        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client.build()));
    }
}