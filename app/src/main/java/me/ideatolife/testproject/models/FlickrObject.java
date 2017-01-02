package me.ideatolife.testproject.models;

import org.json.JSONObject;

import java.io.Serializable;


public class FlickrObject implements Serializable{

    private String title;
    private String link;
    private String imageUrl;
    private String dateTaken;
    private String description;
    private String published;
    private String author;
    private String authorId;
    private String tags;

    public FlickrObject(JSONObject jsonObject) {
        this.title = jsonObject.optString("title");
        this.link = jsonObject.optString("link");
        JSONObject temp = jsonObject.optJSONObject("media");
        if (temp != null) {
            this.imageUrl = temp.optString("m");
        }
        this.dateTaken = jsonObject.optString("date_taken");
        this.description = jsonObject.optString("description");
        this.published = jsonObject.optString("published");
        this.author = jsonObject.optString("author");
        this.authorId = jsonObject.optString("author_id");
        this.tags = jsonObject.optString("tags");
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTags() {
        return tags;
    }
}

