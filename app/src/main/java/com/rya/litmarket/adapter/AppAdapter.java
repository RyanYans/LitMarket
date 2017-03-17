package com.rya.litmarket.adapter;

import android.os.SystemClock;

import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.http.protocol.AppProtocol;
import com.rya.litmarket.ui.holder.AppHolder;
import com.rya.litmarket.ui.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/17.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppAdapter extends ListBaseAdapter {
    public AppAdapter(List<AppBean> data) {
        super(data);
    }

    @Override
    protected BaseHolder getHolder() {
        return new AppHolder();
    }

    @Override
    public List<AppBean> onLoadMore() {
        //模拟耗时操作。。
        SystemClock.sleep(700);

        AppProtocol appProtocol = new AppProtocol();
        ArrayList<AppBean> appBeanArrayList = appProtocol.getData(getListSize());

        if (appBeanArrayList != null) {
            return appBeanArrayList;
        }
        return null;
    }
}
