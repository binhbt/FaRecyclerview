package com.vn.fa.adapter.multipleviewtype;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Class for binding view and data
 *
 * Created by yqritc on 2015/03/01.
 */
abstract public class DataBinder<T extends RecyclerView.ViewHolder> {


    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position);

    abstract public int getItemCount();

    public abstract int getItemViewType();
}
