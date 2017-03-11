package com.rya.litmarket.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.view.LoadingPager;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class GameFragment extends BaseFragment {

    @Override
    protected View onCreateSuccessView() {
        TextView textView = new TextView(UiUtil.getContext());
        textView.setText("GAME~SUCCESS!");
        textView.setTextColor(Color.DKGRAY);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.SUCESS;
    }
}
