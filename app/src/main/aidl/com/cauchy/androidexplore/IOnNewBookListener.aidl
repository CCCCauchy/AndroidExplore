// OnNewBookListener.aidl
package com.cauchy.androidexplore;

import com.cauchy.androidexplore.Book;
// Declare any non-default types here with import statements

interface IOnNewBookListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBook(inout Book newBook);
}
