package com.rya.litmarket.http.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.bean.HomeBean;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/15.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppProtocol extends BaseProtocol<ArrayList<AppBean>> {
    @Override
    protected ArrayList<AppBean> parseData(String data) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<AppBean> appBeanList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            AppBean userBean = gson.fromJson(user, AppBean.class);
            appBeanList.add(userBean);
        }

        return appBeanList;
    }

    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }
}
