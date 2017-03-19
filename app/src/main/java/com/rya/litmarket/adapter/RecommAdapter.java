package com.rya.litmarket.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rya.litmarket.R;
import com.rya.litmarket.ui.view.fly.StellarMap;
import com.rya.litmarket.utils.UiUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ryanyans32 on 2017/3/18.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class RecommAdapter implements StellarMap.Adapter {

    private final List<String> recommProtocolData;

    public RecommAdapter(ArrayList<String> recommProtocolData) {
        this.recommProtocolData = recommProtocolData;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getCount(int group) {
        if (group == getGroupCount() - 1) {
            return recommProtocolData.size() / getGroupCount() + recommProtocolData.size() % getGroupCount();
        }
        return recommProtocolData.size() / getGroupCount();
    }

    @Override
    public View getView(int group, int position, View convertView) {
        if (group > 0) {
            position += group * getCount(group - 1);
        }

        TextView textView = new TextView(UiUtil.getContext());
        textView.setTextColor(UiUtil.getRandomColor());
        textView.setTextSize(UiUtil.getRandomSize());
        textView.setText(recommProtocolData.get(position));

        final int finalPosition = position;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UiUtil.getContext(), recommProtocolData.get(finalPosition), Toast.LENGTH_SHORT).show();
            }
        });

        return textView;
    }

    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {

        // 下滑，上一页 iszoom True
        if (isZoomIn) {
            if (group > 0) {
                group--;
            } else {
                group = getGroupCount() - 1;
            }
        } else {
            if (group < getGroupCount() - 1) {
                group++;
            } else {
                group = 0;
            }
        }
        return group;
    }
}
