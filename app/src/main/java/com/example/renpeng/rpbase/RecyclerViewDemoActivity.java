package com.example.renpeng.rpbase;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.renpeng.base.AbsRecyclerViewActivity;
import com.renpeng.base.adapter.IBaseRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/9.
 */
public class RecyclerViewDemoActivity extends AbsRecyclerViewActivity {
    @Override
    public IBaseRecyclerViewItem loadIBaseRecyclerViewItem() {
        return new ItemRecycleView(this);
    }

    @Override
    protected RecyclerView.LayoutManager loadLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected void init() {
        initData();
        setDataList(list);
        setActivityStatus(SUCCESS_STATUS);
    }

    List<String> list = new ArrayList<>();
    private void initData(){
        for(int i = 0;i<500;i++){
            list.add(i+"");
        }
    }
}
