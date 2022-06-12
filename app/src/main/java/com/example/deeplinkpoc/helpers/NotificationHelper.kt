package com.example.deeplinkpoc.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.deeplinkpoc.R
import com.example.deeplinkpoc.activities.MainActivity
import com.example.deeplinkpoc.model.NotificationModel
import kotlin.random.Random

object NotificationHelper {
    fun makeNotification(context: Context, notificationModel: NotificationModel) {
        val channelName = context.packageName
        val notificationChannelId = context.getString(R.string.default_notification_channel_id)
        val bundle = bundleOf("productId" to notificationModel.destinationContent)
        var destination : Int? = null

        when{
            notificationModel.destination.contains("tutorial") -> destination = R.id.tutorialFragment
            notificationModel.destination.contains("login") -> destination = R.id.loginFragment

        }
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.deep_link_nav)
            .setDestination(destination?:return)
            .setArguments(bundle)
            .createPendingIntent()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    notificationChannelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(notificationModel.title)
            .setContentText(notificationModel.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())

    }

}

