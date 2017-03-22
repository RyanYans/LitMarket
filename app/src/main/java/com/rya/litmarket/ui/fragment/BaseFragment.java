package com.rya.litmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rya.litmarket.utils.UiUtil;
import com.rya.litmarket.ui.view.LoadingPager;

import java.util.List;

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

    /**
     * 检查服务器返回数据是否为空
     * @param list  服务器返回数据
     * @return  返回给LoadingPager层 数据接收信息
     */
    @NonNull
    public LoadingPager.ResultState getResultState(List list) {
        if (list != null) {
            return list.size() > 0 ? LoadingPager.ResultState.SUCESS
                    : LoadingPager.ResultState.EMPTY;
        }
        return LoadingPager.ResultState.ERROR;
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
