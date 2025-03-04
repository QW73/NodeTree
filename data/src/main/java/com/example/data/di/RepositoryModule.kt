package com.example.data.di

import com.example.data.db.dao.NodeDao
import com.example.data.db.repository.TreeRepositoryImpl
import com.example.domain.repository.ITreeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTreeRepository(nodeDao: NodeDao): ITreeRepository = TreeRepositoryImpl(nodeDao)
}