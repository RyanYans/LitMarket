package com.rya.litmarket.ui.fragment;

import android.view.View;

import com.rya.litmarket.adapter.RecommAdapter;
import com.rya.litmarket.http.protocol.RecommProtocol;
import com.rya.litmarket.ui.base.BaseFragment;
import com.rya.litmarket.ui.view.fly.ShakeListener;
import com.rya.litmarket.ui.view.fly.StellarMap;
import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.ui.view.LoadingPager;

import java.util.ArrayList;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class GameFragment extends BaseFragment {

    private ArrayList<String> recommProtocolData;

    @Override
    protected View onCreateSuccessView() {
        final StellarMap stellar = new StellarMap(UiUtil.getContext());

        stellar.setAdapter(new RecommAdapter(recommProtocolData));
        stellar.setRegularity(6, 9);

        int padding = UiUtil.dip2px(10);
        stellar.setInnerPadding(padding, padding, padding, padding);

        stellar.setGroup(0, true);

        ShakeListener shakeListener = new ShakeListener(UiUtil.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellar.zoomIn();
            }
        });

        return stellar;
    }

    @Override
    protected LoadingPager.ResultState onLoad() {
        RecommProtocol recommProtocol = new RecommProtocol();
        recommProtocolData = recommProtocol.getData(0);

        return getResultState(recommProtocolData);
    }
}
