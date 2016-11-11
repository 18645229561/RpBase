package com.example.renpeng.rpbase;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.renpeng.base.adapter.IBaseItemContent;

/**
 * Created by renpeng on 16/11/10.
 */
public class NewType implements IBaseItemContent<String> {
    TextView textView;
    @Override
    public void initView(View convertView) {

        textView = (TextView) convertView.findViewById(R.id.fdfd);
    }

    @Override
    public int getView() {
        return R.layout.a_type_item;
    }

    @Override
    public void bindData(String s) {
        textView.setText(s);
        textView.setTextColor(Color.RED);
    }
}
