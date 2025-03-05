package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.TreeDatabase
import com.example.data.db.dao.TreeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNodeDatabase(@ApplicationContext context: Context): TreeDatabase =
        Room.databaseBuilder(context, TreeDatabase::class.java, "tree_database").build()

    @Provides
    @Singleton
    fun provideTreeNodeDao(database: TreeDatabase): TreeDao = database.treeDao()
}