package me.ideatolife.testproject.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

import me.ideatolife.testproject.AppController;

public class LoadColorFromImage extends AsyncTask<Void, Void, Integer> {
    private final Context context;
    private final String path;
    private final View view;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;
    private PaletteListener listener;
    private int color = 0;

    public LoadColorFromImage(Context context, String path, View view) {
        this.context = context;
        this.path = path;
        this.view = view;
        sharedPreferences = AppController.getmInstance().getPref();
    }
    public LoadColorFromImage(Context context, String path, View view,PaletteListener listener) {
        this(context,path,view);
        this.listener=listener;
        sharedPreferences = AppController.getmInstance().getPref();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            bitmap = Glide.with(this.context).load(path).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(-1,-1).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (bitmap != null && bitmap.getHeight() >= 0 && bitmap.getWidth() >= 0) {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    //work with the palette here
                    try {
                        if (palette.getDarkVibrantSwatch() != null) {
                            color = palette.getDarkVibrantSwatch().getRgb();
                            sharedPreferences.edit().putInt(path, color).apply();
                        } else {
                            color = palette.getDarkMutedSwatch().getRgb();
                            sharedPreferences.edit().putInt(path, color).apply();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if (view instanceof TextView) {
                        view.setBackgroundColor(color);
                    } else if (view instanceof CollapsingToolbarLayout) {
                        ((CollapsingToolbarLayout) view).setContentScrimColor(color);
                    }
                    if (listener != null) {
                        listener.onFinish(color);
                    }
                }
            });
        }
        return color;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    public interface PaletteListener{
        public void onFinish(int color);
    }
}

