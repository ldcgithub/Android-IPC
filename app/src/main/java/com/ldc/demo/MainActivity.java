package com.ldc.demo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected MyService.MyBinder mMyBinder;
    private MyChangeBroadcastReceiver mMyBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1= (Button) findViewById(R.id.bt_startService);
        Button bt2= (Button) findViewById(R.id.bt_stopService);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bindMyService();
        registBroadcast();
    }

    private void registBroadcast() {
        mMyBroadcastReceiver=new MyChangeBroadcastReceiver();
        IntentFilter intenFilter=new IntentFilter();
        intenFilter.addAction("notifi");
        intenFilter.addAction("stopN");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMyBroadcastReceiver,intenFilter);
    }

    private void bindMyService() {
        Intent intent=new Intent(this,MyService.class);
        bindService(intent,mConn,BIND_AUTO_CREATE);
    }

    ServiceConnection mConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMyBinder= (MyService.MyBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_startService:
                mMyBinder.NotifiStart();
                Toast.makeText(this, "开启通知", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_stopService:
                mMyBinder.NotifiStop();
                Toast.makeText(this, "关闭通知", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class MyChangeBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction())
            {
                case "notifi":
                    Toast.makeText(context, "顶部通知已经打开", Toast.LENGTH_SHORT).show();
                    break;
                case "stopN":
                    Toast.makeText(context, "顶部通知已经关闭", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }
}
