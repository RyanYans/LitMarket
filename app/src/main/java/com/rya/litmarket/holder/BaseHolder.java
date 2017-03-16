package com.rya.litmarket.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

abstract public class BaseHolder<T> {
    private View rootView;
    private T data;


    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
    }

    public View getRootView() {
        return rootView;
    }

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }


    public T getData() {
        return this.data;
    }

    abstract protected View initView();

    protected abstract void refreshView(T data);
}
