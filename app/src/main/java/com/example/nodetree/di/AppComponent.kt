package com.example.nodetree.di

import android.content.Context
import com.example.data.di.DatabaseModule
import com.example.data.di.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, RepositoryModule::class])
interface AppComponent {

    // fun inject(fragment: NodeFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}