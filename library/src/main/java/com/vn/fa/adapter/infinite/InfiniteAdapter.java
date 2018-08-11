package com.vn.fa.adapter.infinite;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vn.fa.adapter.multipleviewtype.DataBinder;
import com.vn.fa.adapter.multipleviewtype.IViewBinder;
import com.vn.fa.adapter.multipleviewtype.FaBindAdapter;
import com.vn.fa.superrecyclerview.R;

import java.util.List;

/**
 * Modified by leobui.
 * <p>
 * original version created by Saurabh on 6/2/16.
 * <p>
 * This supports a callback to notify when to load more items.
 * <p>
 * Implementing Activities/fragments should also call moreDataLoaded(int, int)
 * to inform the adapter that more data has been loaded.
 */
public class InfiniteAdapter extends FaBindAdapter {

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private static final int VIEW_TYPE_LOADING = -1;

    private boolean mShouldLoadMore = false;
    private boolean mIsLoading = false;
    // Used to indicate the infinite scrolling should be bottom up
    private boolean mIsReversedScrolling = false;
    private OnLoadMoreListener mLoadMoreListener;
    private int loadingLayout;

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return getLoadingViewHolder(parent);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    /**
     * Subclasses should override this method, to actually bind the view data,
     * but always call <code>super.onBindViewHolder(holder, position)</code>
     * to enable the adapter to calculate whether the load more callback should be invoked
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mShouldLoadMore && !mIsLoading) {
            int threshold = getVisibleThreshold();
            boolean hasReachedThreshold = mIsReversedScrolling ? position <= threshold
                    : position >= super.getItemCount() - threshold;
            if (hasReachedThreshold) {
                mIsLoading = true;
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore();
                }
            }
        }
        if (getItemViewType(position) !=VIEW_TYPE_LOADING){
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public final int getItemCount() {
        int actualCount = getCount();
        // So as to avoid nasty index calculations, having reversed scrolling does
        // not affect the item count.
        // The consequence of this is, while there is more data to load, the first item on
        // the list will be replaced by the loading view
        if (actualCount == 0 || !mShouldLoadMore || mIsReversedScrolling) {
            return actualCount;
        } else {
            return actualCount + 1;
        }
    }

    public int getCount() {
        return super.getItemCount();
    }

    public int size() {
        return getCount();
    }

    @Override
    public final int getItemViewType(int position) {
        if (isLoadingView(position)) {
            return VIEW_TYPE_LOADING;
        } else {
            int viewType = super.getItemViewType(position);
            if (viewType == VIEW_TYPE_LOADING) {
                throw new IndexOutOfBoundsException("0 index is reserved for the loading view");
            } else {
                return viewType;
            }
        }
    }

    private boolean isLoadingView(int position) {
        // For reversed scrolling, the loading view is always the top one
        int loadingViewPosition = mIsReversedScrolling ? 0 : getCount();
        return position == loadingViewPosition && mShouldLoadMore;
    }

    /**
     * Set as false when you don't want the recycler view to load more data.
     * This will also remove the loading view
     */
    public void setShouldLoadMore(boolean shouldLoadMore) {
        this.mShouldLoadMore = shouldLoadMore;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Set as true if you want the endless scrolling to be as the user scrolls
     * to the top of the list, instead of bottom
     */
    public void setIsReversedScrolling(boolean reversed) {
        this.mIsReversedScrolling = reversed;
    }

    /**
     * Registers a callback to be notified when there is a need to load more data
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener != null){
            mShouldLoadMore = true;
        }else{
            mShouldLoadMore = false;
        }
        this.mLoadMoreListener = listener;
    }

    /**
     * This informs the adapter that <code>itemCount</code> more data has been loaded,
     * starting from <code>positionStart</code>
     * <p>
     * This also calls <code>notifyItemRangeInserted(int, int)</code>,
     * so the implementing class only needs to call this method
     *
     * @param positionStart Position of the first item that was inserted
     * @param itemCount     Number of items inserted
     */
    public void moreDataLoaded(int positionStart, int itemCount) {
        mIsLoading = false;
        notifyItemRemoved(positionStart); // remove the loading view
        notifyItemRangeInserted(positionStart, itemCount);
    }

    /**
     * Returns the number of scrollable items that should be left (threshold) in the
     * list before <code>OnLoadMoreListener</code> will be called
     * <p>
     * You can override this to return a preffered threshold,
     * or leave it to use the default
     *
     * @return integer threshold
     */
    public int getVisibleThreshold() {
        return 5;
    }

    public void setLoadingLayout(int loadingViewHolder) {
        this.loadingLayout = loadingViewHolder;
    }

    /**
     * Returns the loading view to be shown at the bottom of the list.
     *
     * @return loading view
     */
    public RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent) {
        if (loadingLayout == 0) {
            View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_load_more, parent, false);
            return new LoadingViewHolder(loadingView);
        }
        View loadingView = LayoutInflater.from(parent.getContext()).inflate(loadingLayout, parent, false);
        return new LoadingViewHolder(loadingView);
    }

    @Override
    public void addAllDataObject(List<IViewBinder> dataset) {
        mIsLoading = false;
        super.addAllDataObject(dataset);
    }

    @Override
    public void addDataObject(IViewBinder object) {
        mIsLoading = false;
        super.addDataObject(object);
    }

    public void addDataObject(IViewBinder object, boolean isAdapterAttached) {
        mIsLoading = false;
        super.addDataObject(object, isAdapterAttached);
    }

    public void addAllDataObject(List<IViewBinder> dataset, boolean isAdapterAttached) {
        mIsLoading = false;
        super.addAllDataObject(dataset, isAdapterAttached);
    }

    public void insertDataObject(IViewBinder object, int position, boolean isAdapterAttached) {
        mIsLoading = false;
        super.insertDataObject(object, position, isAdapterAttached);
    }

    public void add(DataBinder binder) {
        mIsLoading = false;
        super.add(binder);
    }

    public void insert(DataBinder binder, int position) {
        mIsLoading = false;
        super.insert(binder, position);
    }
}