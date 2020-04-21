package com.mtj.aidl.car;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author mtj
 * @time 2019/11/28 2019 11
 * @des
 */
public class Car implements Parcelable {

    public int bookId;

    public int bookName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeInt(this.bookName);
    }

    public Car() {
    }

    private Car(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readInt();
    }

    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel source) {
            return new Car(source);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
}
