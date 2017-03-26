package com.rya.litmarket.ui.holder;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/25.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppDetailInfoHolder extends BaseHolder<AppDetailBean> {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_download_num)
    TextView tvDownloadNum;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_size)
    TextView tvSize;
    private View mView;

    @Override
    protected View initView() {
        mView = UiUtil.inflate(R.layout.item_home_appdetail_info);
        ButterKnife.bind(this, mView);

        return mView;
    }

    @Override
    protected void refreshView(AppDetailBean data) {
        String iconUrl = HttpUtil.URL + "image?name=" + data.getIconUrl();

        tvName.setText(data.getName());
        tvDate.setText(data.getDate());
        tvDownloadNum.setText(data.getDownloadNum());
        tvSize.setText(Formatter.formatFileSize(UiUtil.getContext(), data.getSize()));
        tvVersion.setText(data.getVersion());

        Glide.with(UiUtil.getContext()).load(iconUrl).into(ivIcon);
    }
}
