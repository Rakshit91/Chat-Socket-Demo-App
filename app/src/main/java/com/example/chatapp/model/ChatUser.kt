package com.example.chatapp.model

data class ChatUser(
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val chatList:ArrayList<Message>
)
