package com.vn.fa.adapter.multipleviewtype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by binhbt on 7/21/2016.
 */
abstract public class FaDataBinder<T> extends DataBinder<BinderViewHolder> implements IViewBinder{
    protected T data;
    protected DataBindAdapter mDataBindAdapter;
    protected int itemViewType =0;
    public FaDataBinder(T data){
        this.data = data;
        String className = getClassName();
        itemViewType = className.hashCode();
    }
    @Override
    public BinderViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResource(), parent, false);
        return newHolder(view);
    }
    abstract public int getLayoutResource();
    abstract public BinderViewHolder newHolder(View parent);
    @Override
    public int getItemCount() {
        return 1;
    }
    public T getData(){
        return data;
    }
    public FaDataBinder data(T data){
        this.data = data;
        return this;
    }
    public void setData(T data){
        this.data = data;
    }
    public FaDataBinder adapter(DataBindAdapter dataBindAdapter){
        this.mDataBindAdapter = dataBindAdapter;
        return this;
    }
    public DataBindAdapter getAdapter(){
        return mDataBindAdapter;
    }
    public DataBinder getViewBinder(){
        return this;
    }

    @Override
    public int getItemViewType(){
        return itemViewType;
    }
    public FaDataBinder itemViewType(int itemViewType){
        this.itemViewType = itemViewType;
        return this;
    }
    public static long longHash(String string) {
        long h = 98764321261L;
        int l = string.length();
        char[] chars = string.toCharArray();

        for (int i = 0; i < l; i++) {
            h = 31*h + chars[i];
        }
        return h;
    }
    private String getClassName(){
        Class<?> enclosingClass = getClass().getEnclosingClass();
        if (enclosingClass != null) {
            //System.out.println(enclosingClass.getName());
            return enclosingClass.getName();
        } else {
            //System.out.println(getClass().getName());
            return getClass().getName();
        }
    }
}
