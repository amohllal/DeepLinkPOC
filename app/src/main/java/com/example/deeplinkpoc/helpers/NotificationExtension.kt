package com.example.deeplinkpoc.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.deeplinkpoc.R
import com.example.deeplinkpoc.model.NotificationModel
import kotlin.random.Random


fun Context.makeNotification(notificationModel: NotificationModel) {
    val channelName = this.packageName
    val notificationChannelId = this.getString(R.string.default_notification_channel_id)
    val bundle = bundleOf("productId" to notificationModel.destinationContent)

    val destination = notificationModel.checkDestination()

    val pendingIntent = NavDeepLinkBuilder(
        this
    )
        .setGraph(R.navigation.deep_link_nav)
        .setDestination(destination ?: return)
        .setArguments(bundle)
        .createPendingIntent()

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

fun NotificationModel.checkDestination(): Int? {
    var destination: Int? = null
    when {
        this.destination.contains("tutorial") -> destination = R.id.tutorialFragment
        this.destination.contains("login") -> destination = R.id.loginFragment
    }
    return destination
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





