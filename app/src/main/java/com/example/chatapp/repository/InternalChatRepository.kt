package com.example.chatapp.repository

import com.example.chatapp.model.ChatUser
import com.example.chatapp.model.Message
import com.example.chatapp.utils.ChatDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InternalChatRepository @Inject constructor() {

    // Get messages for a specific user
    fun getMessagesForUser(userId: String): Flow<List<Message>> {
        return flow {
            // Mocking a list of messages from a user
            delay(1000)  // Simulate network delay
            emit(listOf())
        }
    }

    fun getChatUsers(): Flow<List<ChatUser>> = flow {
        delay(1000) // simulate network/db delay
        emit(ChatDatabase.currentChatList)
    }

    fun getChatUserById(userId: String): Flow<ChatUser> = flow {
        val user = ChatDatabase.currentChatList.find { it.name == userId }
        emit(user!!)
    }

    // Send a message to a user
    suspend fun sendMessage(userId: String, message: String) {
        // Mock sending a message (you can add it to a database or API here)
        delay(500)  // Simulate network delay
    }
}