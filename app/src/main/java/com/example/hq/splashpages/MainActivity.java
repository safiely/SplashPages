package com.example.hq.splashpages;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示使用引导界面;
 * */
public class MainActivity extends Activity {

    //是否显示引导界面，第一次默认显示，以后就检该值，决定是否进入引导界面或登录界面
    private boolean isShow=false;

    private ViewPager mViewPage;
    private List<View> views;
    private ImageView[]  iviews;

    //放置小圈圈的layout
    private LinearLayout dot_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //第一次，因为返回的是默认值，所以是false
        if(isShow)
        {
            //显示登录页
            showLogin();
        }else{
            //显示引导页
            showGuide();
            //更改isShow的值，让它下次显示登录页
        }
    }
    //初始化视图
    public void initView()
    {
        //判断显示引导界面还是登录界面

        isShow=PreferenceUtil.getIsShow(this,PreferenceUtil.SHOW_GUIDE);
        mViewPage=(ViewPager)findViewById(R.id.viewPager);

        LayoutInflater layoutInflater=getLayoutInflater();
        View v1=layoutInflater.inflate(R.layout.guide_1, null);
        View v2=layoutInflater.inflate(R.layout.guide_2,null);
        View v3=layoutInflater.inflate(R.layout.guide_3,null);
        views=new ArrayList<View>();
        views.add(v1);
        views.add(v2);
        views.add(v3);
        //创建小圆圈视图
        iviews=new ImageView[views.size()];
        dot_layout=(LinearLayout)findViewById(R.id.dot_layout);
        //开始交给showGuide画图


    }

    //显示登录页
    public void showLogin()
    {
        //可以用intent携带信息告诉，应用是否根据用户选择的自动登录后，直接进入主界面或是登录界面
        Intent intent=new Intent(this,LoginActivity.class);
        //intent.putExtra("直接登录",false);
        startActivity(intent);
        finish();
    }
    //显示引导页
    public void showGuide()
    {
          MViewPageAdapter madapter=new MViewPageAdapter(this,views);
          mViewPage.setAdapter(madapter);
          mViewPage.setOnPageChangeListener(madapter);
          drawCircle();
    }
    //画小圈圈
    public void  drawCircle()
    {
        //画三个小圈圈
        for(int i=0;i<iviews.length;i++)
        {
            iviews[i]=new ImageView(this);
            if (i == 0) {
                // 默认选中第一张照片，所以将第一个小圆圈变为icon_carousel_02
                iviews[i].setImageResource(R.drawable.icon_carousel_02);
            } else {
                iviews[i].setImageResource(R.drawable.icon_carousel_01);
            }
            iviews[i].setPadding(7,7,7,7);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity= Gravity.CENTER_VERTICAL;
            dot_layout.addView(iviews[i],params);
        }
    }

    //viewpage的适配器
    /**
     * 当你实现一个PagerAdapter,你必须至少覆盖以下方法:
     instantiateItem(ViewGroup, int)
     destroyItem(ViewGroup, int, Object)
     getCount()
     isViewFromObject(View, Object)
     * */
    public  class MViewPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
        private List<View> mviews;
        private Activity context;
        private Button button;//开始使用的button
        public MViewPageAdapter (Activity context ,List<View> mviews)
        {
            this.context=context;
            this.mviews=mviews;
            Log.v("适配器-----","我的适配器");
        }
        @Override
        public int getCount() {
            return mviews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(mviews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {

            ((ViewPager) container).addView(mviews.get(position));
            //在示例化视图后，判断当前的视图，并监听按钮
            if(position==(mviews.size()-1))
            {
                //在这里监听最后一个页面的button
                Button button=(Button)findViewById(R.id.use_app);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //并设置isSHow为true
                        PreferenceUtil.setIsShow(context, PreferenceUtil.SHOW_GUIDE, true);
                        startActivity(new Intent(context, LoginActivity.class));
                        context.finish();
                    }
                });
            }
            return  mviews.get(position);


        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }
        /**
         * 页面有所改变，如果是当前页面，将小圆圈改为icon_carousel_02，其他页面则改为icon_carousel_01
         */
        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < iviews.length; i++) {
                if (arg0 != i) {
                    iviews[i]
                            .setImageResource(R.drawable.icon_carousel_01);
                } else {
                    iviews[arg0]
                            .setImageResource(R.drawable.icon_carousel_02);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
