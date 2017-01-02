package me.ideatolife.testproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import me.ideatolife.testproject.R;
import me.ideatolife.testproject.utils.photoview.PhotoViewAttacher;


public class ImageApdater<T> extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> images;
    Handler handler = new Handler();

    public void setImages(ArrayList<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public ImageApdater(Context context, ArrayList<String> banners) {
        this.context = context;
        this.images = banners;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        View headerItem = inflater.inflate(R.layout.item_image_pager, null);


        final ImageView headerImageView = (ImageView) headerItem.findViewById(R.id.item_image);
        final PhotoViewAttacher mAttacher;
//        mAttacher = new PhotoViewAttacher(headerImageView);
        Glide.with(container.getContext()).load(images.get(position)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                mAttacher.update();
                return false;
            }
        }).into(headerImageView);

        container.addView(headerItem);
        return headerItem;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        ImageView imgView = (ImageView) view.findViewById(R.id.item_image);
        if (imgView != null) {
            Glide.clear(imgView);
            imgView.setImageBitmap(null);
        }
        ((ViewPager) container).removeView(view);
        view = null;
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
