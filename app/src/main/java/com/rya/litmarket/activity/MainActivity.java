package com.rya.litmarket.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rya.litmarket.R;
import com.rya.litmarket.ui.fragment.MainContentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mMainDrawerLayout;

    private long mTimeBack = 0;
    private boolean isDrawerOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initStatusBar();
        initToolbar();
        initMainLayout();
    }

    /*
    * 替换主页内容页
    * */
    private void initMainLayout() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fl_main_content, new MainContentFragment(), "main");

        transaction.commit();
    }

    /*
    * 初始化Toolbar标题栏
    * 设置HomeAsUp按钮（侧滑栏menu)
    * */
    private void initToolbar() {
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

    }

    /*
    * 初始化状态栏 --沉浸
    * */
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /*
    * 设置Toolbar按钮条目
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return true;
    }

    /*
    * Toolbar按钮条目 点击事件
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMainDrawerLayout.openDrawer(Gravity.START);

                break;
            case R.id.toolbar_setting:

        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mMainDrawerLayout.isDrawerOpen(Gravity.START)) {
                mMainDrawerLayout.closeDrawer(Gravity.START);
            } else {
                if ((System.currentTimeMillis() - mTimeBack) > 2000) {
                    Toast.makeText(this, "再按一次退出应用！", Toast.LENGTH_SHORT).show();
                    mTimeBack = System.currentTimeMillis();
                } else {
                    //GlobalApplication.getInstance().exit();

                    finish();
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
