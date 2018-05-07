package com.cauchy.androidexplore.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.cauchy.androidexplore.Book;
import com.cauchy.androidexplore.IBookManager;
import com.cauchy.androidexplore.IOnNewBookListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AIDLServerService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private CopyOnWriteArrayList<Book> bookList = null;
    private RemoteCallbackList<IOnNewBookListener> callbackList = null;

    private final Binder binder = new IBookManager.Stub() {

        //对客户端进行权限验证
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission("com.cauchy.androidexplore.aidl");
            if (check == PackageManager.PERMISSION_DENIED) {
                Log.e(TAG, "no permission");
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
                if (!packageName.startsWith("com.cauchy")) {
                    Log.e(TAG, "no permission");
                    return false;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i(TAG, "addBook--" + book.toString());
            bookList.add(book);
            int n = callbackList.beginBroadcast();
            for (int i = 0; i < n; i++) {
                IOnNewBookListener listener = callbackList.getBroadcastItem(i);
                if (listener != null)
                    listener.onNewBook(book);
            }
            callbackList.finishBroadcast();
        }

        @Override
        public void registerListener(IOnNewBookListener listener) throws RemoteException {
            callbackList.register(listener);

        }

        @Override
        public void unregisterListener(IOnNewBookListener listener) throws RemoteException {
            callbackList.unregister(listener);
        }
    };

    public AIDLServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        bookList = new CopyOnWriteArrayList<>();
        callbackList = new RemoteCallbackList<>();
        return binder;
    }

}
