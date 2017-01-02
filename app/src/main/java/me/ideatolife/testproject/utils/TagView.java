package me.ideatolife.testproject.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.ideatolife.testproject.R;
import me.ideatolife.testproject.Utils;

/**
 * Created by jack on 1/2/17.
 */

public class TagView extends CardView {
    private TextView titleView;
    FlowLayout linearLayout;

    public TagView(Context context) {
        super(context);
        init(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_tagview, this);
        titleView = (TextView) findViewById(R.id.titleView);
        linearLayout = (FlowLayout) findViewById(R.id.tags_layout);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setTitleWithTags(String title, String tags) {
        setTitle(title);
        setTags(tags);
    }

    public void setTags(String tags) {
        if (tags.length() == 0) {
            tags = "No_tags";
        }
        String[] parts = tags.split(" ");

        for (String tag : parts) {
            TextView textView = new TextView(getContext());
            textView.setBackgroundResource(R.drawable.tag_background);
            textView.setTextColor(Color.BLACK);
            textView.setText(tag);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 0;
            params.rightMargin = Utils.dipToPixels(getContext(), 5);
            params.bottomMargin = Utils.dipToPixels(getContext(), 5);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);
        }
}
}
