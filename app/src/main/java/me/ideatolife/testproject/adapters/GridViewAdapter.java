package me.ideatolife.testproject.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import me.ideatolife.testproject.AppController;
import me.ideatolife.testproject.R;
import me.ideatolife.testproject.activities.FeedDetailsAcitvity;
import me.ideatolife.testproject.models.FlickrObject;
import me.ideatolife.testproject.tasks.LoadColorFromImage;


public class GridViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<T> items = new ArrayList<T>();
    private SharedPreferences preferences;

    public GridViewAdapter(Context context, ArrayList<T> items) {
        this.context = context;
        this.items = items;
        preferences = AppController.getmInstance().getPref();

    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, null);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder myViewHolder = (ViewHolder) holder;
        FlickrObject flickrObject = (FlickrObject) items.get(position);
        Glide.with(context).load(flickrObject.getImageUrl()).dontAnimate().into(myViewHolder.image);
        myViewHolder.imageDate.setText(flickrObject.getTitle());

        if (preferences.getInt(flickrObject.getImageUrl(), Color.BLACK) == Color.BLACK) {
            new LoadColorFromImage(context, flickrObject.getImageUrl(), myViewHolder.imageDate
            ).execute();
        } else {
            myViewHolder.imageDate.setBackgroundColor(preferences.getInt(flickrObject.getImageUrl(), Color.BLACK));
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(position,myViewHolder);
            }
        });

    }


    private void startActivity(int position, ViewHolder myViewHolder) {
        Intent intent = new Intent(context, FeedDetailsAcitvity.class);
        intent.putExtra(FeedDetailsAcitvity.FEEDS_ARRAY_EXTRA, items);
        intent.putExtra(FeedDetailsAcitvity.FEEDS_POSITION_EXTRA, position);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = "image_transition";
            Pair<View, String> pair1 = Pair.create((View)myViewHolder.image, myViewHolder.image.getTransitionName());
//            Pair<View, String> pair2 = Pair.create((View)myViewHolder.imageDate, myViewHolder.imageDate.getTransitionName());
            Activity activity= (Activity) context;
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, pair1);
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else
            return 0;
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageDate;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.grid_item_image);
            imageDate = (TextView) itemView.findViewById(R.id.grid_item_image_date);
        }
    }

}