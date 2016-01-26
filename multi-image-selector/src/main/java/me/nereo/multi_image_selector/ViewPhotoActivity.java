package me.nereo.multi_image_selector;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bm.library.PhotoView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class ViewPhotoActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<String> imgUrls = new ArrayList<String>();
    private List<PhotoView> views = new ArrayList<PhotoView>();
    private MyAdapter myAdapter;
    private ImageOptions options = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_INSIDE).build();
    private int currrentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imgUrls = bundle.getStringArrayList("imgUrls");
            currrentItem = bundle.getInt("current");
        }

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        for (int i = 0; i < imgUrls.size(); i++) {
            PhotoView photoView = new PhotoView(this);
            photoView.enable();
            photoView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            views.add(photoView);
        }
        myAdapter = new MyAdapter();
        mViewPager.setAdapter(myAdapter);
        mViewPager.setCurrentItem(currrentItem);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.v("Imgurl", imgUrls.get(position));
                x.image().bind(views.get(position), imgUrls.get(position), options);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        x.image().bind(views.get(currrentItem), imgUrls.get(currrentItem), options);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return views.size();
        }

        @Override
        public Object instantiateItem(View arg0, final int arg1) {
            try {
                ((ViewPager) arg0).addView(views.get(arg1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return views.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }
}
