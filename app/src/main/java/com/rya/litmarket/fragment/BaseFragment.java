package com.rya.litmarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.view.LoadingPager;

/**
 * Created by ryanyans32 on 2017/3/11.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPager mLoadingPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPager = new LoadingPager(UiUtil.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            protected ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        /*
        * 创建时加载页面数据
        * */
        mLoadingPager.loadData();

        return mLoadingPager;
    }

    /*
    * 加载LoadingPager内容页面数据
    * */
    public void loadData() {
        mLoadingPager.loadData();
    }

    /*
    * 先加载OnLoad方法，异步加载网络数据。
    * */
    protected abstract LoadingPager.ResultState onLoad();

    /*
    * 再加载onCreateSuccessView，加载具体页面布局。
    * */
    protected abstract View onCreateSuccessView();

}
