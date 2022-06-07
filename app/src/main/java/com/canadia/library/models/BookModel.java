package com.canadia.library.models;
import com.fasterxml.jackson.annotation.*;

import org.json.JSONException;
import org.json.JSONObject;

public class BookModel {
    private long id;
    private long author_id;
    private String title;
    private String body;

    public BookModel(){};

    public BookModel(String title, String body, long id, long authorId) {
        this.title = title;
        this.body= body;
        this.id = id;
        this.author_id = authorId;
    }
    // use for convert json object to model
    public BookModel fromJson(JSONObject json) throws JSONException {
        return new BookModel(
                json.getString("title"),
                json.getString("body"),
                json.getLong("id"),
                json.getLong("author_id"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("author_id")
    public long getAuthor_id() {
        return author_id;
    }

    @JsonProperty("author_id")
    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }
    @JsonProperty("body")
    public String getBody() { return body; }
    @JsonProperty("body")
    public void setBody(String value) { this.body = value; }
}