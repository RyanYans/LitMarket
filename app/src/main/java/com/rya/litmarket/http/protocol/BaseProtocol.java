package com.rya.litmarket.http.protocol;

import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.utils.IOUtils;
import com.rya.litmarket.utils.UiUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

public abstract class BaseProtocol<T> {
    public T getData(int index) {
        T data = null;
        // 先查看是否存在缓存文件
        String cacheData = getDataFromCache(index);
        if (cacheData != null) {
            data = parseData(cacheData);
        }
        else {
            String fromServerData = getDataFromServer(index);
            if (fromServerData != null) {
                setDataToCache(fromServerData, index);
                data = parseData(fromServerData);
            }
        }

        return data;
    }


    private String getDataFromCache(int index) {
        File file = new File(UiUtil.getContext().getExternalCacheDir(), getKey() + "index="
                + index + getParams());
        if (!file.exists()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        FileReader fileReader = null;
        char[] buff = new char[1024];
        int length;

        try {
            fileReader = new FileReader(file);
            while ((length = fileReader.read(buff)) > 0) {
                stringBuilder.append(buff, 0, length);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fileReader);
        }

        return null;
    }

    private void setDataToCache(String data, int index) {
        File file = new File(UiUtil.getContext().getExternalCacheDir(), getKey() + "index="
                + index + getParams());

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            fileWriter.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(fileWriter);
        }
    }

    // 由子类解析json数据
    protected abstract T parseData(String data);

    private String getDataFromServer(int index) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(HttpUtil.URL + getKey() + "?index="
                + index + getParams()).build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String data = null;
        try {
            data = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    abstract public String getKey();

    abstract public String getParams();
}
