package com.example.renpeng.rpbase.test;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renpeng.rpbase.R;
import com.renpeng.base.adapter.IBaseRecyclerViewItem;

/**
 * Created by renpeng on 16/11/9.
 */
public class ItemRecycleView implements IBaseRecyclerViewItem<String> {
    Activity activity;
    TextView textView;
    public ItemRecycleView(Activity activity){
        this.activity = activity;
    }
    @Override
    public void initView(View convertView) {
        textView = (TextView) convertView.findViewById(R.id.rr);
    }

    @Override
    public int getView() {
        return R.layout.item;
    }

    @Override
    public void bindData(String s) {
        textView.setText(s);
    }

    @Override
    public void onClick() {
        Toast.makeText(activity,"onClick",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick() {
        Toast.makeText(activity,"onLongClick",Toast.LENGTH_SHORT).show();
    }
}
