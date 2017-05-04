package com.example.recyclerviewcommadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created on 2017/5/3.
 * author lipengzhang
 */

public abstract class RecyclerViewCommAdapter<T> extends RecyclerView.Adapter<RecyclerViewCommAdapter.RecyclerViewCommViewHolder> {
    private Context context;
    // 为丰富程序功能，提供了两种常见的数据类型
    private List<T> dataList = null;// 数据源List<T>
    private T[] dataArray = null;// 数据源T[]
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public RecyclerViewCommAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public RecyclerViewCommAdapter(T[] dataArray, Context context) {
        this.dataArray = dataArray;
        this.context = context;
    }

    @Override
    public RecyclerViewCommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layOutId = getMultiItemId(viewType);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layOutId, parent, false);
        return new RecyclerViewCommViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCommViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewItemClickListener != null){
                    recyclerViewItemClickListener.onItemClickListener(holder.itemView,position);
                }

            }
        });
        convert(holder,getItem(position),position,getItemViewType(position));
    }

    @Override
    public int getItemViewType(int position) {
       return getMultiItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return dataArray.length;
        }
    }

    /**
     * 根据viewType返回item布局id
     * @param viewType
     * @return
     */
    public abstract int getMultiItemId(int viewType);

    /**
     * 根据position设置item布局的类型
     * @param position
     * @return
     */
    public abstract int getMultiItemViewType(int position);

    /**
     * 对onBindViewHolder数据绑定的重新封装
     * @param holder
     * @param model
     * @param position
     * @param viewType
     */
    public abstract void convert(RecyclerViewCommViewHolder holder, T model,int position,int viewType);
    /**
     * 获取Item数据
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        } else {
            return dataArray[position];
        }
    }

    /**
     * 设置RecyclerView的条目点击事件接口
     * @param recyclerViewItemClickListener
     */
    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }
    /**
     * 自定义通用ViewHolder
     */
    public static class RecyclerViewCommViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;//itemView中各个子view的缓存集合

        public RecyclerViewCommViewHolder(View itemView) {
            super(itemView);
            this.mViews = new SparseArray<View>();
        }

        /**
         * 通过id获取子view
         *
         * @param viewId
         * @param <>
         * @return
         */
        public <M extends View> M getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (M) view;
        }
    }

    public interface RecyclerViewItemClickListener{
        void onItemClickListener(View view, int position);
    }
}
