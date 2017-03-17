package com.rya.litmarket.adapter;

import android.os.SystemClock;

import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.http.protocol.HomeProtocol;
import com.rya.litmarket.ui.holder.BaseHolder;
import com.rya.litmarket.ui.holder.HomeHolder;

import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/17.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeAdapter extends ListBaseAdapter {
    public HomeAdapter(List<HomeBean.ListBean> data) {
            super(data);
        }

        @Override
        protected BaseHolder getHolder() {
            return new HomeHolder();
        }

        @Override
        public List<HomeBean.ListBean> onLoadMore() {
            //模拟耗时操作。。
            SystemClock.sleep(700);

            HomeProtocol homeProtocol = new HomeProtocol();
            HomeBean data = homeProtocol.getData(getListSize());
            if (data != null) {
                return data.getList();
            } else {
                return null;
            }
        }
}
