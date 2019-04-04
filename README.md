
Fucking Awesome RecyclerView
=================

A RecyclerView Adapter which allows you to have an Infinite scrolling list in your apps. This library offers you a custom adapter to use with any recycler view. You get a callback when the user is about to reach the bottom of the list, which you can use to load more data. FaRecyclerView support pull to refresh, multiple type view layout in a sec with model

You can also customize what the loading view at the bottom of the list looks like.

**Note:** This library should be able to work with any layout manager linear, grid, stagged layout manager. You can config layout manager in xml or in java code, support no data layout

Demo
-------
This is the sample app in action:

[![https://img.youtube.com/vi/ihseEsSGgWQ/0.jpg](http://img.youtube.com/vi/ihseEsSGgWQ/0.jpg)](http://www.youtube.com/watch?v=ihseEsSGgWQ "Fucking Awesome Recyclerview")


Usage
-------
Add a dependency to your all project `build.gradle`:

    allprojects {
    repositories {
        google()
        maven { url "https://jitpack.io" }
    }
    }

-------
Add a dependency to your `build.gradle`:

    dependencies {
       implementation 'com.github.binhbt:FaRecyclerView:1.2.8'
    }

Define in xml:


```
<com.vn.fa.widget.FaRecyclerView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/list"
        android:layout_width="match_parent"
        app:fa_orientation="vertical"
        app:fa_type="grid"
        app:fa_spanCount="2"
        android:layout_centerHorizontal="true"
        android:layout_height="match_parent"/>
```
 - `app:fa_layout_empty="@layout/fa_emptyview"`: custom no data
 - `app:fa_progress_in_center="true"`: show loading when load data
 - `app:fa_type="list"`: list, grid, stagged type for Recyclerview
 - `app:fa_orientation="vertical"`: vertical or horizontal oriontation 
 
FarecyclerView support fa_type: list, grid, stagged, fa_spanCount for grid type. fa_orientation can be vertical, horizontal

You can set layout manager in code:

`    mAdapter = new FaAdapter();

     mRecycler = (FaRecyclerView) findViewById(R.id.list);
     GridLayoutManager glm = new GridLayoutManager(this, 2);
     mRecycler.setLayoutManager(glm);
`

Callback for refresh 

       ` mRecycler.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });`
        
        
 Load more data       
        

     `mAdapter.setOnLoadMoreListener(new InfiniteAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
               
            }
        });`
 - `mAdapter.setLoadingLayout(int loadingViewHolder);`: custom loading layout by yourself
 - `mAdapter.setShouldLoadMore(false);`: finish load more data, disable loading
 - `mAdapter.addAllDataObject(list)`: add item for recyclerview List<IViewBinder>
 - `public class PhotoItem implements IViewBinder`: Model need implement IviewBinder for multiple type view(Read on demo for detail)
 - `public class PhotoItemView extends FaDataBinder<PhotoItem> `: View holder for list item view

 - Demo: https://github.com/binhbt/FaRecyclerViewDemo
 
Original License
-------

    Copyright 2016

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


