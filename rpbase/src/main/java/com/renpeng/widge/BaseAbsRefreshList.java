package com.renpeng.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by renpeng on 16/11/23.
 */
public class BaseAbsRefreshList extends AbsRefreshList<ListView> {
    public BaseAbsRefreshList(Context context) {
        super(context);
    }

    public BaseAbsRefreshList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseAbsRefreshList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void setPullContentView(Context context) {
        mContentView = new ListView(context);
        // 设置滚动监听器
        mContentView.setOnScrollListener(this);
    }

    @Override
    protected boolean isTop() {
        return mContentView.getFirstVisiblePosition() == 0
                && getScrollY() <= mHeaderView.getMeasuredHeight();
    }

    @Override
    protected boolean isBottom() {
        return mContentView != null && mContentView.getAdapter() != null
                && mContentView.getLastVisiblePosition() ==
                mContentView.getAdapter().getCount() - 1;
    }
}
