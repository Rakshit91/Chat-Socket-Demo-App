package com.example.chatapp.di

import com.example.chatapp.repository.ChatRepository
import com.example.chatapp.repository.InternalChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    // Provide ChatRepository dependency
    @Provides
    fun provideChatRepository(): ChatRepository {
        return ChatRepository() // Or inject dependencies inside ChatRepository if needed
    }

    @Provides
    fun provideInternalChatRepository(): InternalChatRepository {
        return InternalChatRepository()
    }

}
