package com.example.lichaoqiang.aidl_test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aidlservice.Book;
import com.example.aidlservice.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bindBtn,simpleBtn,complicated;

    private IMyAidlInterface mMyAidlInterface;
    private ServiceConnection connection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            mMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name){
            mMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_bind: //绑定服务
                Intent intent = new Intent("com.example.aidlservice.MyService");
                intent.setPackage("com.example.aidlservice");
                bindService(intent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_simple:   //传输基本类型的数据，如int、double等
                if (mMyAidlInterface != null){
                    try{
                        mMyAidlInterface.transmitSimpleData(1000);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_complicated:  //传输复杂数据类型，如自定义类
                Book book1 = new Book();
                Book book2 = new Book(100,"我的书籍！");
                try{
                    mMyAidlInterface.transmitComplicatedData(book1);
                    mMyAidlInterface.transmitComplicatedData(book2);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void initView() {
        bindBtn = (Button)findViewById(R.id.btn_bind);
        simpleBtn = (Button)findViewById(R.id.btn_simple);
        complicated = (Button)findViewById(R.id.btn_complicated);

        bindBtn.setOnClickListener(this);
        simpleBtn.setOnClickListener(this);
        complicated.setOnClickListener(this);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}

