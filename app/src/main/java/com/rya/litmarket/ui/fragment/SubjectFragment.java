package com.rya.litmarket.ui.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rya.litmarket.adapter.SubjectAdapter;
import com.rya.litmarket.bean.AppBean;
import com.rya.litmarket.bean.SubjectBean;
import com.rya.litmarket.http.protocol.SubjectProtocol;
import com.rya.litmarket.ui.view.LoadingPager;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectBean> subjectBeanArrayList;

    @Override
    protected View onCreateSuccessView() {
        ListView listView = new ListView(UiUtil.getContext());
        listView.setAdapter(new SubjectAdapter(subjectBeanArrayList));

        return listView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        subjectBeanArrayList = subjectProtocol.getData(0);

        if (subjectBeanArrayList != null) {
            return subjectBeanArrayList.size() > 0 ? LoadingPager.ResultState.SUCESS
                    : LoadingPager.ResultState.EMPTY;
        }
        return LoadingPager.ResultState.ERROR;
    }
}