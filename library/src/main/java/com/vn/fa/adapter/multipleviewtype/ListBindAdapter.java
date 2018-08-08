package com.vn.fa.adapter.multipleviewtype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adapter class for managing data binders when the order of binder is in sequence
 *
 * Created by yqritc on 2015/03/19.
 */
public class ListBindAdapter extends DataBindAdapter {

    protected List<DataBinder> mBinderList = new ArrayList<>();

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (int i = 0, size = mBinderList.size(); i < size; i++) {
            DataBinder binder = mBinderList.get(i);
            itemCount += binder.getItemCount();
        }
        return itemCount;
    }
    public int size(){
        return getItemCount();
    }
    @Override
    public int getItemViewType(int position) {

        return mBinderList.get(position).getItemViewType();
    }
    public <T extends DataBinder> T getDataBinderByPosition(int position){
        return (T) mBinderList.get(position);
    }

    @Override
    public <T extends DataBinder> T getDataBinder(int viewType) {
        //Log.e("size", "data size "+ mBinderList.size());
        for (DataBinder dataBinder:mBinderList
             ) {
            if (dataBinder.getItemViewType() == viewType){
                return  (T)dataBinder;
            }
        }
        return (T) mBinderList.get(0);
        //return (T) mBinderList.get(viewType);
    }

    @Override
    public int getPosition(DataBinder binder, int binderPosition) {

        return binderPosition;
    }

    @Override
    public int getBinderPosition(int position) {
        int binderItemCount;
        for (int i = 0, size = mBinderList.size(); i < size; i++) {
            binderItemCount = mBinderList.get(i).getItemCount();
            if (position - binderItemCount < 0) {
                break;
            }
            position -= binderItemCount;
        }
        return position;
    }

    @Override
    public void notifyBinderItemRangeChanged(DataBinder binder, int positionStart, int itemCount) {
        notifyItemRangeChanged(getPosition(binder, positionStart), itemCount);
    }

    @Override
    public void notifyBinderItemRangeInserted(DataBinder binder, int positionStart, int itemCount) {
        notifyItemRangeInserted(getPosition(binder, positionStart), itemCount);
    }

    @Override
    public void notifyBinderItemRangeRemoved(DataBinder binder, int positionStart, int itemCount) {
        notifyItemRangeRemoved(getPosition(binder, positionStart), itemCount);
    }

    public List<DataBinder> getBinderList() {
        return mBinderList;
    }

    public void addBinder(DataBinder binder) {
        mBinderList.add(binder);
    }

    public void addAllBinder(List<DataBinder> dataSet) {
        mBinderList.addAll(dataSet);
    }

    public void addAllBinder(DataBinder... dataSet) {
        mBinderList.addAll(Arrays.asList(dataSet));
    }
}
