package com.sravan.codetestapp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class HighlightResult {

    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("story_text")
    @Expose
    private StoryText storyText;
    @SerializedName("url")
    @Expose
    private Url url;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public StoryText getStoryText() {
        return storyText;
    }

    public void setStoryText(StoryText storyText) {
        this.storyText = storyText;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
