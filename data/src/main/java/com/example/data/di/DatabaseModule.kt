package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.NodeDatabase
import com.example.data.db.dao.NodeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNodeDatabase(context: Context): NodeDatabase =
        Room.databaseBuilder(context, NodeDatabase::class.java, "node_database").build()

    @Provides
    @Singleton
    fun provideNodeDao(database: NodeDatabase): NodeDao = database.nodeDao()
}