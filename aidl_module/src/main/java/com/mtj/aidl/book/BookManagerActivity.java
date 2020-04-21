package com.mtj.aidl.book;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mtj.aidl.Book;
import com.mtj.aidl.IBookManager;
import com.mtj.aidl.IOnNewBookArrivedListener;
import com.mtj.aidl.R;
import com.zhenqi.baselibrary.util.Lg;

import java.util.List;
import java.util.Random;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;


    private IBookManager mRemoteBookManager;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Lg.d("receive new book: " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Lg.i("查询book集合 : " + list.toString());
                Book newBook = new Book(3, "Android艺术探索");
                bookManager.addBook(newBook);
                Lg.i("添加一本书: " + newBook);
                List<Book> newList = bookManager.getBookList();
                Lg.i("查询book集合 : " + newList.toString());
                mRemoteBookManager.registerListener(mIOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Lg.e("binder died");
        }
    };

    //此方法运行在客户端的Binder线程池中  需要用Handler切换到主线程
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        try {
            if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
                Lg.i("unregister listener : " + mIOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mIOnNewBookArrivedListener);
                unbindService(mConnection);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void addBook(View view) {
        if (mRemoteBookManager != null) {
            int bookId = new Random().nextInt(100);
            try {
                mRemoteBookManager.addBook(new Book(bookId, "孟德新书"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
