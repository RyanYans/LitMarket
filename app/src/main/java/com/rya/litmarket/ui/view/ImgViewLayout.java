package com.rya.litmarket.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.MessagePattern;
import android.icu.util.Measure;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.rya.litmarket.R;

/**
 * Created by ryanyans32 on 2017/3/18.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class ImgViewLayout extends FrameLayout {

    private float mRatio;

    public ImgViewLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImgViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImgViewLayout);
        mRatio = typedArray.getFloat(R.styleable.ImgViewLayout_Ratio, -1);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRatio > 0) {
            int imageWidth = width - getPaddingLeft() - getPaddingRight();

            int imageHeight = (int) (imageWidth / mRatio + 0.5f);

            // 动态获取具体高度
            height = imageHeight + getPaddingTop() + getPaddingBottom();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
