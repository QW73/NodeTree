package com.example.nodetree.di

import android.content.Context
import com.example.data.di.DatabaseModule
import com.example.data.di.RepositoryModule
import com.example.domain.usecase.AddNodeUseCase
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNodeByIdUseCase
import com.example.domain.usecase.GetRootNodeUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, RepositoryModule::class])
interface AppComponent {
    fun getRootNodeUseCase(): GetRootNodeUseCase
    fun getNodeByIdUseCase(): GetNodeByIdUseCase
    fun addNodeUseCase(): AddNodeUseCase
    fun deleteNodeUseCase(): DeleteNodeUseCase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}