package com.icodeyou.happyexpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.icodeyou.happyexpress.R;


/**
 * Fragment中RecyclerView的Adapter
 */
public class RecyclerViewNormalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mHasMore = true;
    private boolean mNoMoreViewStubHasInflate = false;

    public RecyclerViewNormalAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.item_content_normal, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = mLayoutInflater.inflate(R.layout.item_footer, parent, false);
            FooterViewHolder holder = new FooterViewHolder(view);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {

        } else if (holder instanceof FooterViewHolder) {
            // 没有更多内容了
            if (!mHasMore) {
                // 隐藏ProgressBar
                ((FooterViewHolder) holder).pbLoadMore.setVisibility(View.GONE);
                // 显示包含TextView的ViewStub
                if (!mNoMoreViewStubHasInflate) {
                    ViewStub vsNoMore = ((FooterViewHolder) holder).vsNoMore;
                    vsNoMore.inflate();
                    mNoMoreViewStubHasInflate = true;
                }
            } else
                ((FooterViewHolder) holder).pbLoadMore.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加上一个FooterView
     */
    @Override
    public int getItemCount() {
//        return mArticleList.size();
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为Footer
        if (position + 1 == getItemCount())
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    public void setHasMore(boolean hasMore) {
        this.mHasMore = hasMore;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pbLoadMore;
        public ViewStub vsNoMore;

        public FooterViewHolder(View itemView) {
            super(itemView);
            pbLoadMore = (ProgressBar) itemView.findViewById(R.id.id_pb_loadmore);
            vsNoMore = (ViewStub) itemView.findViewById(R.id.id_vs_nomore);
        }
    }
}
