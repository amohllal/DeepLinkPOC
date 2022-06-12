package com.example.deeplinkpoc.helpers

import com.example.deeplinkpoc.model.NotificationModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        var title = ""
        var message = ""
        var productId = ""
        var destination = ""
        if (remoteMessage.data.isNotEmpty()) {
            remoteMessage.apply {
                title = data["title"].toString()
                message = data["message"].toString()
                productId = data["productId"].toString()
                destination = data["destination"].toString()
            }

            val notificationModel = NotificationModel(title, message, destination, productId)
            this.makeNotification(notificationModel)
        }
    }

}