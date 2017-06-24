package com.rya.litmarket.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rya.litmarket.http.protocol.RankingProtocol;
import com.rya.litmarket.ui.base.BaseFragment;
import com.rya.litmarket.ui.view.FlowLayout;
import com.rya.litmarket.ui.view.LoadingPager;
import com.rya.litmarket.utils.DrawableUtil;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class RankingFragment extends BaseFragment {

    private ArrayList<String> mRankingProtocolData;

    @Override
    protected View onCreateSuccessView() {
        int padding = UiUtil.dip2px(10);

        ScrollView scrollView = new ScrollView(UiUtil.getContext());

        FlowLayout flowLayout = new FlowLayout(UiUtil.getContext());
        flowLayout.setPadding(padding, padding, padding, padding);
        flowLayout.setHorizontalSpacing(UiUtil.dip2px(6));
        flowLayout.setVerticalSpacing(UiUtil.dip2px(6));


        for (int i = 0; i < mRankingProtocolData.size(); i++) {
            TextView textView = new TextView(UiUtil.getContext());
            textView.setText(mRankingProtocolData.get(i));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);

            textView.setPadding(padding, padding, padding, padding);

            int pressColor = 0xff999999;
            Drawable nomalDrawable = DrawableUtil.getGradientDrawable(UiUtil.getRandomColor(), UiUtil.dip2px(6));
            Drawable pressDrawable = DrawableUtil.getGradientDrawable(pressColor, UiUtil.dip2px(6));

            StateListDrawable selector = DrawableUtil.getSelector(nomalDrawable, pressDrawable);
            textView.setBackground(selector);

            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UiUtil.getContext(), mRankingProtocolData.get(finalI), Toast.LENGTH_SHORT).show();

                }
            });

            flowLayout.addView(textView);
        }

        scrollView.addView(flowLayout);

        return scrollView;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        RankingProtocol rankingProtocol = new RankingProtocol();
        mRankingProtocolData = rankingProtocol.getData(0);

        return getResultState(mRankingProtocolData);
    }
}
