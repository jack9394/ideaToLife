package me.ideatolife.testproject.handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.ideatolife.testproject.base.BaseHander;
import me.ideatolife.testproject.models.FlickrObject;

public class FlickerHandler extends BaseHander {

    public FlickerHandler(JSONObject jsonObject) {
        super(jsonObject);
        JSONArray jsonArray = jsonObject.optJSONArray("items");
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    items.add(new FlickrObject(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
