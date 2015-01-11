package com.kleszcz.damian.zad_1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

class ServiceHandler {

    private static String response = null;
    private final static int GET = 1;
    private final static int POST = 2;

    public ServiceHandler() {

    }


    public String makeServiceCall(String url) {
        return this.makeServiceCall(url, ServiceHandler.GET);
    }

    String makeServiceCall(String url, int method) {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity;
            HttpResponse httpResponse = null;


            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);


                httpResponse = httpClient.execute(httpPost);

            } else if (method == GET) {
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }
            assert httpResponse != null;
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }
}
