package com.example.renpeng.rpbase;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.renpeng.base.AbsBaseActivity;
import com.renpeng.base.adapter.IBaseItemContent;
import com.renpeng.widge.BaseMapListView;
import com.renpeng.widge.entity.MapItemDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/9.
 */
public class BaseRecyclerViewDemoActivity extends AbsBaseActivity implements BaseMapListView.MapItem<String,String>{

    private BaseMapListView baseMapListView;

    @Override
    protected void initView() {
        baseMapListView = (BaseMapListView) findViewById(R.id.list);
        baseMapListView.setMapItem(this);
        setActivityStatus(SUCCESS_STATUS);

    }

    @Override
    protected int loadContentView() {
        return R.layout.test_new_listview;
    }


    @Override
    public List<MapItemDataEntity<String,String>> getMapList() {
        List<String> valueList = new ArrayList<>();
        for(int i = 0;i<500;i++){
            valueList.add(i+"");
        }

        List<MapItemDataEntity<String,String>> list = new ArrayList<>();
        for(int i = 0;i<50;i++){
            MapItemDataEntity<String,String> mapItemDataEntity = new MapItemDataEntity(i+"",valueList);
            list.add(mapItemDataEntity);
        }

        return list;
    }



    @Override
    public void onValueKeyClickListener(AdapterView<?> parent, View view, int position, long id ,int keyPosition) {
        Toast.makeText(this,"onValueKeyClickListener" + keyPosition + "" + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBaseItemContent getKeyItem() {
        return new demoAdapter();
    }

    @Override
    public IBaseItemContent getValueItem() {
        return new demoAdapter();
    }


}
