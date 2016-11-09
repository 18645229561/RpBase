package com.renpeng.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by renpeng on 16/11/9.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private IBaseRecyclerViewItem iBaseRecyclerViewItem;

    public BaseRecyclerViewHolder(View itemView,IBaseRecyclerViewItem iBaseRecyclerViewItem) {
        super(itemView);
        this.iBaseRecyclerViewItem = iBaseRecyclerViewItem;
        iBaseRecyclerViewItem.initView(itemView);
    }

    public IBaseRecyclerViewItem getIBaseRecyclerViewItem(){
        return iBaseRecyclerViewItem;
    }
}
