package com.rya.litmarket.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.http.protocol.HomeAppDetailProtocol;
import com.rya.litmarket.ui.holder.AppDetailDesHolder;
import com.rya.litmarket.ui.holder.AppDetailInfoHolder;
import com.rya.litmarket.ui.holder.AppDetailPicsHolder;
import com.rya.litmarket.ui.holder.AppDetailSafeHolder;
import com.rya.litmarket.ui.view.LoadingPager;
import com.rya.litmarket.utils.UiUtil;

/**
 * Created by ryanyans32 on 2017/3/25.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeAppDetailActivity extends BaseActivity {

    public static String PACKAGE_NAME = "packageName";
    private AppDetailBean mAppDetailBean;
    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadingPager basePager = new LoadingPager(UiUtil.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return HomeAppDetailActivity.this.onCreateSuccessView();
            }

            @Override
            protected ResultState onLoad() {
                return HomeAppDetailActivity.this.onLoad();
            }
        };
        setContentView(basePager);
        basePager.loadData();


    }

    private LoadingPager.ResultState onLoad() {
        String packageName = getIntent().getStringExtra(PACKAGE_NAME);
        HomeAppDetailProtocol homeAppDetailProtocol = new HomeAppDetailProtocol(packageName);
        mAppDetailBean = homeAppDetailProtocol.getData(0);

        if (mAppDetailBean != null) {
            return LoadingPager.ResultState.SUCESS;
        }
        return LoadingPager.ResultState.ERROR;
    }

    private View onCreateSuccessView() {
        mRootView = UiUtil.inflate(R.layout.activity_home_appdetail);

        FrameLayout flDetailInfo = (FrameLayout) mRootView.findViewById(R.id.fl_app_info);
        AppDetailInfoHolder infoHolder = new AppDetailInfoHolder();
        infoHolder.setData(mAppDetailBean);
        View detailInfoView = infoHolder.getRootView();
        flDetailInfo.addView(detailInfoView);

        FrameLayout flSafeInfo = (FrameLayout) mRootView.findViewById(R.id.fl_app_safe);
        AppDetailSafeHolder safeHolder = new AppDetailSafeHolder();
        safeHolder.setData(mAppDetailBean.getSafe());
        View safeInfoView = safeHolder.getRootView();
        flSafeInfo.addView(safeInfoView);

        HorizontalScrollView hsvPicsInfo = (HorizontalScrollView) mRootView.findViewById(R.id.hsv_pics);
        AppDetailPicsHolder picsHolder = new AppDetailPicsHolder();
        picsHolder.setData(mAppDetailBean.getScreen());
        View picsView = picsHolder.getRootView();
        hsvPicsInfo.addView(picsView);

        FrameLayout flDesInfo = (FrameLayout) mRootView.findViewById(R.id.fl_app_des);
        AppDetailDesHolder desHolder = new AppDetailDesHolder();
        desHolder.setData(mAppDetailBean);
        View desView = desHolder.getRootView();
        flDesInfo.addView(desView);

        return mRootView;
    }


}
