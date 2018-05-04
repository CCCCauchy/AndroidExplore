package com.cauchy.androidexplore.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.cauchy.androidexplore.R;
import com.cauchy.androidexplore.service.MessengerServerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MessengerClientActivity extends AppCompatActivity {

    @BindView(R.id.send_message)
    Button sendMessage;
    @BindView(R.id.received_message)
    TextView receivedMessage;

    private int clickCount = 0;
    private Unbinder unbinder = null;
    private Messenger clientMessenger = null;
    private Messenger sendMessenger = null;

    private Handler clientHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            receivedMessage.setText(msg.getData().getString("reply"));
        }
    };

    private ServiceConnection serverConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessenger = new Messenger(service);

            Message message = Message.obtain();
            message.what = 0;
            message.replyTo = clientMessenger;
            Bundle bundle = new Bundle();
            bundle.putString("send", "我是客户端");
            message.setData(bundle);

            try {
                sendMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
        unbinder = ButterKnife.bind(this);
        clientMessenger = new Messenger(clientHandler);
        bindService(new Intent(this, MessengerServerService.class), serverConnection, Service.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.send_message)
    public void onViewClicked() {
        clickCount++;
        Message message = Message.obtain();
        message.what = 0;
        message.replyTo = clientMessenger;
        Bundle bundle = new Bundle();
        bundle.putString("send", "我是客户端" + clickCount);
        message.setData(bundle);

        try {
            sendMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
