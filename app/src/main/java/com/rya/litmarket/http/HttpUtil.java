package com.rya.litmarket.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ryanyans32 on 2017/3/15.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HttpUtil {
    public static final String URL = "http://127.0.0.1:8090/";

    public static Response download(String downloadUrl) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(URL + "download?name=" + downloadUrl)
                .build();

        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
