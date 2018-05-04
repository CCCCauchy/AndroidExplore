package com.cauchy.androidexplore.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerServerService extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private Messenger messenger = null;
    private Handler receiveMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "received message");
            Messenger replyMessenger = msg.replyTo;

            Message message = Message.obtain();
            message.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("reply", msg.getData().getString("send") + "\n服务端已经收到信息");
            message.setData(bundle);
            try {
                replyMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    public MessengerServerService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        messenger = new Messenger(receiveMessageHandler);
        return messenger.getBinder();
    }
}
