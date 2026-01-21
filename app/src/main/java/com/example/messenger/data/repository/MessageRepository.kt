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
            val localLikes = messageDao.getMessageLikes().associateBy { it.id }
            val remoteMessages = messageApi.getMessages()
            val mergedMessages = remoteMessages.map { message ->
                val isLiked = localLikes[message.id]?.isLiked ?: false
                message.copy(isLiked = isLiked)
            }
            messageDao.insertAll(mergedMessages)
        } catch (e: Exception) {
            // Handle error or rethrow. For now, we just log or ignore to keep UI simple, 
            // but in a real app we'd expose an error state.
            e.printStackTrace()
            throw e
        }
    }

    suspend fun toggleLike(message: Message) {
        messageDao.updateLike(message.id, !message.isLiked)
    }
}
