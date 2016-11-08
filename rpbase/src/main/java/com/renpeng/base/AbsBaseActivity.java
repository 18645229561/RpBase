package com.renpeng.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rpbase.R;

/**
 * Created by renpeng on 16/11/7.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    public static final int SUCCESS_STATUS = 1;//成功可以展示该页面
    public static final int LOADING_STATUS = 2;//等待状态
    public static final int FAIL_STATUS = 3;//出错状态
    public static final int CUSTOM_STATUS = 4;//自定义状态

    private int contentView = 0;
    private int loadingContent = R.layout.base_loading_layout;
    private int failContent = R.layout.base_fail_layout;
    private int customContent = 0;

    private LinearLayout llContent;
    private int baseActivityStatus = LOADING_STATUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity_layout);
        llContent = (LinearLayout) findViewById(R.id.ll_base_content);
        setActivityStatus(SUCCESS_STATUS);
        initView();


    }

    public void setGitHub(){}
    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        onActivityResume();
    }

    protected void onActivityResume(){

    }


    protected void setActivityStatus(int activityStatus) {
        switch (activityStatus) {
            case SUCCESS_STATUS:
                contentView = loadContentView();
                break;
            case LOADING_STATUS:
                contentView = loadingContent;
                break;
            case FAIL_STATUS:
                contentView = failContent;
                break;
            case CUSTOM_STATUS:
                contentView = customContent;
                break;
            default:
                break;
        }
        setLoadContentView();
    }

    protected int getActivityStatus(){
        return baseActivityStatus;
    }

    private void setLoadContentView(){
        View childContent = LayoutInflater.from(this).inflate(contentView,null);
        llContent.removeAllViews();
        llContent.addView(childContent);
    }

    protected void setCustomContent(int customContent){
        this.customContent = customContent;
    }

    protected void setFailContent(int failContent){
        this.failContent = failContent;
    }

    protected void setLoadingContent(int loadingContent){
        this.loadingContent = loadingContent;
    }

    protected abstract int loadContentView();

}
