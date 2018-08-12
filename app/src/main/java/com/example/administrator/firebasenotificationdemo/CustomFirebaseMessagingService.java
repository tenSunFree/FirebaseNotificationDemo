package com.example.administrator.firebasenotificationdemo;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    /** 用來接收Firebase Cloud Messaging 傳遞過來的資料 */
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        /** 先判斷CustomService 是否已經啟動, 如果尚未啟動 才會進行啟動 */
        if (CustomApplication.isServiceRunning(this,
                        "com.example.administrator.firebasenotificationdemo.CustomService") == false) {

            /** 取得從Firebase Cloud Messaging傳遞過來的資料, 並調用Service 去彈出訊息通知 */
            Intent serviceIntent = new Intent(this, CustomService.class);
            serviceIntent.putExtra("gameName", remoteMessage.getData().get("gameName"));
            serviceIntent.putExtra("gameIntroduction", remoteMessage.getData().get("gameIntroduction"));
            serviceIntent.putExtra("gameUrl", remoteMessage.getData().get("gameUrl"));
            startService(serviceIntent);
        }
    }
}
