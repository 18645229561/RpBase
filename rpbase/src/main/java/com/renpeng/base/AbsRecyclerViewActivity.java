package com.renpeng.base;

import android.support.v7.widget.RecyclerView;

import com.example.rpbase.R;
import com.renpeng.base.adapter.BaseRecyclerViewAdapter;
import com.renpeng.base.adapter.IBaseRecyclerViewItem;

import java.util.List;

/**
 * Created by renpeng on 16/11/9.
 */
public abstract class AbsRecyclerViewActivity extends AbsBaseActivity {
    private RecyclerView mRecyclerView;

    private BaseRecyclerViewAdapter mBaseRecyclerViewAdapter;

    @Override
    protected void initView() {
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mBaseRecyclerViewAdapter = new BaseRecyclerViewAdapter(this));
        mRecyclerView.setLayoutManager(loadLayoutManager());


        init();
    }

    protected void setDataList(List dataList){
        mBaseRecyclerViewAdapter.setDataList(dataList);
        update();
    }


    protected void update(){
        mBaseRecyclerViewAdapter.notifyDataSetChanged();
    }

    public abstract IBaseRecyclerViewItem loadIBaseRecyclerViewItem();

    protected abstract RecyclerView.LayoutManager loadLayoutManager();

    protected abstract void init();

    @Override
    protected int loadContentView() {
        return R.layout.base_recycler_view_layout;
    }
}
