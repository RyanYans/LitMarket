package com.rya.litmarket.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.holder.BaseHolder;
import com.rya.litmarket.holder.HomeHolder;
import com.rya.litmarket.holder.MoreHolder;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public abstract class ListBaseAdapter<T> extends BaseAdapter {
    private static final String TAG = "ListBaseAdapter";
    private static final int TYPE_MORE = 0;
    private static final int TYPE_NOMAL = 1;
    private List<T> data;

    public ListBaseAdapter(List<T> data) {
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
                holder = new MoreHolder(hasMore());
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
            assert moreHolder != null;
            if (moreHolder.getData() == MoreHolder.STATE_MORE) {
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    private boolean hasMore() {
        return true;
    }

    private boolean isLoadMore = false;

    private void loadMore(final MoreHolder moreHolder) {
        if (!isLoadMore) {
            isLoadMore = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 获取数据，添加到集合。
                    final List<T> moreData = onLoadMore();
                    if (moreData != null) {
                        data.addAll(moreData);

                        moreHolder.setData(MoreHolder.STATE_MORE);

                        UiUtil.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                // 更新listView数据
                                ListBaseAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }).start();

            isLoadMore = false;
        }
    }

    abstract public List<T> onLoadMore();

    public int getInnerType() {
        return TYPE_NOMAL;
    }

    public int getListSize() {
        return data.size();
    }
}
