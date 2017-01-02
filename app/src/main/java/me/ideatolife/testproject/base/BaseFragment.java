package me.ideatolife.testproject.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected Context mContext;
    protected View mView;
    protected Handler handler = new Handler();

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        mContext = null;
    }

    protected abstract void init();

    protected abstract int getLayoutId();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity act = getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.mContext == null) {
            this.mContext = inflater.getContext();
        }

        this.mView = inflater.inflate(this.getLayoutId(), container, false);
        init();
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
