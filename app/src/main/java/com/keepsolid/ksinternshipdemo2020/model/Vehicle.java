package com.keepsolid.ksinternshipdemo2020.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Vehicle implements Parcelable {

    private String modelName;
    private String brandName;
    private int maxSpeed;
    private boolean isIgnited;

    public Vehicle(String brandName, String modelName, int maxSpeed, boolean isIgnited) {
        this.modelName = modelName;
        this.brandName = brandName;
        this.maxSpeed = maxSpeed;
        this.isIgnited = isIgnited;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isIgnited() {
        return isIgnited;
    }

    public void setIgnited(boolean ignited) {
        isIgnited = ignited;
    }

    @NonNull
    @Override
    public String toString() {
        return "Car: " + brandName + " " + modelName + "\n" + "Max speed: " + maxSpeed + "\n" + "Is Ignited: " + isIgnited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (maxSpeed != vehicle.maxSpeed) return false;
        if (isIgnited != vehicle.isIgnited) return false;
        if (modelName != null ? !modelName.equals(vehicle.modelName) : vehicle.modelName != null)
            return false;
        return brandName != null ? brandName.equals(vehicle.brandName) : vehicle.brandName == null;
    }

    @Override
    public int hashCode() {
        int result = modelName != null ? modelName.hashCode() : 0;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + maxSpeed;
        result = 31 * result + (isIgnited ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(brandName);
        parcel.writeString(modelName);
        parcel.writeInt(maxSpeed);
        parcel.writeByte(isIgnited ? (byte) 1 : (byte) 0);
    }

    protected Vehicle(Parcel in) {
        modelName = in.readString();
        brandName = in.readString();
        maxSpeed = in.readInt();
        isIgnited = in.readByte() != 0;
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };


}
