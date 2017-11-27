package com.example.youp.Kuiper_Youp.Models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootObject implements Parcelable
{

    @SerializedName("Results")
    @Expose
    private List<Result> results = null;
    @SerializedName("NextId")
    @Expose
    private Integer nextId;
    public final static Parcelable.Creator<RootObject> CREATOR = new Creator<RootObject>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RootObject createFromParcel(Parcel in) {
            return new RootObject(in);
        }

        public RootObject[] newArray(int size) {
            return (new RootObject[size]);
        }

    }
            ;

    protected RootObject(Parcel in) {
        in.readList(this.results, (com.example.youp.Kuiper_Youp.Models.Result.class.getClassLoader()));
        this.nextId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public RootObject() {
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
        dest.writeValue(nextId);
    }

    public int describeContents() {
        return 0;
    }

}
