package brotic.findmyfriends.Service.GCM;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import java.io.IOException;

import brotic.findmyfriends.Activity.ContactActivity;
import brotic.findmyfriends.Activity.LauncherActivity;
import brotic.findmyfriends.Activity.MainLoginActivity;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;

/**
 * @author Brice VICO
 * @version 1.0.0
 * @date 17/01/2016
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("type");

        if (message != null) {
            if (message.equals("position"))
                sendPositionNotification(data.getString("username"));
            else if (message.equals("acceptFriend"))
                sendAcceptNotification(data.getString("username"));
            else if (message.equals("addFriend"))
                senAddFriendNotification();
            else if (message.equals("askPosition"))
                sendAskPositionNotification(data.getString("username"));
        }
    }

    private void sendAskPositionNotification(String username) {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.askPositionTitle))
                        .setContentText(username + " " + getString(R.string.askPosition));

        Intent it = new Intent(this, LauncherActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mBuilder.build());

    }

    private void senAddFriendNotification() {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.addFriendNotifTitle))
                        .setContentText(getString(R.string.addFriendNotif));

        Intent it = new Intent(this, LauncherActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mBuilder.build());
    }

    private void sendAcceptNotification(String username) {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.askFriendAcceptTitle))
                        .setContentText(username + " " + getString(R.string.askFriendAccept));

        Intent it = new Intent(this, LauncherActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mBuilder.build());
    }

    private void sendPositionNotification(String username) {
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notification_red)
                        .setContentTitle(username + " " + getString(R.string.friendShareTitle))
                        .setContentText(username + " " + getString(R.string.friendShareTitle));

        Intent it = new Intent(this,LauncherActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        it,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mBuilder.build());
    }
}
