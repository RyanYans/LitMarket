package com.rya.litmarket.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rya.litmarket.holder.BaseHolder;
import com.rya.litmarket.holder.HomeHolder;
import com.rya.litmarket.holder.MoreHolder;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class ListBaseAdapter<T> extends BaseAdapter {
    private static final String TAG = "ListBaseAdapter";
    private static final int TYPE_MORE = 0;
    private static final int TYPE_NOMAL = 1;
    private ArrayList<T> data;

    public ListBaseAdapter(ArrayList<T> data) {
        this.data = data;
        Log.i(TAG, "ListBaseAdapter: " + data.size());
    }

    @Override
    public int getCount() {
        // 加载更多+1
        return data.size() + 1;
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getViewTypeCount() {
        // 普通 + 加载更多
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (getCount() - 1)) {
            return TYPE_MORE;
        } else {
            return getInnerType();
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        BaseHolder holder = null;
        if (convertView == null) {
            if (getItemViewType(i) == TYPE_MORE) {
                holder = new MoreHolder();
            } else if (getItemViewType(i) == TYPE_NOMAL){
                holder = new HomeHolder();
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        if (getItemViewType(i) == TYPE_NOMAL) {
            holder.setData(getItem(i));
        } else {
            // 加载更多
            MoreHolder moreHolder = (MoreHolder) holder;
            loadMore(moreHolder);
        }

        return holder.getRootView();
    }

    private boolean isLoadMore = false;
    private void loadMore(MoreHolder moreHolder) {
        if (!isLoadMore) {
            isLoadMore = true;

            moreHolder.setData(MoreHolder.STATE_MORE);
        }
    }

    public int getInnerType() {
        return TYPE_NOMAL;
    }
}
