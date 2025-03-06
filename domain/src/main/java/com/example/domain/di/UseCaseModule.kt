package com.example.domain.di

import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.AddNodeUseCase
import com.example.domain.usecase.DeleteAllNodesUseCase
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNodeByIdUseCase
import com.example.domain.usecase.GetRootNodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetRootNodeUseCase(repository: TreeRepository) = GetRootNodeUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNodeByIdUseCase(repository: TreeRepository) = GetNodeByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideAddNodeUseCase(repository: TreeRepository) = AddNodeUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteNodeUseCase(repository: TreeRepository) = DeleteNodeUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteAllNodesUseCase(repository: TreeRepository) = DeleteAllNodesUseCase(repository)

}