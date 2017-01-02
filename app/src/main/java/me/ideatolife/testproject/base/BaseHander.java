package me.ideatolife.testproject.base;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class BaseHander<T> {
    protected ArrayList<T> items = new ArrayList<T>();
    public BaseHander(String response) {
    }
    public BaseHander(JSONObject jsonObject){

    }

    public ArrayList<T> getItems() {
        return items;
    }

}
