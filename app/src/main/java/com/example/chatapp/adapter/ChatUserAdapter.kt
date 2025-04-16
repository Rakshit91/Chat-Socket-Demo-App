package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.model.ChatUser

class ChatUserAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<ChatUser, ChatUserAdapter.ChatUserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_user, parent, false)
        return ChatUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ChatUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userName = view.findViewById<TextView>(R.id.textUserName)
        private val lastMessage = view.findViewById<TextView>(R.id.textLastMessage)
        private val avatar = view.findViewById<TextView>(R.id.avatar)

        fun bind(user: ChatUser) {
            avatar.text = user.name.first().toString()
            userName.text = user.name

            if(user.chatList.isEmpty()){
                lastMessage.text = user.lastMessage
            }else{
                lastMessage.text = user.chatList[user.chatList.size -1].text
            }


            itemView.setOnClickListener { onClick(user.name) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatUser>() {
        override fun areItemsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean = oldItem == newItem
    }
}
