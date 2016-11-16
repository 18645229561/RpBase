package com.renpeng.httpRequest;

import android.text.TextUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/16.
 */
public class HttpUrlConnectionTest {

    public void requestByHttpClient(String urlStr){
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);//设置读取时间
            connection.setConnectTimeout(15000);//设置链接超时为15秒
            connection.setRequestMethod("POST");//"GET","POST"
            connection.setDoInput(true);//接受输入流
            connection.setDoOutput(true);//启动输出流,当需要传递参数时需要开启
            connection.setRequestProperty("Connection","Keep-Alive");//添加Header

            //===添加参数1
            List<NameValuePair> pairs = new ArrayList<>();
            pairs.add(new BasicNameValuePair("username","fsdfdsfs"));
            pairs.add(new BasicNameValuePair("pwd","fsdafsd"));
            writeParmas(connection.getOutputStream(),pairs);


            //===添加参数2
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String content = "username="+"fdsaf"+"&password="+"fdsafdf";
            out.writeBytes(content);
            out.flush();
            out.close();


            connection.connect();
            inputStream = connection.getInputStream();
            String result = convertStream2String(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertStream2String(InputStream inputStream){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while((line = reader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    private void writeParmas(OutputStream outputStream,List<NameValuePair> pairs) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for(NameValuePair pair : pairs){
            if(!TextUtils.isEmpty(stringBuilder)){
                stringBuilder.append("$");
            }
            stringBuilder.append(URLEncoder.encode(pair.getName(),"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
        }
    }

}
