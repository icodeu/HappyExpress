package com.icodeyou.happyexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.ContentViewPagerAdapter;
import com.icodeyou.happyexpress.fragment.ContentFragment;
import com.icodeyou.happyexpress.fragment.FindFragment;
import com.icodeyou.happyexpress.fragment.HomeFragment;
import com.icodeyou.library.util.ConstantUtil;
import com.icodeyou.library.util.PreferencesUtils;
import com.icodeyou.library.util.amap.AMapUtil;
import com.icodeyou.library.util.bean.User;

import java.util.ArrayList;
import java.util.List;

import c.b.BP;
import cn.bmob.v3.Bmob;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private View mHeaderView;

    // TabLayout的标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragments
    private List<Fragment> mFragments;
    private ContentViewPagerAdapter mViewPagerAdapter;

    // 定位用 mLocationClient
    private AMapLocationClient mLocationClient;
    private double mLongtitude = 0, mLatitude = 0, mLastLongtitude = 0, mLastLatitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "659e6c7416d6423ef6b3cd3a411e96d8");
        BP.init(this, "659e6c7416d6423ef6b3cd3a411e96d8");


        // 初始化控件 findViewById + 状态栏透明(>4.4)
        initViews();

        // 初始化数据 mTitles、mFragments等ViewPager需要的数据
        initData();

        // 对控件进行设置、适配、填充数据
        configViews();

        User.login(this, "CommonUser", "123456");

        // 定位，把数据保存到本地
        mLocationClient = AMapUtil.startLocation(getApplicationContext(), new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        Log.d(TAG, "aMap = " + aMapLocation.toString());
                        mLatitude = aMapLocation.getLatitude();
                        mLongtitude = aMapLocation.getLongitude();
                        // 和上一次的不同 需要更新SP
                        if (mLastLatitude != mLatitude || mLastLongtitude != mLongtitude) {
                            Log.d(TAG, "保存经纬度到本地 latitude = " + mLatitude + "  longtitude = " + mLongtitude);
                            PreferencesUtils.putString(MainActivity.this, ConstantUtil.PREFER_KEY_LONGTITUDE, String.valueOf(mLongtitude));
                            PreferencesUtils.putString(MainActivity.this, ConstantUtil.PREFER_KEY_LATITUDE, String.valueOf(mLatitude));
                            mLastLatitude = mLatitude;
                            mLastLongtitude = mLongtitude;
                        }
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void configUnLoginHeaderView() {
        if (mHeaderView != null)
            mNavigationView.removeHeaderView(mHeaderView);
        mHeaderView = mNavigationView.inflateHeaderView(R.layout.header_nav_unlogin);

        // 登录按钮响应
        TextView tvLogin = (TextView) mHeaderView.findViewById(R.id.id_tv_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                mDrawerLayout.closeDrawers();
            }
        });
    }

    private void configLoginedHeaderView() {
        if (mHeaderView != null)
            mNavigationView.removeHeaderView(mHeaderView);
        mHeaderView = mNavigationView.inflateHeaderView(R.layout.header_nav_logined);
    }

    private void configViews() {
        // 设置显示toolbar
        setSupportActionBar(mToolbar);
        // 设置toolbar导航点击事件--弹出DrawerLayout
        onToolbarNavigationOnClickListener();

        // 设置DrawerLayout开关指示器
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // NavigationView item 点击事件
        onNavigationViewMenuItemSelected(mNavigationView);
        // TODO: 15/11/25 根据用户是否登录加载不同的HeaderView
        mHeaderView = mNavigationView.inflateHeaderView(R.layout.header_nav_unlogin);

        // 初始化ViewPager适配器
        mViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(5);
        // TODO: 15/11/21 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）

        // 设置TabLayout滚动模式
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 将TabLayout和ViewPager进行关联
        mTabLayout.setupWithViewPager(mViewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
    }

    private void onToolbarNavigationOnClickListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    /**
     * 独立写个方法用于设置NavigationView的MenuItem点击事件
     *
     * @param navigationView
     */
    private void onNavigationViewMenuItemSelected(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_nav_menu_feedback:
//                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        break;
                    case R.id.id_nav_menu_setting:
//                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.id_nav_menu_collection:
//                        if (CheckUserValidUtil.isValid(MainActivity.this))
//                            startActivity(new Intent(MainActivity.this, CollectActivity.class));
                        break;
                    case R.id.id_nav_menu_share:
//                        showShare();
                        break;
                }
                // 不用设置item选中 但是要关闭DrawerLayout
                item.setChecked(false);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initData() {
        mFragments = new ArrayList<Fragment>();
        mTitles = new String[4];
        mTitles[0] = "首页";
        mTitles[1] = "发现";
        mTitles[2] = "驿站";
        mTitles[3] = "我的";
        HomeFragment homeFragment = new HomeFragment();
        mFragments.add(homeFragment);
        FindFragment findFragment = new FindFragment();
        mFragments.add(findFragment);
        for (int i = 2; i < mTitles.length; i++) {
            ContentFragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ContentFragment.BUNDLE_KEY_CATEGORY, mTitles[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // 设置SearchView并监听点击事件
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 搜索提交
                Log.d(TAG, "onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notify:
//                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
    }

    /**
     * 初始化一些数据到LeanCloud
     */
    /*
    private void addInitData() {
        List<String> articleListUrls = new ArrayList<String>();
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_Android%20关于%20GridView%20那些事");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_Android%20平台持久化%20debug%20log%20的工具类");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_Android%20开发必备知识：我和%20Gradle%20有个约会");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_Android开发经验总结");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_App性能优化浅谈");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_Gradle新一代自动化构建工具");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_每个%20Android%20开发者都应该了解的资源列表");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_理解%20Java%20中的弱引用");
        articleListUrls.add("http://7xojkh.com1.z0.glb.clouddn.com/android_面试时问哪些问题能试出一个Android应用开发者真正的水平");

        List<String> articleTitles = new ArrayList<String>();
        articleTitles.add("Android 关于 GridView 那些事");
        articleTitles.add("Android 平台持久化 debug log 的工具类");
        articleTitles.add("Android 开发必备知识：我和 Gradle 有个约会");
        articleTitles.add("Android开发经验总结");
        articleTitles.add("App性能优化浅谈");
        articleTitles.add("Gradle新一代自动化构建工具");
        articleTitles.add("每个 Android 开发者都应该了解的资源列表");
        articleTitles.add("理解 Java 中的弱引用");
        articleTitles.add("面试时问哪些问题能试出一个Android应用开发者真正的水平");

        List<String> articleImgs = new ArrayList<String>();
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/11b81b5a618c39ae779a.png?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/e4ec01dec05fa355a172.jpg?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/4324bddd156e7d80f2e5.jpg?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/a507dcafda98ba06ceb7.jpg?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://image2.rayliimg.cn/M/M006/2011-07-09/images/20110709135314549.jpg");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/bfa312a2825543a6992f.png?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/5891c537dfff2fd37c4a.jpg?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/514e6720261eb1438746.jpg?imageView/2/w/800/h/600/q/80/format/png");
        articleImgs.add("http://ac-mhke0kuv.clouddn.com/4921b67f38b0add5ff1e.jpg?imageView/2/w/800/h/600/q/80/format/png");

        for (int i = 0; i < articleListUrls.size(); i++) {
            AVObject post = new AVObject("ArticleList");
            post.put(ArticleList.KEY_ID, String.valueOf(i));
            post.put(ArticleList.KEY_TITLE, articleTitles.get(i));
            post.put(ArticleList.KEY_DESC, "desc");
            post.put(ArticleList.KEY_IMAGE_URL, articleImgs.get(i));
            post.put(ArticleList.KEY_AUTHOR_NAME, "icodeyou");
            post.put(ArticleList.KEY_AUTHOR_IMG_URL, "http://7xojkh.com1.z0.glb.clouddn.com/author.png");
            post.put(ArticleList.KEY_POST_TIME, "2015-12-16 11:23");
            post.put(ArticleList.KEY_STAR_COUNT, 0);
            post.put(ArticleList.KEY_CATEGORY, "android");
            post.put(ArticleList.KEY_RESPONSE_COUNT, 0);
            post.put(ArticleList.KEY_ARTICLE_URL, articleListUrls.get(i));

            post.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Log.d(TAG, "save success");
                    } else {
                        Log.d(TAG, "save fail " + e.getCode() + " message " + e.getMessage());
                    }
                }
            });
        }
    }
    */
}
