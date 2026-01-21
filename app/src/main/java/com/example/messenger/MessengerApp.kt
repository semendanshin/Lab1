package com.example.messenger

import android.app.Application
import com.example.messenger.data.notification.NotificationHelper
import com.example.messenger.data.work.WorkScheduler

class MessengerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
        WorkScheduler.scheduleMessageSync(this)
    }
}
