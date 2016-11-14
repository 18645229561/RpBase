package com.renpeng.widge;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rpbase.R;

import java.util.List;

/**
 * Created by renpeng on 16/11/14.
 */
public class BannerView extends LinearLayout {

    private ViewPager mViewPager;

    private BannerAdapter mBannerAdapter;

    private List<View> viewList;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.base_banner_layout,this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBannerAdapter = new BannerAdapter();
        mViewPager.setAdapter(mBannerAdapter);

    }

    public void setViewList(List<View> viewList){
        this.viewList = viewList;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged(){
        mBannerAdapter.notifyDataSetChanged();
    }


    class BannerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList != null?viewList.size():0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }

}
