package me.ideatolife.testproject.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.ideatolife.testproject.R;
import me.ideatolife.testproject.base.BaseFragment;
import me.ideatolife.testproject.models.FlickrObject;
import me.ideatolife.testproject.utils.TagView;
import me.ideatolife.testproject.utils.TextWithTitleView;


public class FeedDetailsFragment extends BaseFragment {
    public static final String FEED_OBJECT_ARGUMENT="feed_object_argument";
    FlickrObject flickrObject;
    TextWithTitleView titleView,descriptionView,ownerView,dateView;
    TagView tagView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews();
    }

    @Override
    protected void init() {
        Bundle bundle=getArguments();
        flickrObject= (FlickrObject) bundle.getSerializable(FEED_OBJECT_ARGUMENT);
        titleView= (TextWithTitleView) mView.findViewById(R.id.title_view);
        descriptionView= (TextWithTitleView) mView.findViewById(R.id.description_view);
        dateView= (TextWithTitleView) mView.findViewById(R.id.date_view);
        ownerView= (TextWithTitleView) mView.findViewById(R.id.owner_view);
        tagView= (TagView) mView.findViewById(R.id.tag_view);


    }

    private void setViews(){
        titleView.setTitleAndText("title",flickrObject.getTitle());
        descriptionView.setTitleAndText("description",flickrObject.getDescription());
        ownerView.setTitleAndText("taken by",flickrObject.getAuthor());
        dateView.setTitleAndText("date taken",flickrObject.getDateTaken());
        tagView.setTitleWithTags("tags",flickrObject.getTags());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_details;
    }


}
