package com.rya.litmarket.ui.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rya.litmarket.R;
import com.rya.litmarket.bean.AppDetailBean;
import com.rya.litmarket.utils.LogUtils;
import com.rya.litmarket.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ryanyans32 on 2017/3/26.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class AppDetailDesHolder extends BaseHolder<AppDetailBean> implements View.OnClickListener {
    @BindView(R.id.tv_detail_des)
    TextView tvDetailDes;
    @BindView(R.id.tv_detail_author)
    TextView tvDetailAuthor;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.rl_des_detail_toggle)
    RelativeLayout rlDesDetailToggle;

    private ViewGroup.LayoutParams mParams;

    @Override
    protected View initView() {
        View view = UiUtil.inflate(R.layout.item_home_appdetail_des);
        ButterKnife.bind(this, view);

        rlDesDetailToggle.setOnClickListener(this);

        return view;
    }

    @Override
    protected void refreshView(AppDetailBean data) {
        tvDetailDes.setText(data.getDes());
        tvDetailAuthor.setText(data.getAuthor());

        // 初始化详情高度（7行）
        initDesHeight();

    }

    private void initDesHeight() {
        final int shortHeight = getShortHeight();

        // 待处理Bug：少于7行显示到7行了
        mParams = tvDetailDes.getLayoutParams();
        mParams.height = shortHeight;
        tvDetailDes.setLayoutParams(mParams);
    }

    private int getShortHeight() {
        WindowManager windowManager = (WindowManager) UiUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        int windowsHeight = windowManager.getDefaultDisplay().getHeight();

        int width = tvDetailDes.getWidth();

        int measureWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measureHeight = View.MeasureSpec.makeMeasureSpec(windowsHeight, View.MeasureSpec.AT_MOST);

        // 模拟7行高度
        TextView textView = new TextView(UiUtil.getContext());
        textView.setMaxLines(7);
        textView.setText(getData().getDes());
        textView.measure(measureWidth, measureHeight);


        return textView.getMeasuredHeight();
    }

    private int getLongHeight() {
        WindowManager windowManager = (WindowManager) UiUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        int windowsHeight = windowManager.getDefaultDisplay().getHeight();

        int width = tvDetailDes.getWidth();

        int measureWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measureHeight = View.MeasureSpec.makeMeasureSpec(windowsHeight, View.MeasureSpec.AT_MOST);

        TextView textView = new TextView(UiUtil.getContext());
        textView.setText(getData().getDes());
        textView.measure(measureWidth, measureHeight);


        return textView.getMeasuredHeight();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_des_detail_toggle) {
            toggle();
        }
    }

    private boolean isOpen = false;
    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();

        ValueAnimator mAnimator = null;

        // 假如描述不够7行
        if (shortHeight == longHeight) {
            return;
        }

        if (!isOpen) {
            isOpen = true;
            mAnimator = ValueAnimator.ofInt(shortHeight, longHeight);
        } else {
            isOpen = false;
            mAnimator = ValueAnimator.ofInt(longHeight, shortHeight);
        }

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mParams.height = (int) valueAnimator.getAnimatedValue();
                tvDetailDes.setLayoutParams(mParams);
            }
        });

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isOpen) {
                    ivArrow.setImageResource(R.drawable.arrow_up);

                    final ScrollView scrollView = getScrollView();
                    if (scrollView != null) {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                } else {
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }
            }

            private ScrollView getScrollView() {
                View parent = (View) tvDetailDes.getParent();
                for (int i = 0; i < 10 ; i++) {
                    if (parent instanceof ScrollView) {
                        return (ScrollView) parent;
                    }
                    parent = (View) parent.getParent();
                }
                LogUtils.e("Scroll is Null!");
                return null;
            }
        });

        mAnimator.setDuration(250);
        mAnimator.start();
    }
}
