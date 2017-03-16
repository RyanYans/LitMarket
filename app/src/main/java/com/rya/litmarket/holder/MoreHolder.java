package com.rya.litmarket.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rya.litmarket.R;
import com.rya.litmarket.utils.UiUtil;

import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class MoreHolder extends BaseHolder<Integer> {
    public static final int STATE_MORE = 0;
    public static final int STATE_NONE = 1;
    public static final int STATE_ERROR = 2;
    private LinearLayout loadMoreNormal;
    private TextView loadMoreError;

    public MoreHolder(boolean hasMore) {
        setData(hasMore ? STATE_MORE : STATE_NONE);
    }

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_loadmore_normal);
        loadMoreNormal = (LinearLayout)view.findViewById(R.id.ll_loadmore_normal);
        loadMoreError = (TextView)view.findViewById(R.id.tv_loadmore_error);

        return view;
    }

    @Override
    protected void refreshView(Integer data) {
        if (data == STATE_MORE) {
            loadMoreNormal.setVisibility(View.VISIBLE);
            loadMoreError.setVisibility(View.GONE);
        } else if (data == STATE_NONE) {
            loadMoreNormal.setVisibility(View.GONE);
            loadMoreError.setVisibility(View.GONE);
        } else {
            loadMoreNormal.setVisibility(View.GONE);
            loadMoreError.setVisibility(View.VISIBLE);
        }
    }
}
