package com.fahimshahrierrasel.collectionnotifier.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bin implements Parcelable {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("current_level")
    @Expose
    private String currentLevel;
    @SerializedName("depth")
    @Expose
    private String depth;
    @SerializedName("echo_pin")
    @Expose
    private String echoPin;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("notify_level")
    @Expose
    private String notifyLevel;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("trig_pin")
    @Expose
    private String trigPin;
    @SerializedName("tuned")
    @Expose
    private String tuned;
    public final static Parcelable.Creator<Bin> CREATOR = new Creator<Bin>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Bin createFromParcel(Parcel in) {
            return new Bin(in);
        }

        public Bin[] newArray(int size) {
            return (new Bin[size]);
        }

    };

    protected Bin(Parcel in) {
        this.count = ((int) in.readValue((int.class.getClassLoader())));
        this.currentLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.depth = ((String) in.readValue((String.class.getClassLoader())));
        this.echoPin = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.notifyLevel = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.trigPin = ((String) in.readValue((String.class.getClassLoader())));
        this.tuned = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Bin() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getEchoPin() {
        return echoPin;
    }

    public void setEchoPin(String echoPin) {
        this.echoPin = echoPin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(String notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrigPin() {
        return trigPin;
    }

    public void setTrigPin(String trigPin) {
        this.trigPin = trigPin;
    }

    public String getTuned() {
        return tuned;
    }

    public void setTuned(String tuned) {
        this.tuned = tuned;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(count);
        dest.writeValue(currentLevel);
        dest.writeValue(depth);
        dest.writeValue(echoPin);
        dest.writeValue(id);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(name);
        dest.writeValue(notifyLevel);
        dest.writeValue(status);
        dest.writeValue(trigPin);
        dest.writeValue(tuned);
    }

    public int describeContents() {
        return 0;
    }

}
