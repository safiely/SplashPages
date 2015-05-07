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
 * ��ʾʹ����������;
 * */
public class MainActivity extends Activity {

    //�Ƿ���ʾ�������棬��һ��Ĭ����ʾ���Ժ�ͼ��ֵ�������Ƿ��������������¼����
    private boolean isShow=false;

    private ViewPager mViewPage;
    private List<View> views;
    private ImageView[]  iviews;

    //����СȦȦ��layout
    private LinearLayout dot_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //��һ�Σ���Ϊ���ص���Ĭ��ֵ��������false
        if(isShow)
        {
            //��ʾ��¼ҳ
            showLogin();
        }else{
            //��ʾ����ҳ
            showGuide();
            //����isShow��ֵ�������´���ʾ��¼ҳ
        }
    }
    //��ʼ����ͼ
    public void initView()
    {
        //�ж���ʾ�������滹�ǵ�¼����

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
        //����СԲȦ��ͼ
        iviews=new ImageView[views.size()];
        dot_layout=(LinearLayout)findViewById(R.id.dot_layout);
        //��ʼ����showGuide��ͼ


    }

    //��ʾ����ҳ
    public void showLogin()
    {
        //������intentЯ����Ϣ���ߣ�Ӧ���Ƿ�����û�ѡ����Զ���¼��ֱ�ӽ�����������ǵ�¼����
        Intent intent=new Intent(this,LoginActivity.class);
        //intent.putExtra("ֱ�ӵ�¼",false);
        startActivity(intent);
        finish();
    }
    //��ʾ��¼ҳ
    public void showGuide()
    {
          MViewPageAdapter madapter=new MViewPageAdapter(this,views);
          mViewPage.setAdapter(madapter);
          mViewPage.setOnPageChangeListener(madapter);
          drawCircle();
    }
    //��СȦȦ
    public void  drawCircle()
    {
        //������СȦȦ
        for(int i=0;i<iviews.length;i++)
        {
            iviews[i]=new ImageView(this);
            if (i == 0) {
                // Ĭ��ѡ�е�һ����Ƭ�����Խ���һ��СԲȦ��Ϊicon_carousel_02
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

    //viewpage��������
    /**
     * ����ʵ��һ��PagerAdapter,��������ٸ������·���:
     instantiateItem(ViewGroup, int)
     destroyItem(ViewGroup, int, Object)
     getCount()
     isViewFromObject(View, Object)
     * */
    public  class MViewPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
        private List<View> mviews;
        private Activity context;
        private Button button;//��ʼʹ�õ�button
        public MViewPageAdapter (Activity context ,List<View> mviews)
        {
            this.context=context;
            this.mviews=mviews;
            Log.v("������-----","�ҵ�������");
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
            //��ʾ������ͼ���жϵ�ǰ����ͼ����������ť
            if(position==(mviews.size()-1))
            {
                //������������һ��ҳ���button
                Button button=(Button)findViewById(R.id.use_app);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //������isSHowΪtrue
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
         * ҳ�������ı䣬����ǵ�ǰҳ�棬��СԲȦ��Ϊicon_carousel_02������ҳ�����Ϊicon_carousel_01
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
