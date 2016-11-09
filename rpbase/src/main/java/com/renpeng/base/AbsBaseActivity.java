package com.renpeng.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import com.example.rpbase.R;

/**
 * Created by renpeng on 16/11/7.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    public static final int SUCCESS_STATUS = 1;//成功可以展示该页面
    public static final int LOADING_STATUS = 2;//等待状态
    public static final int ERROR_STATUS = 3;//出错状态
    public static final int EMPTY_STATUS = 5;// 空数据
    public static final int CUSTOM_STATUS = 4;//自定义状态

    private int contentView = 0;
    private int loadingContent = R.layout.base_loading_layout;
    private int errorContent = R.layout.base_fail_layout;
    private int emptyContent = R.layout.base_empty_layout;
    private int customContent = R.layout.base_custom_layout;

    private ViewAnimator mViewAnimator;
    private int baseActivityStatus = LOADING_STATUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity_layout);
        mViewAnimator = (ViewAnimator) findViewById(R.id.parent_container);

        initStatusView();

        initView();


    }

    protected <T extends View> T getViewById(int resId){
        return (T)findViewById(resId);
    }

    private void initStatusView(){
        View loadingView = LayoutInflater.from(this).inflate(loadingContent,null);
        initLoadingView(loadingView);
        mViewAnimator.addView(loadingView);

        View contentView = LayoutInflater.from(this).inflate(loadContentView(),null);
        initContentView(contentView);
        mViewAnimator.addView(contentView);

        View errorView = LayoutInflater.from(this).inflate(errorContent,null);
        errorFailView(errorView);
        mViewAnimator.addView(errorView);

        View emptyView = LayoutInflater.from(this).inflate(emptyContent,null);
        initEmptyView(emptyView);
        mViewAnimator.addView(emptyView);

        View customView = LayoutInflater.from(this).inflate(customContent,null);
        initCustomView(customView);
        mViewAnimator.addView(customView);
    }

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
            case  LOADING_STATUS:
                mViewAnimator.setDisplayedChild(0);
                break;
            case SUCCESS_STATUS:
                mViewAnimator.setDisplayedChild(1);
                break;
            case ERROR_STATUS:
                mViewAnimator.setDisplayedChild(2);
                break;
            case EMPTY_STATUS:
                mViewAnimator.setDisplayedChild(3);
                break;
            case CUSTOM_STATUS:
                mViewAnimator.setDisplayedChild(4);
                break;
            default:
                break;
        }
    }

    protected int getActivityStatus(){
        return baseActivityStatus;
    }


    protected void setCustomContent(int customContent){
        this.customContent = customContent;
    }

    protected void initCustomView(View view){}

    protected void setErrorContent(int errorContent){
        this.errorContent = errorContent;
    }

    protected void errorFailView(View view){}

    protected void setLoadingContent(int loadingContent){
        this.loadingContent = loadingContent;
    }

    protected void initLoadingView(View view){}

    protected abstract int loadContentView();

    protected void initContentView(View view){}

    protected void setEmptyContent(int emptyContent){
        this.emptyContent = emptyContent;
    }

    protected void initEmptyView(View view){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onBaseActivityDestory();

    }

    protected void onBaseActivityDestory(){}
}
