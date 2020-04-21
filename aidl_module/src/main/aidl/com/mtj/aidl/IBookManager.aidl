// Book.aidl
package com.mtj.aidl;

import com.mtj.aidl.Book;
import com.mtj.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements
interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);

}
