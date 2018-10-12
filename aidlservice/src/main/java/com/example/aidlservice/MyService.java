package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void transmitSimpleData(int num) throws RemoteException {
            Log.i("MyService", "MyService 传输的基本类型数据为：" + num);
        }

        @Override
        public void transmitComplicatedData(Book book) throws RemoteException {
            Log.i("MyService", "MyService 传输的复杂数据为：" + book.toString());
        }
    };
}
