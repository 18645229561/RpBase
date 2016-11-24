package com.example.renpeng.rpbase.test;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.renpeng.rpbase.R;
import com.renpeng.base.AbsBaseActivity;
import com.renpeng.widge.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/11.
 */
public class RequestHttpTestActivity extends AbsBaseActivity {

    private BannerView mBannerView;

    private List<View> list = new ArrayList<>();

    @Override
    protected void initView() {
//        mBannerView = getViewById(R.id.banner);
        initPagerView();
        mBannerView.setViewList(list);
        setActivityStatus(SUCCESS_STATUS);
    }

    private void initPagerView(){
        for(int i = 1;i<=4;i++){
            TextView textView = new TextView(this);
            if(i == 1){
                textView.setBackgroundColor(Color.BLUE);
            }else if(i == 2){
                textView.setBackgroundColor(Color.RED);
            }else if(i == 3){
                textView.setBackgroundColor(Color.GRAY);
            }else if(i == 4){
                textView.setBackgroundColor(Color.GREEN);
            }

            textView.setHeight(200);
            textView.setText(i+"");
            list.add(textView);
        }

    }
    @Override
    protected int loadContentView() {
        return R.layout.activity_main;
    }
}
