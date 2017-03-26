package com.rya.litmarket.ui.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.http.HttpUtil;
import com.rya.litmarket.utils.UiUtil;

import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/25.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppDetailSafeHolder extends BaseHolder<List<AppDetailBean.SafeBean>> implements View.OnClickListener {

    private LinearLayout mSafePicsLayout;
    private LinearLayout mSafeInfoLayout;
    private RelativeLayout mSafeFold;
    private ViewGroup.LayoutParams mParams;
    private int mInfoHeight;
    private ValueAnimator mAnimator;
    private ImageView mToggle;

    @Override
    protected View initView() {

        View view = UiUtil.inflate(R.layout.item_home_appdeatil_safe);

        mSafePicsLayout = (LinearLayout) view.findViewById(R.id.ll_safe_pics);
        mSafeInfoLayout = (LinearLayout) view.findViewById(R.id.ll_safe_info);
        mSafeFold = (RelativeLayout) view.findViewById(R.id.rl_safe_detail_toggle);
        mToggle = (ImageView) view.findViewById(R.id.iv_safe_toggle);

        // 初始化安全描述布局高度（默认隐藏）
        mParams = mSafeInfoLayout.getLayoutParams();
        mParams.height = 0;
        mSafeInfoLayout.setLayoutParams(mParams);

        // 点击事件（展开/隐藏 描述）
        mSafeFold.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshView(List<AppDetailBean.SafeBean> data) {

        for (int i = 0; i < data.size(); i++) {
            String safeUrl = HttpUtil.URL + "image?name=" + data.get(i).getSafeUrl();
            ImageView ivSafe = new ImageView(UiUtil.getContext());
            LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsInfo.height = UiUtil.dip2px(30);
            layoutParamsInfo.width = UiUtil.dip2px(45);
            Glide.with(UiUtil.getContext()).load(safeUrl).into(ivSafe);
            ivSafe.setLayoutParams(layoutParamsInfo);
            layoutParamsInfo.leftMargin = 5;
            mSafePicsLayout.addView(ivSafe);

            LinearLayout linearLayout = new LinearLayout(UiUtil.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            String safeDesUrl = HttpUtil.URL + "image?name=" + data.get(i).getSafeDesUrl();
            ImageView ivSafeDes = new ImageView(UiUtil.getContext());
            LinearLayout.LayoutParams layoutParamsDes = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsDes.height = UiUtil.dip2px(25);
            layoutParamsDes.width = UiUtil.dip2px(25);
            Glide.with(UiUtil.getContext()).load(safeDesUrl).into(ivSafeDes);
            ivSafeDes.setLayoutParams(layoutParamsDes);
            linearLayout.addView(ivSafeDes);


            TextView tvSafeDes = new TextView(UiUtil.getContext());
            tvSafeDes.setText(data.get(i).getSafeDes());
            tvSafeDes.setTextSize(16);
            tvSafeDes.setTextColor(R.color.colorTextGray);
            linearLayout.addView(tvSafeDes);

            mSafeInfoLayout.addView(linearLayout);
        }

        //测量描述布局高度
        mSafeInfoLayout.measure(0, 0);
        mInfoHeight = mSafeInfoLayout.getMeasuredHeight();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_safe_detail_toggle) {
            toggle();
        }
    }

    private boolean isOpen = false;
    private void toggle() {
        if (!isOpen) {
            isOpen = true;
            mAnimator = ValueAnimator.ofInt(0, mInfoHeight);
        } else {
            isOpen = false;
            mAnimator = ValueAnimator.ofInt(mInfoHeight, 0);
        }

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (int)valueAnimator.getAnimatedValue();
                mParams.height = height;
                mSafeInfoLayout.setLayoutParams(mParams);
            }
        });

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isOpen) {
                    mToggle.setImageResource(R.drawable.arrow_up);
                } else {
                    mToggle.setImageResource(R.drawable.arrow_down);
                }
            }
        });
        mAnimator.setDuration(250);
        mAnimator.start();
    }
}
