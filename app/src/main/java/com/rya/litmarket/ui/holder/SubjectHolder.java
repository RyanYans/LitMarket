package com.rya.litmarket.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.bean.SubjectBean;
import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/17.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected View initView() {

        View view = UiUtil.inflate(R.layout.item_subject_listview);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void refreshView(SubjectBean data) {
        String subjectUrl = HttpUtil.URL + "image?name=" + data.getUrl();

        tvTitle.setText(data.getDes());
        Glide.with(UiUtil.getContext()).load(subjectUrl).into(ivPic);
    }
}
