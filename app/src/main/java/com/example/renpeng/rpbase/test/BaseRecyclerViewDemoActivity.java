package com.example.renpeng.rpbase.test;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.renpeng.rpbase.R;
import com.renpeng.base.AbsBaseActivity;
import com.renpeng.widge.AbsPullContentView;
import com.renpeng.widge.BaseAbsRefreshList;

/**
 * Created by renpeng on 16/11/9.
 */
public class BaseRecyclerViewDemoActivity extends AbsBaseActivity{
    private BaseAbsRefreshList mRefreshList;
    String[] data = new String[20];



    @Override
    protected void initView() {
        setActivityStatus(SUCCESS_STATUS);
        mRefreshList = getViewById(R.id.refresh_list);
        initData();
        mRefreshList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data));
        mRefreshList.setOnRefreshListener(new AbsPullContentView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(BaseRecyclerViewDemoActivity.this,"refresh",Toast.LENGTH_SHORT).show();
                mRefreshList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshList.refreshComplete();
                    }
                }, 3000);
            }
        });
        mRefreshList.setOnLoadListener(new AbsPullContentView.OnLoadingListener() {
            @Override
            public void onLoading() {
                Toast.makeText(BaseRecyclerViewDemoActivity.this,"loading",Toast.LENGTH_SHORT).show();
                mRefreshList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshList.loadCompelte();
                    }
                }, 3000);
            }
        });
    }

    private void initData(){
        for(int i=0;i<20;i++){
            data[i] = i+"";
        }
    }

    @Override
    protected int loadContentView() {
        return R.layout.activity_main;
    }
}
