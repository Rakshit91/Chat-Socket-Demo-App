package com.example.chatapp.utils

import android.util.Log
import com.example.chatapp.model.ChatUser
import com.example.chatapp.model.Message

object ChatDatabase {
    var user: String = "DevUser"
    var currentScreen:String? = null
    var currentOpenedUser:String? = null
    val currentChatList:ArrayList<ChatUser> = arrayListOf(
        ChatUser("Alice", ArrayList()),
        ChatUser("Bob", ArrayList()),
        ChatUser("Charlie", ArrayList())
    )

    fun updateMessages(message: Message) {
        if (message.receiverId == user) {
            val index = currentChatList.indexOfFirst { user -> message.senderId == user.name }
            Log.e("ChatDatabase", "updateMessages: index $index")

            if(currentOpenedUser != null){
                message.read = currentOpenedUser == message.senderId
            }else{
                message.read = false
            }

            if(index == -1){
                currentChatList.add(ChatUser(message.senderId, arrayListOf(message)))
            }else{
                currentChatList[index].chatList.add(message)
            }
        }
    }

}