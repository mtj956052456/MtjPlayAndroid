package com.mtj.aidl.binder_pool;


import android.os.RemoteException;

import com.mtj.aidl.ISecurityCenter;

/**
 * @author mtj
 * @time 2019/12/8 2019 12
 * @des
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';


    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
