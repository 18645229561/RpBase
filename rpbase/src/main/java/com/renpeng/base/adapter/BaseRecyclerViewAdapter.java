package com.renpeng.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renpeng.base.AbsBaseRecyclerViewActivity;

import java.util.List;

/**
 * Created by renpeng on 16/11/9.
 */
public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List dataList;
    private AbsBaseRecyclerViewActivity mAbsBaseRecyclerViewActivity;

    private IBaseRecyclerViewItem iBaseRecyclerViewItem;


    public BaseRecyclerViewAdapter(AbsBaseRecyclerViewActivity mAbsBaseRecyclerViewActivity){
        this.mAbsBaseRecyclerViewActivity = mAbsBaseRecyclerViewActivity;
    }

    public void setDataList(List dataList){
        this.dataList = dataList;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        iBaseRecyclerViewItem = mAbsBaseRecyclerViewActivity.loadIBaseRecyclerViewItem();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(iBaseRecyclerViewItem.getView(),null);
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(itemView,iBaseRecyclerViewItem);
        setOnClickListener(itemView);

        return baseRecyclerViewHolder;
    }

    private void setOnClickListener(View itemView){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBaseRecyclerViewItem.onClick();
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iBaseRecyclerViewItem.onLongClick();
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.getIBaseRecyclerViewItem().bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnClickItemListener{
        void onClick();

        void onLongClick();
    }
}
