package com.vn.vega.adapter.multipleviewtype;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by binhbt on 7/21/2016.
 */
public class VegaBindAdapter extends ListBindAdapter {

    public VegaBindAdapter() {
    }
    public VegaBindAdapter listData(List<DataBinder> dataSet){
        if (dataSet == null){
            dataSet = new ArrayList<>();
        }
        this.mBinderList = dataSet;
        return this;
    }
//    public void addBinderData(List<DataBinder> dataSet){
//        if (dataSet != null){
//            addAllBinder(dataSet);
//            notifyDataSetChanged();
//        }
//    }
    public List<DataBinder> getBinderList(){
        return this.mBinderList;
    }
    public void setBinderList(List<DataBinder> dataSet){
        if (dataSet == null){
            dataSet = new ArrayList<>();
        }
        this.mBinderList = dataSet;
    }
    public void addDataObject(IViewBinder object, boolean isAdapterAttached){
        VegaDataBinder iViewBinder = (VegaDataBinder) object.getViewBinder();
        if (isAdapterAttached){
            iViewBinder.adapter(this);
        }
        if (object != null){
            add(iViewBinder);
        }
    }
    public void addDataObject(IViewBinder object){
        VegaDataBinder iViewBinder = (VegaDataBinder) object.getViewBinder();
        if (object != null){
            add(iViewBinder);
        }
    }
    public void addAllDataObject(List<IViewBinder> dataset){
        if (dataset != null){
            addAll(convertDataToViewBinder(dataset));
        }
    }
    public void addAllDataObject(List<IViewBinder> dataset, boolean isAdapterAttached){
        if (dataset != null){
            addAll(convertDataToViewBinder(dataset, isAdapterAttached));
        }
    }
    public void add(DataBinder binder) {
        insert(binder, mBinderList.size());
    }

    public void insert(DataBinder binder, int position) {
        mBinderList.add(position, binder);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mBinderList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = mBinderList.size();
        mBinderList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<DataBinder> dataSet) {
        int startIndex = mBinderList.size();
        addAllBinder(dataSet);
        notifyItemRangeInserted(startIndex, dataSet.size());
    }
    public static List<DataBinder> convertDataToViewBinder(List<IViewBinder> dataset){
        if (dataset == null) throw new IllegalArgumentException("List data can not null!");
        if (dataset.size() ==0) return new ArrayList<>();
        List<DataBinder> list = new ArrayList<>();
        for (IViewBinder viewBinder: dataset) {
            list.add(viewBinder.getViewBinder());
        }
        return list;
    }
    public List<DataBinder> convertDataToViewBinder(List<IViewBinder> dataset, boolean isAdapterAttached){
        if (dataset == null) throw new IllegalArgumentException("List data can not null!");
        if (dataset.size() ==0) return new ArrayList<>();
        List<DataBinder> list = new ArrayList<>();
        for (IViewBinder viewBinder: dataset) {
            VegaDataBinder iViewBinder = (VegaDataBinder) viewBinder.getViewBinder();
            if (isAdapterAttached){
                iViewBinder.adapter(this);
            }
            list.add(iViewBinder);
        }
        return list;
    }
    public DataBinder getItemView(int position){
        if (mBinderList != null && mBinderList.size() >position){
            return mBinderList.get(position);
        }
        return null;
    }
    public Object getItemData(int position){
        return ((VegaDataBinder)getItemView(position)).getData();
    }
    //Fix position bug
    @Override
    public int getBinderPosition(int position){
        return position;
    }

}