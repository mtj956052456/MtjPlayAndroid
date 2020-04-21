package com.mtj.aidl.car;

import android.os.IBinder;

/**
 * Local-side IPC implementation stub class.
 */
public class ICarManagerImpl extends android.os.Binder implements ICarManager {

    //Binder的唯一标识
    private static final java.lang.String DESCRIPTOR = "com.mtj.aidl.car.ICarManager";

    /**
     * Construct the stub at attach it to the interface.
     */
    public ICarManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    /**
     * 将IBinder对象强制转换为com.mtj.aidl.ICarManager接口，
     * 如果需要，生成代理。
     */
    public static ICarManager asInterface(android.os.IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        //如果CS在同一进程 返回服务端对象本身
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof ICarManager))) {
            return ((ICarManager) iin);
        }
        //否则返回时系统封装后的 ICarManagerImpl.Proxy对象
        return new ICarManagerImpl.Proxy(obj);
    }

    @Override
    public android.os.IBinder asBinder() {
        return this;
    }

    // 1.CS在同一个进程的时候不会走该方法
    // 2.CS不在同一个进程的时候会走该方法
    // 此方法运行在服务端中的Binder线程池中,
    // 当客户端发起跨进程请求时,远程请求会通过系统底层封装后交由此方法来处理.
    // 服务端通过code可以确定客户端请求的目标方法是什么,
    // 接着从data中取出目标方法所需的参数(如果目标方法有参数的话) 然后执行目标方法,
    // 当目标方法执行完毕后,就向reply中写入返回值(如果目标方法有返回值的话).
    // 注意:
    // 如果方法返回的是false,呢客户端会请求失败,因此我们可以利用这个特性来做权限验证,我们也不希望随意一个进程
    // 都能远程访问我们的服务
    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        java.lang.String descriptor = DESCRIPTOR;
        // 服务端通过code可以确定客户端请求的目标方法是什么,
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(descriptor);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(descriptor);
                java.util.List<Car> _result = this.getCarList();
                reply.writeNoException();
                // 当目标方法执行完毕后,就向reply中写入返回值(如果目标方法有返回值的话)
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addBook: {
                data.enforceInterface(descriptor);
                // 接着从data中取出目标方法所需的参数(如果目标方法有参数的话)
                Car _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Car.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                // 然后执行目标方法
                this.addCar(_arg0);
                reply.writeNoException();
                return true;
            }
            default: {
                return super.onTransact(code, data, reply, flags);
            }
        }
    }

    @Override
    public java.util.List<Car> getCarList() throws android.os.RemoteException {
        return null;
    }

    @Override
    public void addCar(Car car) throws android.os.RemoteException {

    }

    private static class Proxy implements ICarManager {
        private android.os.IBinder mRemote;

        Proxy(android.os.IBinder remote) {
            mRemote = remote;
        }

        @Override
        public android.os.IBinder asBinder() {
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public java.util.List<Car> getCarList() throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<Car> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                //发起RPC(远程过程调用)请求 同时线程挂起
                mRemote.transact(ICarManager.TRANSACTION_getBookList, _data, _reply, 0);
                //调用onTransact后 返回值
                _reply.readException();
                //从返回值取出RPC的返回结果
                _result = _reply.createTypedArrayList(Car.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addCar(Car car) throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((car != null)) {
                    _data.writeInt(1);
                    car.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(ICarManager.TRANSACTION_addBook, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {

        }
    };
}