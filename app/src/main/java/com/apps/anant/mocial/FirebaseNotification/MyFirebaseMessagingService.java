package com.apps.anant.mocial.FirebaseNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.apps.anant.mocial.Activities.SelectGenre;
import com.apps.anant.mocial.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public static final String PRIMARY_CHANNEL = "default";
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    private Intent intent=null;
    String title = "";
    String body= "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final String title = remoteMessage.getData().get("title");
        final String message = remoteMessage.getData().get("message");

        Log.d("Notification ","Title received is ; "+title);
        Log.d("Notification ","Message received is ; "+message);


        final Random random = new Random();

        intent = new Intent(this, SelectGenre.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);




        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT); // (context, request code, intent, flags)
        notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.mipmap.mocial_icon);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.mocial_icon));
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSound(alarmSound);
        notificationBuilder.setTicker(message);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX );
        notificationBuilder.setAutoCancel(true);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL,getString(R.string.default_notification_channel_id), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(channel);
        }else {
            notificationManager.notify(random.nextInt(99999), notificationBuilder.build());
        }







//        Log.d("Message", "from ass"+remoteMessage);
//
//        Log.d("Messaging Service","FROM : "+remoteMessage.getFrom());
//
//        if(remoteMessage.getData().size()>0){
//
//            title = remoteMessage.getData().get("title");
//            body = remoteMessage.getData().get("body");
//            Log.d("Messaging Service","Message Data :"+remoteMessage.getData().get("title"));
//            Log.d("Messaging Service","Message Data :"+remoteMessage.getData().get("message"));
//        }
//
//        if(remoteMessage.getNotification() !=null){
//            Log.d("Messaging Service","Message Body : "+remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage.getNotification().getBody());
//        }
//    }
//
//
//    public void sendNotification(String body){
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//
//        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.icon)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(notificationSound)
//                .setContentIntent(pendingIntent);
////
//        NotificationManager notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,notifiBuilder.build());
//
////        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
////        manager.notify(0,notifiBuilder);

    }

}
