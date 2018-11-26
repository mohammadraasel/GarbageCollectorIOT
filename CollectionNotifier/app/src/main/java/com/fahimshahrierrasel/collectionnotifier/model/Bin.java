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
    private int currentLevel;
    @SerializedName("depth")
    @Expose
    private int depth;
    @SerializedName("echo_pin")
    @Expose
    private int echoPin;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latitude")
    @Expose
    private float latitude;
    @SerializedName("longitude")
    @Expose
    private float longitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("notify_level")
    @Expose
    private int notifyLevel;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("trig_pin")
    @Expose
    private int trigPin;
    @SerializedName("tuned")
    @Expose
    private boolean tuned;
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
        this.currentLevel = ((int) in.readValue((int.class.getClassLoader())));
        this.depth = ((int) in.readValue((int.class.getClassLoader())));
        this.echoPin = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.latitude = ((float) in.readValue((float.class.getClassLoader())));
        this.longitude = ((float) in.readValue((float.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.notifyLevel = ((int) in.readValue((int.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.trigPin = ((int) in.readValue((int.class.getClassLoader())));
        this.tuned = ((boolean) in.readValue((boolean.class.getClassLoader())));
    }

    public Bin() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getEchoPin() {
        return echoPin;
    }

    public void setEchoPin(int echoPin) {
        this.echoPin = echoPin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTrigPin() {
        return trigPin;
    }

    public void setTrigPin(int trigPin) {
        this.trigPin = trigPin;
    }

    public boolean isTuned() {
        return tuned;
    }

    public void setTuned(boolean tuned) {
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