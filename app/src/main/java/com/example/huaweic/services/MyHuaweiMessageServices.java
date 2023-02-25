package com.example.huaweic.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import java.util.Map;

public class MyHuaweiMessageServices extends HmsMessageService {
    private static final String TAG ="PushDemoLog";
    public static final String CHANNEL_ID="PUSH_NOTIFICATIONS";
    @Override
    public void onNewToken(String token, Bundle bundle) {
        // Obtain a push token.
        Log.i(TAG, "have received refresh token " + token);
        // Check whether the token is null.
        if (!TextUtils.isEmpty(token)) {
            refreshedTokenToServer(token);
        }
    }
    @Override
    public void onDestroy(){
        Log.i(TAG, "Aqui no se va a destruir nada");
    }
    private void refreshedTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotificationChannel();
        Log.e("HMS","OnMessageReceived");
        Map<String,String> map=remoteMessage.getDataOfMap();
        for(String key: map.keySet()){
            Log.e("Data",key+" "+map.get(key));
        }
        String title=map.get("title");
        String message=map.get("message");
        if(title!=null&&message!=null){
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setContentTitle(title);
            builder.setContentText(message);
            //builder.setSmallIcon(R.mipmap.ic_launcher);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.notify(0,builder.build());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "Notifications";
        String description = "Push Notifications";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
