package me.ideatolife.testproject.volley;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import me.ideatolife.testproject.AppController;

public class PerformeAction<T> {
    public static final int DEPRECATED_GET_OR_POST = -1;
    public static final int GET = 0;
    public static final int POST = 1;

    private Context context;
    private boolean showLoader;
    private HashMap<String, String> bodyParams;
    private int type;
    private Response.Listener listener;
    private String url;
    private Response.ErrorListener errorListener;
    private boolean asJson = false;

    public String getUrl() {
        return url;
    }

    private <T> PerformeAction(Builder builder) {
        this.context = builder.context;
        this.showLoader = builder.showLoader;
        this.bodyParams = builder.bodyParams;
        this.type = builder.type;
        this.listener = builder.listener;
        this.url = builder.url;
        this.errorListener = builder.errorListener;
        this.asJson=builder.asJson;

    }

    public void run() {
        if (asJson) {
            sendJsonRequest();
        } else {
            sendStringRequest();
        }
    }

    private void sendJsonRequest() {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        if (showLoader)
            pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(type, url, null, listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return bodyParams;
            }
        };
        AppController.getmInstance().addToRequestQueue(jsonObjReq, url);
    }

    private void sendStringRequest() {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        if (showLoader)
            pDialog.show();
        StringRequest jsonObjReq = new StringRequest(type, url, listener, errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return bodyParams;
            }

        };
        AppController.getmInstance().addToRequestQueue(jsonObjReq, url);
    }

    public static class Builder<T> {
        private Context context;
        private boolean showLoader = true;
        private HashMap<String,String> bodyParams=new HashMap<>();
        private int type = GET;
        private Response.Listener listener;
        private String url;
        private Response.ErrorListener errorListener;
        private boolean asJson = false;

        public Builder() {
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder asJson(boolean asJson) {
            this.asJson = asJson;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder listener(Response.Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder showLoader(boolean showLoader) {
            this.showLoader = showLoader;
            return this;
        }

        public Builder errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public Builder bodyParams(HashMap<String,String> bodyParams) {
            this.bodyParams = bodyParams;
            return this;
        }

        public PerformeAction build() {
            return new PerformeAction(this);
        }
    }


}