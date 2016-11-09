package com.example.renpeng.rpbase;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.renpeng.base.AbsBaseNormalListActivity;
import com.renpeng.base.IitemContent;

import java.util.ArrayList;
import java.util.List;

public class TestDemoActivity extends AbsBaseNormalListActivity {

    List<String> list = new ArrayList<>();

    @Override
    protected void initBaseView() {
        list.add("fadfdasf");
        list.add("wwwww");
        list.add("tttttt");
        setDataList(list);
        setActivityStatus(CUSTOM_STATUS);
    }

    @Override
    protected IitemContent loadItemContent() {
        return new demoAdapter();
    }

    @Override
    protected void onBaseItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getBaseContext(),"onBaseItemClick",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBaseItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getBaseContext(),"onBaseItemLongClick",Toast.LENGTH_SHORT).show();
    }
}
