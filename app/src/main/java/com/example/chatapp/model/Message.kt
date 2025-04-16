package com.example.chatapp.model

data class Message(
    val text: String,
    val senderId: String, // ID of the sender
    val receiverId:String,
    val timestamp: String,
    var sent:Boolean,
    var read:Boolean
)

