package com.rya.litmarket.http.protocol;

import com.google.gson.Gson;
import com.rya.litmarket.bean.HomeBean;

/**
 * Created by ryanyans32 on 2017/3/15.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeProtocol extends BaseProtocol<HomeBean> {
    @Override
    protected HomeBean parseData(String data) {
        return new Gson().fromJson(data, HomeBean.class);
    }

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return "";
    }
}
