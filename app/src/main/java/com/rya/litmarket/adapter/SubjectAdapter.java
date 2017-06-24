package com.rya.litmarket.adapter;

import com.rya.litmarket.bean.SubjectBean;
import com.rya.litmarket.http.protocol.SubjectProtocol;
import com.rya.litmarket.ui.base.BaseHolder;
import com.rya.litmarket.ui.holder.SubjectHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanyans32 on 2017/3/17.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class SubjectAdapter extends ListBaseAdapter<SubjectBean> {

    public SubjectAdapter(List data) {
        super(data);
    }

    @Override
    protected BaseHolder getHolder() {
        return new SubjectHolder();
    }

    @Override
    public List onLoadMore() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        ArrayList<SubjectBean> moreData = subjectProtocol.getData(getListSize());

        if (moreData.size() > 0) {
            return moreData;
        }
        return null;
    }
}


