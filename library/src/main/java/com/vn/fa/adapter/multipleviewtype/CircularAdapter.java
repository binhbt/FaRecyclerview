package com.vn.fa.adapter.multipleviewtype;

import android.support.v7.widget.RecyclerView;

/**
 * Created by leobui on 4/13/2017.
 */

public class CircularAdapter extends VegaBindAdapter{
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        int positionInList = position % mBinderList.size();
        super.onBindViewHolder(viewHolder, positionInList);
    }
    @Override
    public int getItemViewType(int position) {
        return position%mBinderList.size();
    }
}
