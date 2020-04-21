package com.mtj.kotlinbase;

import android.os.SystemClock;

import com.zhenqi.baselibrary.util.Lg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mtj
 * @time 2019/12/4 2019 12
 * @des
 */
public class ThreadUtil {

    private ThreadUtil() {

    }

    public static ThreadUtil instance() {
        return Builder.threadUtil;
    }

    private static class Builder {
        final static ThreadUtil threadUtil = new ThreadUtil();
    }


    public String object = null;

    private synchronized void printObject() {
        while (object == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Lg.i(object);
    }

    private synchronized void changeObject() {
        object = "M先森";
        notifyAll();
    }

    public void startThread() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                printObject();
            }
        });

        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                changeObject();
            }
        });
        thread2.start();
    }


    public void stopThread() {

    }


}
