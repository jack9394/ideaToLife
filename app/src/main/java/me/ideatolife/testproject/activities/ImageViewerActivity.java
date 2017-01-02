package me.ideatolife.testproject.activities;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import me.ideatolife.testproject.R;
import me.ideatolife.testproject.adapters.ImageApdater;
import me.ideatolife.testproject.base.BaseActivity;
import me.ideatolife.testproject.utils.ParallaxPageTransformer;

public class ImageViewerActivity extends BaseActivity {
    public final static String IMAGES_ARRAYLIST_EXTRA = "images_extra";
    public final static String IMAGES_POSITION_EXTRA = "position_extra";

    ViewPager viewPager;
    ArrayList<String> imagesArrayList;
    ImageApdater adapter;
    int position=0;
    ImageView closeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewPager();
        closeImg.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_viewer;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==closeImg.getId()){
            finish();
        }
    }

    @Override
    protected void init() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        imagesArrayList =getIntent().getStringArrayListExtra(IMAGES_ARRAYLIST_EXTRA);
        position=getIntent().getIntExtra(IMAGES_POSITION_EXTRA,0);
        closeImg= (ImageView) findViewById(R.id.activity_image_close_btn);
    }

    public void setParralaxViewPager(ViewPager viewPager, int viewId, int enterEffect, int exitEffect) {
        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer().add(viewId, enterEffect, exitEffect);
        viewPager.setPageTransformer(true, pageTransformer);
    }

    public void setViewPager() {
//        circleIndicator.setExtraSpacing(S.utils.dipToPixels(this, 5));
        adapter = new ImageApdater(this, imagesArrayList);
        setParralaxViewPager(viewPager, R.id.item_image, 2, 2);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
//        circleIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

}
