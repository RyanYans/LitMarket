package com.rya.litmarket.http.protocol;

import com.google.gson.Gson;
import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.bean.AppDetailBean;

/**
 * Created by ryanyans32 on 2017/3/25.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeAppDetailProtocol extends BaseProtocol<AppDetailBean>{

    private final String packageName;

    public HomeAppDetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected AppDetailBean parseData(String data) {
        AppDetailBean appDetailBean = new Gson().fromJson(data, AppDetailBean.class);

        return appDetailBean;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName=" + packageName;
    }
}
