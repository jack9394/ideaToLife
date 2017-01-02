package me.ideatolife.testproject.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.ideatolife.testproject.R;

/**
 * Created by jack on 1/2/17.
 */

public class TextWithTitleView extends CardView {
    private TextView titleView, textView;

    public TextWithTitleView(Context context) {
        super(context);
        init(context);

    }

    public TextWithTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }


    private void init(Context context) {
        inflate(context, R.layout.item_texttitle_view, this);
        titleView = (TextView) findViewById(R.id.titleView);
        textView = (TextView) findViewById(R.id.textView);
    }

    public TextView getTextView() {
        return textView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setText(String text) {
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(text));
        }
    }

    public void setTitleAndText(String title, String text) {
        setTitle(title);
        setText(text);
    }
}
