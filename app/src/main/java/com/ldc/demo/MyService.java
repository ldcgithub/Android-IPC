package com.ldc.demo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented")
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void connService()
        {
        }
        public void NotifiStart()
        {
            Intent intent=new Intent(MyService.this,MainActivity.class);
            PendingIntent pi=PendingIntent.getActivity(MyService.this,0,intent,0);
            Notification notifi=new NotificationCompat.Builder(MyService.this)
                    .setContentTitle("我他妈看今天谁敢念诗")
                    .setContentText("苟利国家生死以，岂因福祸避趋之！")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pi)
                    .build();
            startForeground(1,notifi);


            Intent intent1=new Intent("notifi");
            LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent1);
        }
        public void NotifiStop()
        {

            Intent intent2=new Intent("stopN");
            LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent2);
            stopForeground(true);

        }


    }
}
