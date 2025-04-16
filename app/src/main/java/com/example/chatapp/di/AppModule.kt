package com.example.chatapp.di

import android.content.Context
import android.content.SharedPreferences
import com.vivek.chatingapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferences {
//        return context.getSharedPreferences(Constant.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
//    }
//
//    @Singleton
//    @Provides
//    fun provideRemoteMsgHeaders(@ApplicationContext context: Context) =
//        HashMap<String,String>().apply {
//            put(Constant.REMOTE_MSG_AUTHORIZATION, String.format("key=%s",context.getString(R.string.firebase_server_key)))
//            put(Constant.REMOTE_MSG_CONTENT_TYPE,"application/json")
//        }
//
//}