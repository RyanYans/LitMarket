package com.rya.litmarket.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.global.ConstantsValues;
import com.rya.litmarket.ui.base.BaseHolder;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/26.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppDetailPicsHolder extends BaseHolder<List<String>> {
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    @BindView(R.id.iv_pic4)
    ImageView ivPic4;
    @BindView(R.id.iv_pic5)
    ImageView ivPic5;
    private ArrayList<ImageView> mImageViews;

    @Override
    protected View initView() {
        mImageViews = new ArrayList<>();
        View view = UiUtil.inflate(R.layout.item_home_appdetail__pics);

        ButterKnife.bind(this, view);

        mImageViews.add(ivPic1);
        mImageViews.add(ivPic2);
        mImageViews.add(ivPic3);
        mImageViews.add(ivPic4);
        mImageViews.add(ivPic5);

        return view;
    }

    @Override
    protected void refreshView(List<String> data) {
        if (data != null && mImageViews != null) {
            for (int i = 0; i < data.size(); i++) {
                Glide.with(UiUtil.getContext())
                        .load(ConstantsValues.BASE_URL + data.get(i))
                        .into(mImageViews.get(i));
            }
        }
    }
}
