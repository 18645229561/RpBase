package com.example.renpeng.rpbase.test;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.renpeng.rpbase.R;
import com.example.renpeng.rpbase.util.RequestUtil;
import com.renpeng.base.AbsBaseActivity;

/**
 * Created by renpeng on 16/11/11.
 */
public class RequestHttpTestActivity extends AbsBaseActivity {

    TextView textView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(request);
            setActivityStatus(SUCCESS_STATUS);
        }
    };

    String request;


    @Override
    protected void initView() {
        textView = getViewById(R.id.text);
        request("https://www.baidu.com");

    }

    public void request(final String str){
        new Thread(new Runnable() {
            @Override
            public void run() {
                request = RequestUtil.requestHttpUrlConnection(str);
                Message message = new Message();
                message.arg1 = 111;
                handler.sendMessage(message);
            }
        }).start();

    }

    @Override
    protected int loadContentView() {
        return R.layout.activity_main;
    }
}
