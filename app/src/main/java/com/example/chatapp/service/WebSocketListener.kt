package com.example.chatapp.service

import android.util.Log
import com.example.chatapp.model.Message
import com.example.chatapp.ui.chat.ChatViewModel
import com.example.chatapp.utils.ChatDatabase
import com.google.gson.Gson
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketListener(
    private val viewModel: ChatViewModel
): WebSocketListener() {

    private val TAG = "Test"

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        viewModel.setStatus(true)
        webSocket.send("Android Device Connected")
        for(i in ChatDatabase.currentChatList.indices){
            for (j in ChatDatabase.currentChatList[i].chatList.indices){
                if(!ChatDatabase.currentChatList[i].chatList[j].sent){
                    ChatDatabase.currentChatList[i].chatList[j].sent = true
                    val messageJson = Gson().toJson(ChatDatabase.currentChatList[i].chatList[j])
                    webSocket.send(messageJson?:"")
                }
            }
        }
        viewModel.updateDatabase()
        Log.d(TAG, "onOpen:")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        try {
            val message = Gson().fromJson(text, Message::class.java)
            Log.e(TAG, "onMessage: $text")
            ChatDatabase.updateMessages(message)
            viewModel.updateDatabase()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "onClosing: $code $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        viewModel.setStatus(false)
        Log.d(TAG, "onClosed: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure: ${t.message} $response")
        viewModel.setStatus(false)
        super.onFailure(webSocket, t, response)
    }
}