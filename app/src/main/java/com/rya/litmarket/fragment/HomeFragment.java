package com.rya.litmarket.fragment;

import android.view.View;
import android.widget.ListView;

import com.rya.litmarket.adapter.ListBaseAdapter;
import com.rya.litmarket.utils.TestUtil;
import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.view.LoadingPager;

import java.util.ArrayList;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class HomeFragment extends BaseFragment {

    @Override
    protected View onCreateSuccessView() {

        ListView listView = new ListView(UiUtil.getContext());
        listView.setAdapter(new MyAdapter(TestUtil.getAdapterData()));


        return listView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.SUCESS;
    }

    private class MyAdapter extends ListBaseAdapter {
        public MyAdapter(ArrayList data) {
            super(data);
        }

    }

}
