package com.example.libnetwork;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.libnetwork.http.impl.GetRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.OkHttpClient;

public class TA {
    <E, A> void  fun(E t, List<A> list){

        List<TA> l = new ArrayList<>();

        View view = new View(null);
        new Callable<E>(){
            @Override
            public E call() throws Exception {
                E t = null;
                return t;
            }
        };

        String result = "";
        JSONObject obj = JSONObject.parseObject(result);
        JSONObject jsonObject = obj.getJSONObject("data");
        if(jsonObject != null){
            ParameterizedType type = (ParameterizedType) list.getClass().getGenericSuperclass();
            Type actualTypeArgument = type.getActualTypeArguments()[0];
            JSON.parseObject(jsonObject.toJSONString(), actualTypeArgument);
        }

        GetRequest<Object> request = new GetRequest<>("http://");
        request.addParam("","");
        request.addParam("","");
        request.addParam("","");
        request.execute(null, new Function1<String, Unit>() {
            @Override
            public Unit invoke(String s) {
                return null;
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        TrustManager[] manager = new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
    }
}
