package com.rya.litmarket.http.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.bean.SubjectBean;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/15.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectBean>> {
    @Override
    protected ArrayList<SubjectBean> parseData(String data) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(data).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<SubjectBean> subjectBeenList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement bean : jsonArray) {
            //使用GSON，直接转成Bean对象
            SubjectBean subjectBean = gson.fromJson(bean, SubjectBean.class);
            subjectBeenList.add(subjectBean);
        }

        return subjectBeenList;
    }

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }
}
