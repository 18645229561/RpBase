package com.example.renpeng.rpbase.test;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.renpeng.rpbase.R;
import com.renpeng.base.adapter.IBaseItemContent;

/**
 * Created by renpeng on 16/11/8.
 */
public class demoAdapter implements IBaseItemContent<String> {
    TextView textView;
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
        textView.setTextColor(Color.BLUE);
        textView.setText(s);
    }
}
