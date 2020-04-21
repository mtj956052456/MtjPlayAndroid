// Book.aidl
package com.mtj.aidl;


import com.mtj.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}