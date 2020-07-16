package com.saiprashant.jdfinnancialproject.APIHit;

import android.text.Html;

import com.saiprashant.jdfinnancialproject.Utility.Utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.saiprashant.jdfinnancialproject.Urls.Urls.base_url;

public class APIHit {


    public static String HitAPI(String api, JSONObject params, String request_method/*, int from_html*/) {
        try {
            URL url = new URL(base_url+api);
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(45000);
            con.setConnectTimeout(45000);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod(request_method);
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            if (params != null) {
                writer.write(getPostDataString(params));
            }
            writer.flush();
            writer.close();
            os.close();
            int status = con.getResponseCode();
            InputStream in;
            if (status == HttpsURLConnection.HTTP_OK)
                in = con.getInputStream();
            else
                in = con.getErrorStream();
            String readStream = readStream(in, 1);
            Utility.printMessage("readSrram : " + readStream);
            return readStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    private static String readStream(InputStream in, int from_html) {
        StringBuilder sb = new StringBuilder();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (from_html == 1) {
            return Html.fromHtml(sb.toString()).toString();
        } else return (sb.toString());
    }

}
