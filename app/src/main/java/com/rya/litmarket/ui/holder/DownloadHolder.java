package com.rya.litmarket.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.bean.DownloadBean;
import com.rya.litmarket.manager.DownloadManager;
import com.rya.litmarket.ui.view.ProgressHorizontal;
import com.rya.litmarket.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/28.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class DownloadHolder extends BaseHolder<AppDetailBean> implements View.OnClickListener, DownloadManager.DownloadObserver {
    @BindView(R.id.btn_fav)
    Button btnFav;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.fl_download)
    FrameLayout flDownload;
    private DownloadManager mDownloadManager;
    private DownloadBean mDownLoadInfo;
    private AppDetailBean mAppInfo;
    private int mCurrentState;
    private float mprogress;
    private ProgressHorizontal pbProgress;

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_home_appdetail_download);
        ButterKnife.bind(this, view);

        btnDownload.setOnClickListener(this);
        flDownload.setOnClickListener(this);

        pbProgress = new ProgressHorizontal(UiUtil.getContext());
        pbProgress.setProgressTextColor(Color.WHITE);
        pbProgress.setProgressTextSize(UiUtil.dip2px(18));
        pbProgress.setProgressResource(R.drawable.progress_normal);
        pbProgress.setBackgroundResource(R.drawable.progress_bg);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        flDownload.addView(pbProgress, params);

        return view;
    }

    @Override
    protected void refreshView(AppDetailBean data) {
        mAppInfo = data;

        // 注册观察..
        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.registerObserver(this);

        DownloadBean info = mDownloadManager.getInfo(data);
        if (info == null) {
            // 之前无下载过
            mCurrentState = DownloadBean.STATE_NONE;
            mprogress = 0;
        } else {
            // 之前已下载过
            mCurrentState = info.getCurrentState();
            mprogress = info.getProgress();
        }

        refreshUI(mCurrentState, mprogress);
    }

    /*
    * 注销观察者
    * */
    public void unRegisterHolder() {
        if (mDownloadManager != null) {
            mDownloadManager.unRegisterObserver(this);
        }
    }

    private void refreshUI(int CurrentState, float progress) {
        mCurrentState = CurrentState;
        mprogress = progress;

        switch (CurrentState) {
            case DownloadBean.STATE_NONE:
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;
            case DownloadBean.STATE_WAITING:
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待");
                break;
            case DownloadBean.STATE_DOWNLOADING:
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setProgress(progress);
                pbProgress.setCenterText("");
                break;
            case DownloadBean.STATE_PAUSE:
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setProgress(progress);
                pbProgress.setCenterText("暂停");
                break;
            case DownloadBean.STATE_SUCCESS:
                btnDownload.setVisibility(View.VISIBLE);
                flDownload.setVisibility(View.GONE);
                btnDownload.setText("安装");

                break;
            case DownloadBean.STATE_ERROR:
                btnDownload.setVisibility(View.VISIBLE);
                flDownload.setVisibility(View.GONE);
                btnDownload.setText("下载失败");

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
                refreshUI(info.getCurrentState(), info.getProgress());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //下载..
            case R.id.btn_download:
            //暂停..
            case R.id.fl_download:
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
        if (info.getId() == mAppInfo.getId()) {
            RefreshUiOnUiThread(info);
        }
    }

    @Override
    public void onDownloadProgressChanged(DownloadBean info) {
        if (info.getId() == mAppInfo.getId()) {
            RefreshUiOnUiThread(info);
        }
    }
}
