package com.mtj.aidl.binder_pool;

import android.os.RemoteException;

import com.mtj.aidl.IComputeCenter;

/**
 * @author mtj
 * @time 2019/12/8 2019 12
 * @des
 */
public class ComputeImpl extends IComputeCenter.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
