package com.example.data.di

import com.example.data.db.dao.TreeDao
import com.example.data.db.repository.TreeRepositoryImpl
import com.example.domain.repository.TreeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTreeRepository(nodeDao: TreeDao): TreeRepository = TreeRepositoryImpl(nodeDao)
}