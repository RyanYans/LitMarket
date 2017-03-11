package com.rya.litmarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.rya.litmarket.R;
import com.rya.litmarket.utils.UiUtil;

/**
 * Created by ryanyans32 on 2017/3/11.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public abstract class LoadingPager extends FrameLayout {

    private final int UNDO_STATE = 0;
    private final int LOADING_STATE = 1;
    private static final int ERROR_STATE = 2;
    private static final int EMPTY_STATE = 3;
    private static final int SUCCESS_STATE = 4;
    private int currentState = UNDO_STATE;
    private View mLoadingPager;
    private View mErrorPager;
    private View mEmptyPager;
    private View mSuccessPager;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /*
    * 将共性页面添加到布局中
    * */
    private void initView() {
        mLoadingPager = UiUtil.inflate(R.layout.pager_loading);
        this.addView(mLoadingPager);

        mErrorPager = UiUtil.inflate(R.layout.pager_error);
        this.addView(mErrorPager);

        mEmptyPager = UiUtil.inflate(R.layout.pager_empty);
        this.addView(mEmptyPager);

        showContentView();
    }

    /*
    * 加载内容页面数据
    * */
    public void loadData() {
        if (currentState != LOADING_STATE) {
            currentState = LOADING_STATE;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ResultState result = onLoad();
                    if (result != null) {
                        currentState = result.getState();

                        /*
                        * 返回成功数据，在UI线程更新内容界面
                        * */
                        UiUtil.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                showContentView();
                            }
                        });
                    }
                }
            }).start();

        }
    }


    /*
    * 根据当前STATE设置共性页面的隐藏或显示
    * */
    private void showContentView() {

        mLoadingPager.setVisibility(currentState == UNDO_STATE || currentState == LOADING_STATE ?
                VISIBLE : GONE);

        mErrorPager.setVisibility(currentState == ERROR_STATE ? VISIBLE : GONE);

        mEmptyPager.setVisibility(currentState == EMPTY_STATE ? VISIBLE : GONE);

        /*
        * 本页面未知页面具体逻辑，
        * 交由调用者实现Success页面
        * */
        if (mSuccessPager == null && currentState == SUCCESS_STATE) {
            mSuccessPager = onCreateSuccessView();
            if (mSuccessPager != null) {
                this.addView(mSuccessPager);
            }
        }

        if (mSuccessPager != null) {
            mSuccessPager.setVisibility(currentState == SUCCESS_STATE ? VISIBLE : GONE);
        }
    }

    /*
    * 枚举，用于返回数据结果
    * */
    public enum ResultState {
        SUCESS(SUCCESS_STATE), EMPTY(EMPTY_STATE), ERROR(ERROR_STATE);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return this.state;
        }
    }

    /*
    * 调用者实现Success页面
    * */
    public abstract View onCreateSuccessView();

    /*
    * 调用者实现数据加载并返回结果
    * */
    protected abstract ResultState onLoad();
}
