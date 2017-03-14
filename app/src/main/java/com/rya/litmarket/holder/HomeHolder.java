package com.rya.litmarket.holder;

import android.view.View;
import android.widget.TextView;

import com.rya.litmarket.R;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeHolder extends BaseHolder<String>{

    private TextView contentView;

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_home_listview);
        contentView = (TextView)view.findViewById(R.id.tv_item);
        return view;
    }

    @Override
    protected void refreshView(String data) {
        contentView.setText(data);
    }

}
