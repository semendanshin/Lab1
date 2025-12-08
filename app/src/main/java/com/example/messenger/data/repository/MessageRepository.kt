package com.example.messenger.data.repository

import com.example.messenger.data.api.MessageApi
import com.example.messenger.data.db.MessageDao
import com.example.messenger.data.model.Message
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val messageDao: MessageDao,
    private val messageApi: MessageApi
) {
    val messages: Flow<List<Message>> = messageDao.getAllMessages()

    suspend fun refreshMessages() {
        try {
            val remoteMessages = messageApi.getMessages()
            messageDao.insertAll(remoteMessages)
        } catch (e: Exception) {
            // Handle error or rethrow. For now, we just log or ignore to keep UI simple, 
            // but in a real app we'd expose an error state.
            e.printStackTrace()
            throw e
        }
    }
}
