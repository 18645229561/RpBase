package com.renpeng.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.renpeng.base.adapter.IBaseItemContent;

import java.util.List;

/**
 * Created by renpeng on 16/11/10.
 */
public class BaseListView extends LinearLayout {

    private ListView listView;

    private ListAdapter listAdapter;

    private List dataList;

    private IBaseItem iBaseItem;

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private  void setListView(Context context){
        listView = new ListView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        listAdapter = new ListAdapter();
        listView.setLayoutParams(params);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iBaseItem.onClick();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                iBaseItem.onLongClick();
                return false;
            }
        });
        addView(listView);
    }


    private void setDataList(List dataList){
        this.dataList = dataList;
    }

    public void update(){
        listAdapter.notifyDataSetChanged();
    }

    public void initRpBaseListView(Context context,IBaseItem iBaseItem,List dataList){
        setIBaseItem(iBaseItem);
        setDataList(dataList);
        setListView(context);
    }

    private void setIBaseItem(IBaseItem iBaseItem){
        this.iBaseItem = iBaseItem;
    }
    private  class ListAdapter extends BaseAdapter {

        public ListAdapter(){

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getViewTypeCount() {
            return iBaseItem.getBaseViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return iBaseItem.getBaseItemViewType(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IBaseItemContent IBaseItemContent = null;
            if(convertView == null){
                if(getViewTypeCount() == 1){
                    IBaseItemContent = iBaseItem.loadItemContent();
                }else{
                    IBaseItemContent = iBaseItem.loadItemContentByType(getItemViewType(position));
                }
                convertView = LayoutInflater.from(parent.getContext()).inflate(IBaseItemContent.getView(),null);
                IBaseItemContent.initView(convertView);
                convertView.setTag(IBaseItemContent);
            }

            if(IBaseItemContent == null){
                IBaseItemContent = (IBaseItemContent) convertView.getTag();
            }

            if(dataList != null && dataList.size() > 0 && dataList.get(position) != null){
                IBaseItemContent.bindData(dataList.get(position));
            }

            return convertView;
        }

    }

    public interface IBaseItem{
        IBaseItemContent loadItemContent();
        IBaseItemContent loadItemContentByType(int type);
        int getBaseViewTypeCount();
        int getBaseItemViewType(int position);
        void onClick();
        void onLongClick();

    }
}
