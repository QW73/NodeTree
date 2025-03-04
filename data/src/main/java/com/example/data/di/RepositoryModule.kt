package com.example.data.di

import com.example.data.db.dao.NodeDao
import com.example.data.db.repository.TreeRepositoryImpl
import com.example.domain.repository.ITreeRepository
import com.example.domain.usecase.AddNodeUseCase
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNodeByIdUseCase
import com.example.domain.usecase.GetRootNodeUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTreeRepository(nodeDao: NodeDao): ITreeRepository = TreeRepositoryImpl(nodeDao)

    @Provides
    @Singleton
    fun provideGetRootNodeUseCase(repository: ITreeRepository): GetRootNodeUseCase =
        GetRootNodeUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNodeByIdUseCase(repository: ITreeRepository): GetNodeByIdUseCase =
        GetNodeByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideAddNodeUseCase(repository: ITreeRepository): AddNodeUseCase =
        AddNodeUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteNodeUseCase(repository: ITreeRepository): DeleteNodeUseCase =
        DeleteNodeUseCase(repository)
}