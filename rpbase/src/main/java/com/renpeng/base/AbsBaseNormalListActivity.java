package com.renpeng.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.rpbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renpeng on 16/11/7.
 */
public abstract class AbsBaseNormalListActivity extends AbsBaseActivity {

    private ListView listView;

    private List dataList = new ArrayList();

    private ListAdapter listAdapter = new ListAdapter();
    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.base_normal_list);

        setListClickEvent();
        listView.setAdapter(listAdapter);

        initBaseView();
    }

    protected abstract void initBaseView();

    protected abstract IitemContent loadItemContent();



    private void setListClickEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onBaseItemClick(parent,view,position,id);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onBaseItemLongClick(parent,view,position,id);
                return false;
            }
        });
    }

    protected IitemContent loadItemContentByType(int itemType){
        return null;
    }

    protected int getBaseViewTypeCount(){
        return 1;
    }

    protected int getBaseItemViewType(int position){
        return 1;
    }

    protected void setDataList(List dataList){
        this.dataList = dataList;
        updateList();

    }

    protected void updateList(){
        listAdapter.notifyDataSetChanged();
        listView.requestLayout();
    }

    protected void setlistHeader(View view){
        if(view != null){
            listView.addHeaderView(view);
        }
    }

    protected void setListFooter(View view){
        if(view != null){
            listView.addFooterView(view);
        }
    }


    @Override
    protected int loadContentView() {
        return R.layout.base_normal_list_layout;
    }

    protected ListView getListView(){
        return listView;
    }

    protected abstract void onBaseItemClick(AdapterView<?> parent, View view, int position, long id);

    protected abstract void onBaseItemLongClick(AdapterView<?> parent, View view, int position, long id);

    private  class ListAdapter extends BaseAdapter{

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
            return getBaseViewTypeCount();
        }

        @Override
        public int getItemViewType(int position) {
            return getBaseItemViewType(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IitemContent iitemContent = null;
            if(convertView == null){
                if(getViewTypeCount() == 1){
                    iitemContent = loadItemContent();
                }else{
                    iitemContent = loadItemContentByType(getItemViewType(position));
                }
                convertView = LayoutInflater.from(getBaseContext()).inflate(iitemContent.getView(),null);
                iitemContent.initView(convertView);
                convertView.setTag(iitemContent);
            }

            if(iitemContent == null){
                iitemContent = (IitemContent) convertView.getTag();
            }

            if(dataList != null && dataList.size() > 0 && dataList.get(position) != null){
                iitemContent.bindData(dataList.get(position));
            }

            return convertView;
        }

    }
}
