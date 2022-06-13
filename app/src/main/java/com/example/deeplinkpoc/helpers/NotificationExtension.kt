package com.example.deeplinkpoc.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkSaveStateControl
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.deeplinkpoc.R
import com.example.deeplinkpoc.model.NotificationModel
import com.example.implicit.HostingActivity
import kotlin.random.Random


fun Context.makeNotification(notificationModel: NotificationModel) {
    val channelName = this.packageName
    val notificationChannelId = this.getString(R.string.default_notification_channel_id)

    val intent = Intent(this, HostingActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra("deeplink",notificationModel.destination.toUri())
    }

    val pendingIntent =
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notificationManager =
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.sendNotification(
        channelName,
        notificationChannelId,
        pendingIntent,
        notificationModel,
        this
    )

}
fun NotificationManager.sendNotification(
    channelName: String,
    notificationChannelId: String,
    pendingIntent: PendingIntent,
    notificationModel: NotificationModel,
    applicationContext: Context
) {
    val notificationId = Random.nextInt()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(
                notificationChannelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
        this.createNotificationChannel(notificationChannel)
    }

    val builder = NotificationCompat.Builder(applicationContext, notificationChannelId)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(notificationModel.title)
        .setContentText(notificationModel.message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    this.notify(notificationId, builder.build())
}





