package com.cauchy.androidexplore.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cauchy.androidexplore.Book;
import com.cauchy.androidexplore.IBookManager;
import com.cauchy.androidexplore.IOnNewBookListener;
import com.cauchy.androidexplore.R;
import com.cauchy.androidexplore.service.AIDLServerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AIDLClientActivity extends AppCompatActivity {
    @BindView(R.id.received_message)
    TextView receivedMessage;

    private int count = 0;
    private Unbinder unbinder = null;
    private IBookManager iBookManager = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                iBookManager.registerListener(listener);
                receivedMessage.setText(iBookManager.getBookList().toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IOnNewBookListener listener = new IOnNewBookListener.Stub() {
        @Override
        public void onNewBook(Book newBook) throws RemoteException {
            receivedMessage.setText("list = " + iBookManager.getBookList().toString()
                    + "\nnewbook = " + newBook);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlclient);
        unbinder = ButterKnife.bind(this);
        bindService(new Intent(this, AIDLServerService.class), serviceConnection, Service.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        if (iBookManager != null && iBookManager.asBinder().isBinderAlive()) {
            try {
                iBookManager.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.add_book)
    public void onViewClicked() {
        count++;
        try {
            iBookManager.addBook(new Book("Android艺术探索" + count, "android"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
