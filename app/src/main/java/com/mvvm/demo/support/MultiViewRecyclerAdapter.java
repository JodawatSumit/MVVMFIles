package com.mvvm.demo.support;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.support.widgets.WidgetsViewModel;

import java.util.List;

public class MultiViewRecyclerAdapter<T extends WidgetsViewModel> extends RecyclerView.Adapter<MultiViewRecyclerAdapter.ViewHolder> {

    private List<? extends T> list;

    public MultiViewRecyclerAdapter(List<? extends T> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).layoutId();
    }

    @Override
    public MultiViewRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding bind = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        return new ViewHolder<>(bind);
    }

    @Override
    public void onBindViewHolder(MultiViewRecyclerAdapter.ViewHolder holder, final int position) {
        final T model = list.get(position);
        holder.getBinding().setVariable(BR.widget, model);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private V v;

        public ViewHolder(V v) {
            super(v.getRoot());
            this.v = v;
        }

        public V getBinding() {
            return v;
        }
    }

}