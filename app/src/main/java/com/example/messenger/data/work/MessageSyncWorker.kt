package com.example.messenger.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.data.api.RetrofitClient
import com.example.messenger.data.db.AppDatabase
import com.example.messenger.data.notification.NotificationHelper
import com.example.messenger.data.repository.MessageRepository

class MessageSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MessageRepository(database.messageDao(), RetrofitClient.api)

        return try {
            repository.refreshMessages()
            NotificationHelper.showSyncSuccess(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
