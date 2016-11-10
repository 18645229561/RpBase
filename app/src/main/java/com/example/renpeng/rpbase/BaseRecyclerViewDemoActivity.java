package com.example.renpeng.rpbase;

import android.widget.Toast;

import com.renpeng.base.AbsListBaseActivity;
import com.renpeng.base.adapter.IBaseItemContent;
import com.renpeng.widge.BaseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/9.
 */
public class BaseRecyclerViewDemoActivity extends AbsListBaseActivity {
    BaseListView baseListView;

    @Override
    protected void initView() {
        initData();
        setActivityStatus(SUCCESS_STATUS);
        baseListView = (BaseListView) findViewById(R.id.new_list);
        baseListView.initRpBaseListView(this,this,list);

    }

    List<String> list = new ArrayList<>();

    private void initData(){
        for(int i = 0;i<500;i++){
            list.add(i+"");
        }
    }

    @Override
    protected int loadContentView() {
        return R.layout.test_new_listview;
    }

    @Override
    public IBaseItemContent loadItemContent() {
        return new demoAdapter();
    }

    @Override
    public IBaseItemContent loadItemContentByType(int type) {
        return null;
    }

    @Override
    public int getBaseViewTypeCount() {
        return 1;
    }

    @Override
    public int getBaseItemViewType(int position) {
        return 0;
    }

    @Override
    public void onClick() {
        Toast.makeText(this,"onClick",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick() {
        Toast.makeText(this,"onLongClick",Toast.LENGTH_SHORT).show();
    }
}
