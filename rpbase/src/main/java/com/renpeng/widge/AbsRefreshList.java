package com.renpeng.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;

/**
 * Created by renpeng on 16/11/23.
 */
public abstract class AbsRefreshList<T extends AbsListView> extends AbsPullContentView<T> {
    public AbsRefreshList(Context context) {
        super(context);
    }

    public AbsRefreshList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsRefreshList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setAdapter(ListAdapter adapter) {
        mContentView.setAdapter(adapter);
    }

    public ListAdapter getAdapter() {
        return mContentView.getAdapter();
    }


}
