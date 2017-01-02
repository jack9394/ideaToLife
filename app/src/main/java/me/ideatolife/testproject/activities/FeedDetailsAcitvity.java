package me.ideatolife.testproject.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import me.ideatolife.testproject.AppController;
import me.ideatolife.testproject.R;
import me.ideatolife.testproject.Utils;
import me.ideatolife.testproject.adapters.FeedPagerAdapter;
import me.ideatolife.testproject.base.BaseActivity;
import me.ideatolife.testproject.fragments.FeedDetailsFragment;
import me.ideatolife.testproject.models.FlickrObject;
import me.ideatolife.testproject.tasks.LoadColorFromImage;

public class FeedDetailsAcitvity extends BaseActivity {
    public static final String FEEDS_ARRAY_EXTRA = "feeds_array_extra";
    public static final String FEEDS_POSITION_EXTRA = "feeds_position_extra";
    private final int REQUEST_CODE_WRITE_PERMISSION = 1;

    ViewPager viewpager;
    ArrayList<FlickrObject> items;
    FeedPagerAdapter adapter;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    SharedPreferences sharedPreferences;
    FloatingActionButton shareFab;
    FlickrObject currentObject;
    int currentPosition = 0;
    ArrayList<String> imagesUrlsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }
        setViewpager();
        shareFab.setOnClickListener(this);
        imageView.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_details;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.fab_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, currentObject.getLink());
            sendIntent.setType("text/plain");
            this.startActivity(sendIntent);
        } else if (v.getId() == R.id.fragment_feed_details_img) {
            Intent intent = new Intent(this, ImageViewerActivity.class);
            intent.putExtra(ImageViewerActivity.IMAGES_ARRAYLIST_EXTRA, imagesUrlsArrayList);
            intent.putExtra(ImageViewerActivity.IMAGES_POSITION_EXTRA, currentPosition);
            startActivity(intent);

        }
    }


    @Override
    protected void init() {
        viewpager = (ViewPager) findViewById(R.id.activity_feed_details_viewpager);
        imageView = (ImageView) findViewById(R.id.fragment_feed_details_img);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_feed_details_collapsing_layout);
        items = (ArrayList<FlickrObject>) getIntent().getSerializableExtra(FEEDS_ARRAY_EXTRA);
        sharedPreferences = AppController.getmInstance().getPref();
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));
        shareFab = (FloatingActionButton) findViewById(R.id.fab_share);
        currentPosition = getIntent().getIntExtra(FEEDS_POSITION_EXTRA, 0);
    }

    public void setViewpager() {
        adapter = new FeedPagerAdapter(getSupportFragmentManager());
        imagesUrlsArrayList = new ArrayList<>();
        for (FlickrObject flickrObject : items) {
            imagesUrlsArrayList.add(flickrObject.getImageUrl());
            FeedDetailsFragment feedDetailsFragment = new FeedDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(feedDetailsFragment.FEED_OBJECT_ARGUMENT, flickrObject);
            feedDetailsFragment.setArguments(bundle);
            adapter.addFrag(feedDetailsFragment, flickrObject.getTitle());
        }
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentObject = items.get(position);
                currentPosition = position;
                setImageView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(currentPosition, false);
        currentObject = items.get(currentPosition);
        setImageView();

    }

    void setFabColor(int color) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
        };
        int[] colors = new int[]{
                color};
        shareFab.setBackgroundTintList(new ColorStateList(states, colors));
    }

    private void setImageView() {
        String imageUrl = currentObject.getImageUrl();
        Glide.with(FeedDetailsAcitvity.this).load(imageUrl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return false;
            }
        }).into(imageView);
        setCollapsingColor(imageUrl);
        collapsingToolbarLayout.setTitle(currentObject.getTitle());
    }

    public void setCollapsingColor(String imageUrl) {
        if (sharedPreferences.getInt(imageUrl, Color.BLACK) == Color.BLACK) {
            new LoadColorFromImage(this, imageUrl, collapsingToolbarLayout
                    , new LoadColorFromImage.PaletteListener() {
                @Override
                public void onFinish(int color) {
                    setStatusBarColor(color);
                    setFabColor(color);
                }
            }).execute();
        } else {
            collapsingToolbarLayout.setContentScrimColor(sharedPreferences.getInt(imageUrl, Color.BLACK));
            setStatusBarColor(sharedPreferences.getInt(imageUrl, Color.BLACK));
            setFabColor(sharedPreferences.getInt(imageUrl, Color.BLACK));
        }
    }


    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_WRITE_PERMISSION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.download_menu) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE_PERMISSION);
                } else {
                    saveImage();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        final GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) imageView.getDrawable();
        if(bitmapDrawable==null)
            return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                Utils.saveImage(FeedDetailsAcitvity.this, bitmapDrawable.getBitmap(), currentObject.getTitle());
            }
        });
    }
}
