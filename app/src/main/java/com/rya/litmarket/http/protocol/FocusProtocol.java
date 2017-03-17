package com.rya.litmarket.http.protocol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.rya.litmarket.bean.AppBean;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/15.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class FocusProtocol extends BaseProtocol<ArrayList<AppBean>> {
    @Override
    protected ArrayList<AppBean> parseData(String data) {


        return null;
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
