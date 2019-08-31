package com.rathana.asynctaskdemo.util;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class LoadMoreLinearLayoutManager extends LinearLayoutManager {

    private  int loadMoreBeforeEnd;
    private boolean isLoadMore=false;
    private int pastVisibleItem=0;
    private OnLoadMOreListener loadMOreListener;
    private LinearSmoothScroller mLinearSmoothScroller;
    private static final float MILLISECONDS_PER_INCH = 10f;

    public void setLoadMOreListener(OnLoadMOreListener loadMOreListener) {
        this.loadMOreListener = loadMOreListener;
    }

    public int getLoadMoreBeforeEnd() {
        return loadMoreBeforeEnd;
    }

    public void setLoadMoreBeforeEnd(int loadMoreBeforeEnd) {
        this.loadMoreBeforeEnd = loadMoreBeforeEnd;
    }

    public void loadingFinished(){
        isLoadMore=false;
    }

    public int getPastVisibleItem() {
        return pastVisibleItem;
    }

    public void setPastVisibleItem(int pastVisibleItem) {
        this.pastVisibleItem = pastVisibleItem;
    }

    public LoadMoreLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        int visibleItemCount= this.getChildCount();
        int totalCount=this.getItemCount();
        pastVisibleItem=this.findFirstVisibleItemPosition();

        if(!isLoadMore){
            if(visibleItemCount + pastVisibleItem >= totalCount-loadMoreBeforeEnd){
                isLoadMore=true;
                if(loadMOreListener!=null)
                    loadMOreListener.onLoadMore();
            }
        }
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
        if (mLinearSmoothScroller == null) {
            mLinearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return LoadMoreLinearLayoutManager.
                            this.computeScrollVectorForPosition(targetPosition);
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                }
            };
        }
        mLinearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(mLinearSmoothScroller);
    }

    public interface OnLoadMOreListener{
        void onLoadMore();
    }

}








