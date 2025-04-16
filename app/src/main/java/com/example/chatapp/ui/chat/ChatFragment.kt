package com.example.chatapp.ui.chat

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.model.ChatUser
import com.example.chatapp.model.Message
import com.example.chatapp.service.WebSocketListener
import com.example.chatapp.utils.ChatDatabase
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val viewModel: ChatViewModel by activityViewModels()
    private lateinit var chatAdapter: ChatAdapter

    private var userId: String? = null
    var currentUser:ChatUser? = null

    lateinit var binding: FragmentChatBinding
    val currentId = "6"
    var index = ChatDatabase.currentChatList.indexOfFirst { user -> userId == user.name }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentChatBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getString("userId")
        index = ChatDatabase.currentChatList.indexOfFirst { user -> userId == user.name }
        startObservers()
        for(i in ChatDatabase.currentChatList[index].chatList.indices){
            ChatDatabase.currentChatList[index].chatList[i].read = true
        }
        viewModel.updateDatabase()
        chatAdapter = ChatAdapter(viewModel.currentUser.value ?: "")
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.messageRecyclerView.adapter = chatAdapter
        chatAdapter.submitList(ChatDatabase.currentChatList[index].chatList)

        binding.noChat.visibility = if(ChatDatabase.currentChatList[index].chatList.isEmpty()) View.VISIBLE else View.GONE
        binding.messageRecyclerView.visibility = if(ChatDatabase.currentChatList[index].chatList.isEmpty()) View.GONE else View.VISIBLE

        // Handle send button click
        binding.sendButton.setOnClickListener {
            val messageText = binding.editMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                viewModel.webSocket?.send(messageText)
                userId?.let {
                    viewModel.sendMessage(it, messageText)
                    val currentMessage = Message(messageText, viewModel.currentUser.value ?: "", userId?:"","", false, true)
                    ChatDatabase.currentChatList[index].chatList.add(currentMessage)
                    viewModel.updateDatabase()

                    if (viewModel.socketStatus.value != false) {
                        currentMessage.sent = true
                        val messageJson = Gson().toJson(currentMessage)
                        viewModel.webSocket?.send(messageJson?:"")
                    }

                    chatAdapter.submitList(ChatDatabase.currentChatList[index].chatList)
                    binding.messageRecyclerView.scrollToPosition(ChatDatabase.currentChatList[index].chatList.size - 1)  // Scroll to the latest message
                    binding.editMessage.text.clear()  // Clear input after sending
                    binding.noChat.visibility = if(ChatDatabase.currentChatList[index].chatList.isEmpty()) View.VISIBLE else View.GONE
                    binding.messageRecyclerView.visibility = if(ChatDatabase.currentChatList[index].chatList.isEmpty()) View.GONE else View.VISIBLE
                }
            }
        }
    }

    private fun startObservers(){
        lifecycleScope.launchWhenStarted {
            viewModel.getUserById(userId?:"").collect{
                currentUser = it
                binding.avatar.text = it.name.first().toString()
                binding.textView2.text = it.name
            }
            userId?.let {
                chatAdapter.submitList(ChatDatabase.currentChatList[index].chatList)
            }
        }

        viewModel.databaseUpdated.observe(viewLifecycleOwner){
            chatAdapter.notifyDataSetChanged()
            Log.e("ChatFragment", "startObservers: updated")
        }

        viewModel.socketStatus.observe(viewLifecycleOwner){
            if(it){
                binding.connectionStatus.text = "Connection Status: Connected"
            }else{
                binding.connectionStatus.text = "Connection Status: Not Connected"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ChatDatabase.currentScreen = ChatFragment::class.java.name
        ChatDatabase.currentOpenedUser = userId
    }

}
