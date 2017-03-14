package com.rya.litmarket.utils;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ryanyans32 on 2017/3/14.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class TestUtil {

    private static final String TAG = "TestUtil";
    public static ArrayList getAdapterData() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            strings.add("我是测试数据啦。。" + i);
        }

        Log.i(TAG, "ListBaseAdapter: " + strings.size());
        return strings;
    }
}
