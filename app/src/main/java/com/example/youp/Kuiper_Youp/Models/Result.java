package com.example.youp.Kuiper_Youp.Models;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable
{

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Feed")
    @Expose
    private Integer feed;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Summary")
    @Expose
    private String summary;
    @SerializedName("PublishDate")
    @Expose
    private String publishDate;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("Related")
    @Expose
    private List<String> related = null;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("IsLiked")
    @Expose
    private Boolean isLiked;
    public final static Parcelable.Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    };


    protected Result(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.feed = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.summary = ((String) in.readValue((String.class.getClassLoader())));
        this.publishDate = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.related = new ArrayList<>();
        this.categories = new ArrayList<>();
        in.readList(this.related, (java.lang.String.class.getClassLoader()));
        in.readList(this.categories, (Category.class.getClassLoader()));
        this.isLiked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Result() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeed() {
        return feed;
    }

    public void setFeed(Integer feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getRelated() {
        return related;
    }

    public void setRelated(List<String> related) {
        this.related = related;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(feed);
        dest.writeValue(title);
        dest.writeValue(summary);
        dest.writeValue(publishDate);
        dest.writeValue(image);
        dest.writeValue(url);
        dest.writeList(related);
        dest.writeList(categories);
        dest.writeValue(isLiked);
    }

    public int describeContents() {
        return 0;
    }

}