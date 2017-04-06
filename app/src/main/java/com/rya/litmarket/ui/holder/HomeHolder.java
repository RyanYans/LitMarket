package com.rya.litmarket.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.bean.DownloadBean;
import com.rya.litmarket.bean.HomeBean;
import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.manager.DownloadManager;
import com.rya.litmarket.ui.view.ProgressArc;
import com.rya.litmarket.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class HomeHolder extends BaseHolder<HomeBean.ListBean> implements View.OnClickListener, DownloadManager.DownloadObserver {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.fl_home_app_download)
    FrameLayout flHomeAppDownload;
    @BindView(R.id.tv_Home_app_download)
    TextView tvDownload;
    private ProgressArc progressArc;
    private int mCurrentState;
    private float mProgress;
    private DownloadManager mDownloadManager;
    private HomeBean.ListBean mAppInfo;

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_home_listview);
        ButterKnife.bind(this, view);

        progressArc = new ProgressArc(UiUtil.getContext());
        //内圆进度条
        progressArc.setArcDiameter(UiUtil.dip2px(26));
        progressArc.setProgressColor(UiUtil.getColor(R.color.colorGlobal));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UiUtil.dip2px(27), UiUtil.dip2px(27));

        flHomeAppDownload.addView(progressArc, params);

        flHomeAppDownload.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshView(HomeBean.ListBean data) {
        mAppInfo = data;

        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.registerObserver(this);

        // 获取icon图片在服务器的全路径
        String iconUrl = HttpUtil.URL + "image?name=" + data.getIconUrl();

        tvName.setText(data.getName());
        tvDes.setText(data.getDes());
        rbStar.setRating(data.getStars());
        tvSize.setText(Formatter.formatFileSize(UiUtil.getContext(), data.getSize()));
        Glide.with(UiUtil.getContext()).load(iconUrl).into(ivIcon);

        DownloadBean info = mDownloadManager.getInfo(data);
        if (info == null) {
            // 首次下载
            mCurrentState = DownloadBean.STATE_NONE;
            mProgress = 0;
        } else {
            // 已下载过
            mCurrentState = info.getCurrentState();
            mProgress = info.getProgress();
        }

        refreshUI(mCurrentState, mProgress, mAppInfo.getId());
    }

    /*
    * 注销观察者
    * */
    public void unRegisterHolder() {
        if (mDownloadManager != null) {
            mDownloadManager.unRegisterObserver(this);
        }
    }


    private void refreshUI(int CurrentState, float progress, int id) {
        // 判断id是否与该条目匹配，防止listView重用导致item复用显示错误
        if (id != mAppInfo.getId()) {
            return;
        }

        mCurrentState = CurrentState;
        mProgress = progress;

        switch (CurrentState) {
            case DownloadBean.STATE_NONE:
                progressArc.setBackgroundResource(R.drawable.ic_download);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("下载");
                break;
            case DownloadBean.STATE_WAITING:
                progressArc.setBackgroundResource(R.drawable.ic_download);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownload.setText("等待");
                break;
            case DownloadBean.STATE_DOWNLOADING:
                progressArc.setBackgroundResource(R.drawable.ic_pause);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                progressArc.setProgress(progress, true);
                tvDownload.setText((int) (progress * 100) + "%");
                break;
            case DownloadBean.STATE_PAUSE:
                progressArc.setBackgroundResource(R.drawable.ic_resume);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownloadBean.STATE_SUCCESS:
                progressArc.setBackgroundResource(R.drawable.ic_install);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("安装");
                break;
            case DownloadBean.STATE_ERROR:
                progressArc.setBackgroundResource(R.drawable.ic_redownload);
                progressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("重试");
                break;
            default:
                break;
        }
    }

    private void RefreshUiOnUiThread(final DownloadBean info) {
        if (info == null) {
            return;
        }
        UiUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.getCurrentState(), info.getProgress(), info.getId());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //下载..
            case R.id.fl_home_app_download:
                if (mCurrentState == DownloadBean.STATE_NONE
                        || mCurrentState == DownloadBean.STATE_ERROR
                        || mCurrentState == DownloadBean.STATE_PAUSE) {
                    mDownloadManager.download(mAppInfo);
                } else if (mCurrentState == DownloadBean.STATE_WAITING
                        || mCurrentState == DownloadBean.STATE_DOWNLOADING) {
                    mDownloadManager.pause(mAppInfo);
                } else {
                    // 下载成功
                    mDownloadManager.install(mAppInfo);
                }
                break;
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadBean info) {
        if (mAppInfo.getId() == info.getId()) {
            RefreshUiOnUiThread(info);
        }
    }

    @Override
    public void onDownloadProgressChanged(DownloadBean info) {
        if (mAppInfo.getId() == info.getId()) {
            RefreshUiOnUiThread(info);
        }
    }
}
