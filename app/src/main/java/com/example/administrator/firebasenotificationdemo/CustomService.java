package com.example.administrator.firebasenotificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.example.administrator.firebasenotificationdemo.CustomApplication.CHANNEL_ID;

public class CustomService extends Service {

    private String gameNameString, gameIntroductionString, gameUrlString;

    public static CustomService customService;
    private Notification notification;
    private Bitmap largeIconBitmap;
    private Resources resources;

    public CustomService() {
        customService = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /** 將資源中的圖片轉化成所需的Bitmap格式 */
        resources = getResources();
        largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.portia);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /** 取得從fcm傳遞過來的資料 */
        gameNameString = intent.getExtras().getString("gameName");
        gameIntroductionString = intent.getExtras().getString("gameIntroduction");
        gameUrlString = intent.getExtras().getString("gameUrl");

        /** 創建PendingIntent, 包含著webViewUrl */
        Intent mainIntent = new Intent(this, Main2Activity.class);
        mainIntent.putExtra("webViewUrl", gameUrlString);
        PendingIntent pendingIntent = PendingIntent.getActivity(                                       // PendingIntent可以看作是对Intent的包装, 当点击消息时就会向系统发送mainIntent意图, 就算在执行时当前Application已经不存在了, 也能通过存在PendingIntent里的Application的Context 照样执行Intent
                this, 1, mainIntent, 0);

        /** 实例化NotificationCompat.Builder对象 */
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(gameNameString)
                .setContentText(gameIntroductionString)
                .setSmallIcon(R.drawable.ic_videogame_asset_black_24dp)
                .setLargeIcon(largeIconBitmap)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);                                                        // 开始前台服务, 参数1 唯一的通知标识, 参数2 通知消息
        return START_NOT_STICKY;                                                                  // 如果在执行完onStartCommand后, 服务被异常kill掉, 系统不会自动重启该服务
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
