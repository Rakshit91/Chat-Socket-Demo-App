package com.example.chatapp.repository

import com.example.chatapp.model.ChatUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRepository @Inject constructor() {
    fun getChatUsers(): Flow<List<ChatUser>> = flow {
        delay(1000) // simulate network/db delay
    }
}
