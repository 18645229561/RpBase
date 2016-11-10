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

import com.example.rpbase.R;
import com.renpeng.base.adapter.IBaseItemContent;
import com.renpeng.widge.entity.MapItemDataEntity;

import java.util.List;

/**
 * Created by renpeng on 16/11/10.
 */
public class BaseMapListView extends LinearLayout {

    private ListView keyListView;

    private ListView valueListView;

    private MapKeyAdapter keyMapListAdapter;

    private MapValueAdapter valueMapListAdapter;

    private MapItem mMapItem;

    private List<MapItemDataEntity> list;

    private int keyClickPosition = 0;


    public BaseMapListView(Context context) {
        super(context);
        init();
    }

    public BaseMapListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseMapListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        View.inflate(getContext(),R.layout.base_map_list_layout,this);
        keyListView = (ListView) findViewById(R.id.key_list);
        valueListView = (ListView) findViewById(R.id.value_list);
        keyMapListAdapter = new MapKeyAdapter();
        valueMapListAdapter = new MapValueAdapter();
        keyListView.setAdapter(keyMapListAdapter);
        valueListView.setAdapter(valueMapListAdapter);

        keyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyClickPosition = position;
                valueMapListAdapter.notifyDataSetChanged();
                valueListView.setSelection(0);
            }
        });

        valueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMapItem.onValueKeyClickListener(parent,view,position,id,keyClickPosition);
            }
        });
    }

    public void setMapItem(MapItem mMapItem){
        this.mMapItem = mMapItem;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged(){
        list = mMapItem.getMapList();
        keyMapListAdapter.notifyDataSetChanged();
        valueMapListAdapter.notifyDataSetChanged();
    }

    class MapKeyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            int size = 0;
            if(list != null && list.size() >0){
                size = list.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position).k;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IBaseItemContent IBaseItemContent = null;
            if(convertView == null){
                if(getViewTypeCount() == 1){
                    IBaseItemContent = mMapItem.getKeyItem();
                }
//                else{
//                    IBaseItemContent = loadItemContentByType(getItemViewType(position));
//                }
                convertView = LayoutInflater.from(parent.getContext()).inflate(IBaseItemContent.getView(),null);
                IBaseItemContent.initView(convertView);
                convertView.setTag(IBaseItemContent);
            }

            if(IBaseItemContent == null){
                IBaseItemContent = (IBaseItemContent) convertView.getTag();
            }
            IBaseItemContent.bindData(list.get(position).k);
            return convertView;
        }
    }

    class MapValueAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            int size = 0;
            if(list != null && list.get(keyClickPosition) != null && list.get(keyClickPosition).list != null
                    && list.get(keyClickPosition).list.size() > 0){
                size = list.get(keyClickPosition).list.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return list.get(keyClickPosition).list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IBaseItemContent IBaseItemContent = null;
            if(convertView == null){
                if(getViewTypeCount() == 1){
                    IBaseItemContent = mMapItem.getValueItem();
                }
//                else{
//                    IBaseItemContent = loadItemContentByType(getItemViewType(position));
//                }
                convertView = LayoutInflater.from(parent.getContext()).inflate(IBaseItemContent.getView(),null);
                IBaseItemContent.initView(convertView);
                convertView.setTag(IBaseItemContent);
            }

            if(IBaseItemContent == null){
                IBaseItemContent = (IBaseItemContent) convertView.getTag();
            }

            IBaseItemContent.bindData(list.get(keyClickPosition).list.get(position));

            return convertView;
        }
    }

    public interface MapItem<K,T>{
        List<MapItemDataEntity<K,T>> getMapList();

        void onValueKeyClickListener(AdapterView<?> parent, View view, int position, long id,int keyPosition);

        IBaseItemContent getKeyItem();

        IBaseItemContent getValueItem();

    }
}
