package cn.cblueu.cblueuapp;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import adapter.BaseFragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.MusicFragment;

public class MusicActivity extends AppCompatActivity{

    @BindView(R.id.tool_bar) Toolbar toolbar;
    @BindView(R.id.mTabSegment) QMUITabSegment mTabSegment;
    @BindView(R.id.mViewPager) QMUIViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpTabSegment();
        EventBus.getDefault().register(this);
    }

    private void setUpTabSegment() {
        mTabSegment.reset();
        List<Fragment> fragments = new ArrayList<>();
        MusicFragment wFragment = MusicFragment.newInstance("netease");
        MusicFragment qFragment = MusicFragment.newInstance("qq");
        MusicFragment kFragment = MusicFragment.newInstance("kugou");
        fragments.add(wFragment);
        fragments.add(qFragment);
        fragments.add(kFragment);
        BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabSegment.setHasIndicator(true);
        mTabSegment.addTab(new QMUITabSegment.Tab("网易云"));
        mTabSegment.addTab(new QMUITabSegment.Tab("QQ音乐"));
        mTabSegment.addTab(new QMUITabSegment.Tab("酷狗音乐"));
        mTabSegment.setupWithViewPager(mViewPager,false);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
            }

            @Override
            public void onTabUnselected(int index) {
            }

            @Override
            public void onTabReselected(int index) {
            }

            @Override
            public void onDoubleTap(int index) {
            }
        });

    }

    private void setUpToolbar() {

        //修改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        icon.setColorFilter(Color.WHITE);
        searchView.setQueryHint("请输入音乐名");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MusicActivity.this,"请输入音乐名",Toast.LENGTH_LONG).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //TODO:进行数据请求和转换

//                Toast.makeText(MusicActivity.this,"你输入了"+s,Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new MessageEvent("song", s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe //无用的，没有会报错
    public void onEvent(String message_Type) {
    }

}
