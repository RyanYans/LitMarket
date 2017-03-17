package com.rya.litmarket.ui.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.rya.litmarket.adapter.HomeAdapter;
import com.rya.litmarket.adapter.ListBaseAdapter;
import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.http.protocol.HomeProtocol;
import com.rya.litmarket.ui.holder.BaseHolder;
import com.rya.litmarket.ui.holder.HomeHolder;
import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.ui.view.LoadingPager;

import java.util.List;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class HomeFragment extends BaseFragment {

    private HomeBean homeBean;

    @Override
    protected View onCreateSuccessView() {

        ListView listView = new ListView(UiUtil.getContext());
        listView.setAdapter(new HomeAdapter(homeBean.getList()));

        return listView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        HomeProtocol homeProtocol = new HomeProtocol();
        homeBean = homeProtocol.getData(0);

        if (homeBean != null) {
            return homeBean.getList().size() > 0 ? LoadingPager.ResultState.SUCESS
                    : LoadingPager.ResultState.EMPTY;
        } else {
            return LoadingPager.ResultState.ERROR;
        }

    }

}
