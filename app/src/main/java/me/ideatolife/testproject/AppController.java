package me.ideatolife.testproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    private static final String TAG = AppController.class
            .getSimpleName();
    private SharedPreferences pref;
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    public SharedPreferences getPref() {
        return pref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        pref = getSharedPreferences(TAG,MODE_PRIVATE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pref.edit().clear().apply();

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }



    public static AppController getmInstance() {
        return mInstance;
    }
}