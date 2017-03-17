package com.rya.litmarket.ui.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rya.litmarket.R;
import com.rya.litmarket.utils.UiUtil;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */
public class MainContentFragment extends Fragment {

    private Indicator mainIndicator;
    private ViewPager mainViewPager;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = View.inflate(getActivity(), R.layout.fragment_main, null);

        mainIndicator = (Indicator) rootView.findViewById(R.id.main_indicator);
        mainViewPager = (ViewPager) rootView.findViewById(R.id.vp_main);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView();

        return rootView;
    }

    private void initView() {
        initIndicatorViewPager();
    }

    private void initIndicatorViewPager() {
        // 设置指示器底部样式
        mainIndicator.setScrollBar(new ColorBar(UiUtil.getContext(), UiUtil.getColor(R.color.colorGlobal), 5));

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.3f;
        mainIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(UiUtil.getColor(R.color.colorGlobal)
                , Color.GRAY).setSize(selectSize, unSelectSize));

        //结合 viewPager和indicator
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mainIndicator, mainViewPager);

        // 设置indicatorViewPager的适配器( 包括设置viewPager适配器 -- newsDetailViewPager.setAdapter(new NewsDetilAdapter()); )
        indicatorViewPager.setAdapter(new TopTabIndicatorAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager()));

        indicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                BaseFragment fragment = FragmentFactory.creatFragment(currentItem);

            }
        });

        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class TopTabIndicatorAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private final String[] tabNames;

        public TopTabIndicatorAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            tabNames = UiUtil.getStringArr(R.array.tab_names);
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {

            /* 非空判断 */
            if (tabNames.length <= 0) {
                return null;
            }
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_tab_top, container, false);
            }
            TextView textView = (TextView) convertView;

            textView.setText(tabNames[position]);

            int witdh = getTextWidth(textView);
            int padding = UiUtil.dip2px(12);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);

            return convertView;
        }

        @Override
        public android.support.v4.app.Fragment getFragmentForPage(int position) {
            return FragmentFactory.creatFragment(position);
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }
    }
}
