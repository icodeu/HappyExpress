package com.icodeyou.courier.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.RecyclerViewNormalAdapter;


/**
 * 显示具体内容的Fragment
 */
public class ContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ContentFragment";

    private View mView;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerViewNormalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // 保存NewsList数据的集合
//    private List<ArticleList> mArticleList;

    // 当前Fragment的文章类别
    private String mCategory;
    public static final String BUNDLE_KEY_CATEGORY = "bundle_key_category";

    // 最后一个可见的Item 实现上拉加载更多的功能
    private int mLastVisibleItem;
    // 分页加载的页面大小
    private static final int PAGE_SIZE_LIMIT = 5;
    // 当前页数
    private int currentPage = 0;

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_content, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化NewsList集合
//        mArticleList = new ArrayList<ArticleList>();

        // 获取文章类别信息 并 转换为小写，主要是为了和服务器查询时保持相同的小写
        Bundle cotegoryBundle = getArguments();
        if (cotegoryBundle != null) {
            mCategory = cotegoryBundle.getString(BUNDLE_KEY_CATEGORY).toLowerCase();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.id_swiperefreshlayout);
        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // 第一次进入显示加载
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        // 配置RecyclerView，根据ItemMode设置Adapter
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 设置滚动监听器，实现上拉加载
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 开始加载更多
                if (recyclerView.SCROLL_STATE_IDLE == newState && mLastVisibleItem == mAdapter.getItemCount() - 1) {
                    // load more
                    // 当前已加载页数加1
                    currentPage++;
                    getDataFromLeanCloud();
                    refreshRecyclerView();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        // 从LeanCloud上获取数据
        getDataFromLeanCloud();
    }

    /**
     * 从LeanCloud上获取数据
     */
    private void getDataFromLeanCloud() {
//        AVQuery<AVObject> query = new AVQuery<AVObject>(ArticleList.TABLE_ARTICLE_LIST);
//        query.whereEqualTo(ArticleList.KEY_CATEGORY, mCategory);
//        query.setLimit(PAGE_SIZE_LIMIT);
//        query.setSkip(currentPage * PAGE_SIZE_LIMIT);
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                Log.d(TAG, "queryDone");
//                if (e == null) {
//                    saveToNewsListBean(list);
//                    Log.d(TAG, String.valueOf(list.size()));
//                } else {
//                    //fail
//                }
//            }
//        });
    }


    /**
     * 将网络上获取的数据保存到NewsListBean中
     */
    /*
    private void saveToNewsListBean(List<AVObject> list) {
        // 获取到了新内容
        if (list.size() != 0) {
            for (AVObject item : list) {
                ArticleList articleList = new ArticleList();
                articleList.setId(item.getString(ArticleList.KEY_ID));
                articleList.setTitle(item.getString(ArticleList.KEY_TITLE));
                articleList.setDesc(item.getString(ArticleList.KEY_DESC));
                articleList.setImgUrl(item.getString(ArticleList.KEY_IMAGE_URL));
                articleList.setAuthorName(item.getString(ArticleList.KEY_AUTHOR_NAME));
                articleList.setAuthorImgUrl(item.getString(ArticleList.KEY_AUTHOR_IMG_URL));
                articleList.setPostTime(item.getString(ArticleList.KEY_POST_TIME));
                articleList.setStarCount(item.getInt(ArticleList.KEY_STAR_COUNT));
                articleList.setCategory(item.getString(ArticleList.KEY_CATEGORY));
                articleList.setArticleUrl(item.getString(ArticleList.KEY_ARTICLE_URL));
                articleList.setResponseCount(item.getInt(ArticleList.KEY_RESPONSE_COUNT));
                mArticleList.add(articleList);
            }
        } else {
            // 没有新内容了
            mAdapter.setHasMoreImpl(false);
        }
        // 更新RecyclerView的数据显示
        refreshRecyclerView();
    }
    */

    /**
     * 更新RecyclerView的数据显示
     */
    private void refreshRecyclerView() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * SwipeRefreshLayout 刷新回调
     */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "暂时没有干货啦", Toast.LENGTH_SHORT).show();
            }
        }, 1000);

    }

}
