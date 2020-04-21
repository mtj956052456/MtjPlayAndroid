package com.mtj.aidl.car;

import android.os.IInterface;

public interface ICarManager extends IInterface {

    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public java.util.List<Car> getCarList() throws android.os.RemoteException;

    public void addCar(Car car) throws android.os.RemoteException;

}