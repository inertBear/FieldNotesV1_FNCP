package com.devhunter.fncp.mvc.controller;

import com.devhunter.fncp.utilities.FNUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.devhunter.fncp.constants.FNSqlConstants.*;

public class JsonParser {

    public JsonParser() {
    }

    JSONObject createHttpRequest(String url, String method, List<NameValuePair> params) {
        //get Product key
        String productKey = FNUtil.getInstance().getCurrentProductKey();
        // add the Product key to params
        params.add(new BasicNameValuePair(PRODUCT_KEY_TAG, productKey));

        // Make new HTTP request
        InputStream mInputStream = null;
        JSONObject mJsonObj = null;
        String mJsonString = "";
        try {
            // checking request method
            if (method.equals(HTTP_REQUEST_METHOD_POST)) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                mInputStream = httpEntity.getContent();
            } else if (method.equals(HTTP_REQUEST_METHOD_GET)) {
                HttpClient httpClient = HttpClientBuilder.create().build();
                String paramString = URLEncodedUtils.format(params, StandardCharsets.UTF_8);
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                mInputStream = httpEntity.getContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("POST exception: " + "An exception in POST");
        }

        try {
            // read response data and build string
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    mInputStream, StandardCharsets.ISO_8859_1), 8);
            StringBuilder str = new StringBuilder();
            String strLine;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine).append("\n");
            }
            mInputStream.close();
            mJsonString = str.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BufferedReader: " + "An Exception in building the string");
        }
        // parse the string into JSON object
        try {
            mJsonObj = new JSONObject(mJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            // should only reach this if the PHP is wrong - check server for errors
            System.out.println("JSON Parse: " + "Make sure you check you PHP");
        }
        return mJsonObj;
    }
}

