package com.rya.litmarket.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppBean;
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

public class AppHolder extends BaseHolder<AppBean> {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.ll_download)
    LinearLayout llDownload;
    @BindView(R.id.tv_des)
    TextView tvDes;

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_app_listview);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void refreshView(AppBean data) {
        String appUrl = HttpUtil.URL + "image?name=" + data.getIconUrl();

        tvName.setText(data.getName());
        tvDes.setText(data.getDes());
        tvSize.setText(Formatter.formatFileSize(UiUtil.getContext(), data.getSize()));
        rbStar.setRating(data.getStars());

        Glide.with(UiUtil.getContext()).load(appUrl).into(ivIcon);
    }
}
