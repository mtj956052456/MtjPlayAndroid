package com.mtj.aidl.binder_pool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.mtj.aidl.IComputeCenter;
import com.mtj.aidl.ISecurityCenter;
import com.mtj.aidl.R;
import com.zhenqi.baselibrary.util.Lg;

public class BinderPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }
    private void doWork() {
        Lg.d("visit ISecurityCenter1");
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter mSecurityBinder = SecurityCenterImpl.asInterface(securityBinder);
        Lg.d("visit ISecurityCenter");
        String msg = "Hello Word Android";
        Lg.i("content: " + msg);
        try {
            String password = mSecurityBinder.encrypt(msg);
            Lg.i("encrypt : " + password);
            Lg.i("decrypt : " + mSecurityBinder.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Lg.d("visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        IComputeCenter mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            Lg.i("3+5 = " + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
