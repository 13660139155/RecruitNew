package com.example.hy.recruitnew.http;

import android.content.Context;

import com.example.hy.recruitnew.http.cookie.CookieManger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chensongsong on 2018/7/12.
 */

public class HttpUtils {

    private OkHttpClient okHttpClient;

    public HttpUtils(Context context){
        okHttpClient = new OkHttpClient.Builder().
                cookieJar(new CookieManger(context))
                .build();
    }

    public static String requsetUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String resultData = null;
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                inputStream.close();
                resultData = new String(byteArrayOutputStream.toByteArray());
                byteArrayOutputStream.close();
                return resultData;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  String requestPost(String urlString, RegisterData registerData){
        okHttpClient.followRedirects();
        okHttpClient.followSslRedirects();
        //json提交
        // MediaType mediaType = MediaType.parse("application/json");
        //RequestBody requestBody = RequestBody.create(mediaType,postParam);

        //通过Form表达提交
        FormBody formBody = new FormBody.Builder()
                                .add("studentId", registerData.getSchoolNumber())
                                .add("name", registerData.getName())
                                .add("sex", String.valueOf(registerData.getSex()))
                                .add("professionClass", registerData.getProfession() + registerData.getClasses())
                                .add("direction", registerData.getDirection())
                                .add("contact", registerData.getPhone())
                                .add("introduction", registerData.getSelfIntroduction())
                                .add("validate", registerData.getValidate())
                                .add("challenge", registerData.getChallenge())
                                .add("seccode", registerData.getSeccode())
                                .build();

        Request request = new Request
                .Builder()
                .post(formBody)
                .url(urlString)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String requestGet(String urlString){
        okHttpClient.followRedirects();
        okHttpClient.followSslRedirects();
        Request request = new Request.Builder().url(urlString).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
