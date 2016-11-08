package com.renpeng.base;

import android.view.View;

/**
 * Created by renpeng on 16/11/8.
 */
public  interface IitemContent<T> {

    void initView(View convertView);

    int getView();

    void bindData(T t);

}
