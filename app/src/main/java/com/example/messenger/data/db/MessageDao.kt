package com.example.messenger.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<Message>>

    @Query("SELECT id, isLiked FROM messages")
    suspend fun getMessageLikes(): List<MessageLike>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<Message>)

    @Query("UPDATE messages SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Int, isLiked: Boolean)
}
