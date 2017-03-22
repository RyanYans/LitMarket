package com.rya.litmarket.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.rya.litmarket.adapter.HomeAdapter;
import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.http.protocol.HomeProtocol;
import com.rya.litmarket.ui.holder.HomeHeaderHolder;
import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.ui.view.LoadingPager;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class HomeFragment extends BaseFragment {

    private HomeBean mHomeBean;

    @Override
    protected View onCreateSuccessView() {

        ListView listView = new ListView(UiUtil.getContext());

        //添加头部轮播图
        HomeHeaderHolder homeHeaderHolder = new HomeHeaderHolder();
        homeHeaderHolder.setData(mHomeBean.getPicture());
        listView.addHeaderView(homeHeaderHolder.getRootView());

        listView.setAdapter(new HomeAdapter(mHomeBean.getList()));

        return listView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        HomeProtocol homeProtocol = new HomeProtocol();
        mHomeBean = homeProtocol.getData(0);

        if (mHomeBean != null) {
            return getResultState(mHomeBean.getList());
        }
        return LoadingPager.ResultState.ERROR;
    }

}
