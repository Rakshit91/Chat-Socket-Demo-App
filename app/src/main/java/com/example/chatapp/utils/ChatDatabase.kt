package com.example.chatapp.utils

import android.util.Log
import com.example.chatapp.model.ChatUser
import com.example.chatapp.model.Message

object ChatDatabase {
    var user: String = "DevUser"

    val currentChatList:ArrayList<ChatUser> = arrayListOf(
        ChatUser("Alice", "Hey, what's up?", "10:30 AM", ArrayList()),
        ChatUser("Bob", "See you tomorrow!", "Yesterday", ArrayList()),
        ChatUser("Charlie", "Typing...", "Just now", ArrayList())
    )

    fun updateMessages(message:Message){
        if(message.receiverId == user){
            val index = currentChatList.indexOfFirst { user -> message.senderId == user.name }
            currentChatList[index].chatList.add(message)
            Log.e("ChatDatabase", "updateMessages: messaged updated")
        }
    }

}