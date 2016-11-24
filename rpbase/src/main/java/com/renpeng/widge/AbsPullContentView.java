package com.renpeng.widge;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;

import com.example.rpbase.R;

/**
 * Created by renpeng on 16/11/22.
 */
public abstract class AbsPullContentView<T extends View> extends ViewGroup implements AbsListView.OnScrollListener{

    //头布局
    protected View mHeaderView;

    //底布局
    private View mFooterView;

    protected T mContentView;

    private Scroller mScroller;

    private LayoutInflater mLayoutInflater;

    //滑动的位移差值
    private int mOffsetY = 0;

    private int mHeaderHeight = 0;

    private int mPlayHeight = 0;

    //初始化界面布局位置
    private int mInitScrollY = 0;

    //最后一次触摸Y的坐标
    private int mLastY = 0;

    //当前Y坐标
    private int mCurrnetY = 0;

    private static final int STATUS_PULL_IDLE = 0;

    //下拉或者上啦没有达到可刷新的状态
    private static final int STATUS_PULL_TO_REFRESH = 1;

    //上拉或者下拉的状态
    private static final int STAUS_RELEASE_TO_REFRESH = 2;

    //刷新中
    private static final int STATUS_REFRESHING = 3;

    private static final int STATUS_LOADING = 4;

    private int mCurrentStatus = STATUS_PULL_IDLE;

    private OnRefreshListener mOnRefreshListener;

    private OnLoadingListener mOnLoadingListener;

    public AbsPullContentView(Context context) {
        super(context);
        init(context);
    }

    public AbsPullContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AbsPullContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mLayoutInflater = LayoutInflater.from(context);
        mScroller = new Scroller(context);
        mPlayHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        mHeaderHeight = mPlayHeight/4;

        setHeaderView();
        setPullContentView(context);
        setDefaultContentLayoutParams();
        addView(mContentView);
        setFooterView();

    }

    private void setHeaderView(){
        mHeaderView = mLayoutInflater.inflate(R.layout.base_pull_header_view,this,false);
        mHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,mHeaderHeight));
        mHeaderView.setBackgroundColor(Color.RED);
        mHeaderView.setPadding(0,mHeaderHeight - 100,0,0);
        addView(mHeaderView);
    }

    private void setFooterView(){
        mFooterView = mLayoutInflater.inflate(R.layout.base_pull_footer_view,this,false);
        addView(mFooterView);
    }

    protected void setDefaultContentLayoutParams() {
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }
    protected abstract void setPullContentView(Context context);


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Do not intercept touch event, let the child handle it
            return false;
        }

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // int yDistance = (int) ev.getRawY() - mYDown;
                mOffsetY = (int) ev.getRawY() - mLastY;
                // 如果拉到了顶部, 并且是下拉,则拦截触摸事件,从而转到onTouchEvent来处理下拉刷新事件
                if (isTop() && mOffsetY > 0) {
                    return true;
                }
                break;

        }
        // Do not intercept touch event, let the child handle it
        return false;
    }

    protected abstract boolean isTop();

    protected abstract boolean isBottom();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int currentY = (int) event.getRawY();
                mOffsetY = currentY - mLastY;
                if (mCurrentStatus != STATUS_LOADING) {
                    changeScrollY(mOffsetY);
                }

                mLastY = currentY;
                break;

            case MotionEvent.ACTION_UP:
                // 下拉刷新的具体操作
                deRefresh();
                break;
            default:
                break;
        }
        return true;
    }

    private void deRefresh(){
        changeHeaderViewStatus();
        if(mCurrentStatus == STATUS_REFRESHING && mOnRefreshListener != null){
            mOnRefreshListener.onRefresh();
        }

    }

    private void changeHeaderViewStatus(){
        int currScrollY = getScrollY();
        if(currScrollY < mInitScrollY/2){
            mScroller.startScroll(getScrollX(),currScrollY,0,mHeaderView.getPaddingTop() - currScrollY);
            mCurrentStatus = STATUS_REFRESHING;
        }else{
            mScroller.startScroll(getScrollX(),currScrollY,0,mInitScrollY - currScrollY);
            mCurrentStatus = STATUS_PULL_IDLE;
        }
        invalidate();
    }

    private void changeScrollY(int distance){
        int currY = getScrollY();
        if(distance > 0 && currY - distance > getPaddingTop()){//下拉
            scrollBy(0,-distance);
        }else if(distance < 0 && currY - distance <= mInitScrollY){//上拉
            scrollBy(0,-distance);
        }
        currY = getScrollY();
        int slop = mInitScrollY/2;
        if(currY > 0 && currY < slop){
            mCurrentStatus = STAUS_RELEASE_TO_REFRESH;
        }else if(currY > 0 && currY > slop){
            mCurrentStatus = STATUS_PULL_TO_REFRESH;
        }
    }

    public void refreshComplete(){
        mCurrentStatus = STATUS_PULL_IDLE;
        mScroller.startScroll(getScrollX(),getScrollY(),0,mInitScrollY - getScrollY());
        invalidate();
    }
    public void loadCompelte() {
        // 隐藏footer
        startScroll(mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_PULL_IDLE;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(mOnLoadingListener != null
                && isBottom()
                && mScroller.getCurrY() <= mInitScrollY
                && mOffsetY <= 0
                && mCurrentStatus == STATUS_PULL_IDLE){
            showFooterView();
            doLoadingMore();
        }
    }

    private void showFooterView(){
        startScroll(mInitScrollY - getScrollY());
        mCurrentStatus = STATUS_LOADING;
    }

    private void startScroll(int yOffset) {
        mScroller.startScroll(getScrollX(), getScrollY(), 0, yOffset);
        invalidate();
    }


    private void doLoadingMore(){
        if(mOnLoadingListener != null){
            mOnLoadingListener.onLoading();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int finalHeight = 0;
        int childCount = getChildCount();
        for(int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            finalHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(width,finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int top = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.layout(0, top, child.getMeasuredWidth(), child.getMeasuredHeight() + top);
            top += child.getMeasuredHeight();
        }

        // 计算初始化滑动的y轴距离
        mInitScrollY = mHeaderView.getMeasuredHeight() + getPaddingTop();
        // 滑动到header view高度的位置, 从而达到隐藏header view的效果
        scrollTo(0, mInitScrollY);
    }
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    /**
     * 设置滑动到底部时自动加载更多的监听器
     *
     * @param listener
     */
    public void setOnLoadListener(OnLoadingListener listener) {
        mOnLoadingListener = listener;
    }

    public T getContentView() {
        return mContentView;
    }

    /**
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * @return
     */
    public View getFooterView() {
        return mFooterView;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public interface OnRefreshListener{
        void onRefresh();
    }

    public interface OnLoadingListener{
        void onLoading();
    }


}
