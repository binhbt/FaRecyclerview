package com.vn.fa.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.vn.fa.superrecyclerview.R;

/**
 * Created by binhbt on 6/27/2016.
 */
public class FaRecyclerView extends RelativeLayout{
    public static final int TYPE_LIST = 0x0;
    public static final int TYPE_GRID = 0x01000000;
    public static final int TYPE_STAGGED = 0x02000000;
    public static final int ORIENTATION_VERTICAL = 0x0;
    public static final int ORIENTATION_HORIZONTAL = 0x01000000;
    protected SuperRecyclerView mRecycler;

    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mEmptyId;
    protected int mMoreProgressId;
    protected int mProgressId;

    protected boolean isPullToRefresh;
    protected boolean isLoadmore;
    protected boolean isProgressInCenter;
    protected int mType;
    protected int mOrientation;
    protected int mSpanCount;

    protected boolean isVerticalScrollBarEnabled;
    protected boolean isHorizontalScrollBarEnabled;

    protected boolean isVerticalWrapContent;
    protected boolean isHorizontalWrapContent;
    public FaRecyclerView(Context context) {
        super(context);
    }

    public FaRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public FaRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }
    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FaRecyclerView);
        try {
            mClipToPadding = a.getBoolean(R.styleable.FaRecyclerView_fa_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.FaRecyclerView_fa_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.FaRecyclerView_fa_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.FaRecyclerView_fa_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.FaRecyclerView_fa_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.FaRecyclerView_fa_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = a.getInt(R.styleable.FaRecyclerView_fa_scrollbarStyle, -1);
            mEmptyId = a.getResourceId(R.styleable.FaRecyclerView_fa_layout_empty, 0);
            mMoreProgressId = a.getResourceId(R.styleable.FaRecyclerView_fa_layout_moreProgress, 0);
            mProgressId = a.getResourceId(R.styleable.FaRecyclerView_fa_layout_progress, 0);
            //fa
            isPullToRefresh = a.getBoolean(R.styleable.FaRecyclerView_fa_pull_to_refresh, true);
            isLoadmore = a.getBoolean(R.styleable.FaRecyclerView_fa_load_more, true);
            isProgressInCenter = a.getBoolean(R.styleable.FaRecyclerView_fa_progress_in_center, true);
            mOrientation = (int) a.getInt(R.styleable.FaRecyclerView_fa_orientation, ORIENTATION_VERTICAL);
            mType = (int) a.getInt(R.styleable.FaRecyclerView_fa_type, TYPE_LIST);
            mSpanCount = (int) a.getInt(R.styleable.FaRecyclerView_fa_spanCount, -1);

            isVerticalScrollBarEnabled = a.getBoolean(R.styleable.FaRecyclerView_fa_vertical_scrollbarEnabled, true);
            isHorizontalScrollBarEnabled = a.getBoolean(R.styleable.FaRecyclerView_fa_horizontal_scrollbarEnabled, true);

            isVerticalWrapContent = a.getBoolean(R.styleable.FaRecyclerView_fa_vertical_wrap_content, false);
            isHorizontalWrapContent = a.getBoolean(R.styleable.FaRecyclerView_fa_horizontal_wrap_content, false);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        //vertical
        if (mOrientation == ORIENTATION_VERTICAL){
            if (isVerticalWrapContent){
                LayoutInflater.from(getContext()).inflate(R.layout.fa_layout_vertical_wrap_content, this);
            }else {
                LayoutInflater.from(getContext()).inflate(R.layout.fa_layout_vertical, this);
            }
        }else{
            //horizontal
            if (isHorizontalWrapContent){
                LayoutInflater.from(getContext()).inflate(R.layout.fa_layout_horizontal_main_wrap_content, this);
            }else {
                LayoutInflater.from(getContext()).inflate(R.layout.fa_layout_horizontal, this);
            }
        }
        mRecycler = (SuperRecyclerView) findViewById(R.id.super_list);
        mRecycler.supportLoadMore(isLoadmore);
        //Transfer to superRecyclerView
        transferAttrsToSuperRecyclerView(mClipToPadding, mPadding,
                mPaddingTop, mPaddingBottom,
                mPaddingLeft, mPaddingRight,
                mScrollbarStyle, mEmptyId,
                mMoreProgressId, mProgressId);
        mRecycler.getRecyclerView().setVerticalScrollBarEnabled(isVerticalScrollBarEnabled);
        mRecycler.getRecyclerView().setHorizontalScrollBarEnabled(isHorizontalScrollBarEnabled);
        mRecycler.showLoading(isProgressInCenter);
        if (!isPullToRefresh || mOrientation == ORIENTATION_HORIZONTAL){
            mRecycler.disableRefresh();
        }
        //list
        if(mType == TYPE_LIST){
            setLayoutManager(new LinearLayoutManager(getContext(),
                    mOrientation==ORIENTATION_HORIZONTAL?LinearLayoutManager.HORIZONTAL:LinearLayoutManager.VERTICAL,
                    false));
        }
        //grid
        if (mType == TYPE_GRID){
            if (mSpanCount != -1){
                setLayoutManager(new GridLayoutManager(getContext(),
                        mSpanCount, mOrientation==ORIENTATION_HORIZONTAL?GridLayoutManager.HORIZONTAL:GridLayoutManager.VERTICAL, false));
            }
        }
        //stagged
        if (mType == TYPE_STAGGED){
            if (mSpanCount != -1){
                setLayoutManager(new StaggeredGridLayoutManager(mSpanCount,
                        mOrientation==ORIENTATION_HORIZONTAL?StaggeredGridLayoutManager.HORIZONTAL:StaggeredGridLayoutManager.VERTICAL));
            }
        }
        if (isHorizontalWrapContent || isVerticalWrapContent){
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                    isHorizontalWrapContent? LayoutParams.WRAP_CONTENT:RelativeLayout.LayoutParams.MATCH_PARENT,
                    isVerticalWrapContent? LayoutParams.WRAP_CONTENT:RelativeLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(rlp);
            getSuperRecyclerView().setLayoutParams(rlp);
            getRecyclerView().setLayoutParams(rlp);
            View rootContentView =findViewById(R.id.root_content);
            if (rootContentView != null){
                FrameLayout.LayoutParams rlp1 = new FrameLayout.LayoutParams(
                        isHorizontalWrapContent? LayoutParams.WRAP_CONTENT:RelativeLayout.LayoutParams.MATCH_PARENT,
                        isVerticalWrapContent? LayoutParams.WRAP_CONTENT:RelativeLayout.LayoutParams.MATCH_PARENT);
                rootContentView.setLayoutParams(rlp1);
            }
        }

    }
    private void transferAttrsToSuperRecyclerView(boolean mClipToPadding, int mPadding,
                                                  int mPaddingTop, int mPaddingBottom,
                                                  int mPaddingLeft, int mPaddingRight,
                                                  int mScrollbarStyle, int mEmptyId,
                                                  int mMoreProgressId, int mProgressId){
        mRecycler.transferAttrsToSuperRecyclerView(mClipToPadding, mPadding,
                mPaddingTop, mPaddingBottom,
                mPaddingLeft, mPaddingRight,
                mScrollbarStyle, mEmptyId,
                mMoreProgressId, mProgressId);

    }
    public void setProgressInCenter(boolean isShow){
        isProgressInCenter = isShow;
    }
    public boolean isProgressInCenter(){
        return isProgressInCenter;
    }
    public void disableRefresh(){
        mRecycler.disableRefresh();
    }
    public SuperRecyclerView getSuperRecyclerView(){
        return mRecycler;
    }
    public RecyclerView getRecyclerView(){
        return mRecycler.getRecyclerView();
    }
    /**
     * Set the layout manager to the recycler
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }

    /**
     * @return inflated progress view or null
     */
    public View getProgressView() {
        return mRecycler.getProgressView();
    }

    /**
     * @return inflated more progress view or null
     */
    public View getMoreProgressView() {
        return mRecycler.getMoreProgressView();
    }

    /**
     * @return inflated empty view or null
     */
    public View getEmptyView() {
        return mRecycler.getEmptyView();
    }
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecycler.setAdapter(adapter);
    }
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mRecycler.setRefreshListener(listener);
    }
    /**
     * Set the colors for the SwipeRefreshLayout states
     */
    public void setRefreshingColorResources(@ColorRes int colRes1, @ColorRes int colRes2, @ColorRes int colRes3, @ColorRes int colRes4) {
       mRecycler.setRefreshingColorResources(colRes1, colRes2, colRes3, colRes4);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     */
    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mRecycler.setRefreshingColor(col1, col2, col3, col4);
    }

    /**
     * Hide the progressbar
     */
    public void hideProgress() {
        mRecycler.hideProgress();
    }

    /**
     * Hide the recycler
     */
    public void hideRecycler() {
        mRecycler.setVisibility(View.GONE);
    }

    /**
     * Set the scroll listener for the recycler
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecycler.setOnScrollListener(listener);
    }

    /**
     * Add the onItemTouchListener for the recycler
     */
    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }

    /**
     * @return the recycler adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecycler.getAdapter();
    }

    /**
     * Sets the More listener
     *
     * @param max Number of items before loading more
     */
    public void setupMoreListener(OnMoreListener onMoreListener, int max) {
        mRecycler.setupMoreListener(onMoreListener, max);
    }
    public void setupMoreListener(OnMoreListener onMoreListener) {
        mRecycler.setupMoreListener(onMoreListener, 1);
    }
    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mRecycler.setOnMoreListener(onMoreListener);
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        mRecycler.setNumberBeforeMoreIsCalled(max);
    }

    public boolean isLoadingMore() {
        return mRecycler.isLoadingMore();
    }

    /**
     * Enable/Disable the More event
     */
    public void setLoadingMore(boolean isLoadingMore) {
        mRecycler.setLoadingMore(isLoadingMore);
    }

    /**
     * Remove the moreListener
     */
    public void removeMoreListener() {
        mRecycler.removeMoreListener();
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }

    public void setupSwipeToDismiss(final SwipeDismissRecyclerViewTouchListener.DismissCallbacks listener) {
        mRecycler.setupSwipeToDismiss(listener);
    }
    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecycler.getRecyclerView().setItemAnimator(animator);
    }
    public void showLoading(boolean isShow){
        mRecycler.showLoading(isShow);
    }
    public void showLoadMore(boolean isShow){
        mRecycler.showLoadMore(isShow);
    }
    public void showProgress() {
        mRecycler.showProgress();
    }

    /**
     * Hide the progressbar and show the recycler
     */
    public void showRecycler() {
        mRecycler.showRecycler();
    }

    public void showMoreProgress() {
        mRecycler.showMoreProgress();
    }

    public void hideMoreProgress() {
        mRecycler.hideMoreProgress();
    }

    public void setRefreshing(boolean refreshing) {
        mRecycler.setRefreshing(refreshing);
    }
    public void supportLoadMore(boolean supportLoadMore){
        this.isLoadmore = supportLoadMore;
        mRecycler.supportLoadMore(supportLoadMore);
    }
    public boolean isSupportLoadMore(){
        return this.isLoadmore;
    }
    public void endData(){
        this.isLoadmore = false;
        mRecycler.endData();
    }
    public void setHorizontalScrollBarEnabled(boolean isEnabled){
        getRecyclerView().setHorizontalScrollBarEnabled(isEnabled);
    }
    public void setVerticalScrollBarEnabled(boolean isEnabled){
        getRecyclerView().setVerticalScrollBarEnabled(isEnabled);
    }
    public void showEmptyView(boolean isShow){
        mRecycler.getEmptyStub().setVisibility(isShow?View.VISIBLE:View.GONE);
    }
}
