package com.rya.litmarket.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.rya.litmarket.adapter.AppAdapter;
import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.http.protocol.AppProtocol;
import com.rya.litmarket.ui.view.LoadingPager;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class AppFragment extends BaseFragment {

    private ArrayList<AppBean> mAppBeanArrayList;

    @Override
    protected View onCreateSuccessView() {
        ListView listView = new ListView(UiUtil.getContext());
        listView.setAdapter(new AppAdapter(mAppBeanArrayList));

        return listView;

    }

    @Override
    protected LoadingPager.ResultState onLoad() {

        AppProtocol appProtocol = new AppProtocol();
        mAppBeanArrayList = appProtocol.getData(0);

        if (mAppBeanArrayList != null) {
            return mAppBeanArrayList.size() > 0 ? LoadingPager.ResultState.SUCESS
                    : LoadingPager.ResultState.EMPTY;
        }
        return LoadingPager.ResultState.ERROR;
    }
}
