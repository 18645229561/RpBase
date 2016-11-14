package com.example.renpeng.rpbase.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by renpeng on 16/11/11.
 */
public class RequestUtil {

    public static String requestHttpUrlConnection(String http){

        try {
            URL url = new URL(http);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("GET");
            int code = httpURLConnection.getResponseCode();
            if(code == 200){
                return streamToString(httpURLConnection.getInputStream());
            }else {
                return "网路访问失败";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "网路访问失败 catch";
    }

    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e("tag", e.toString());
            return null;
        }
    }

}
