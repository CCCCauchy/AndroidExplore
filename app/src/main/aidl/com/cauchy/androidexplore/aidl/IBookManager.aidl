// IBookManager.aidl
package com.cauchy.androidexplore.aidl;

import com.cauchy.androidexplore.aidl.Book;
import com.cauchy.androidexplore.aidl.IOnNewBookListener;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookListener listener);

    void unregisterListener(IOnNewBookListener listener);
}
