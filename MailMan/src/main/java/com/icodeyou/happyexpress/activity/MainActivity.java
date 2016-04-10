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

import com.icodeyou.happyexpress.R;
import com.icodeyou.happyexpress.adapter.ContentViewPagerAdapter;
import com.icodeyou.happyexpress.fragment.ContentFragment;
import com.icodeyou.happyexpress.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "659e6c7416d6423ef6b3cd3a411e96d8");

        // 初始化控件 findViewById + 状态栏透明(>4.4)
        initViews();

        // 初始化数据 mTitles、mFragments等ViewPager需要的数据
        initData();

        // 对控件进行设置、适配、填充数据
        configViews();
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
        mTitles[0] = "抢单";
        mTitles[1] = "历史记录";
        mTitles[2] = "驿站";
        mTitles[3] = "我的";
        HomeFragment homeFragment = new HomeFragment();
        mFragments.add(homeFragment);
        for (int i = 1; i < mTitles.length; i++) {
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
}
