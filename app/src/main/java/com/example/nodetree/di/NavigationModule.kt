package com.example.nodetree.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.nodetree.navigation.TreeNavigatorImpl
import com.example.presentation.TreeNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object NavigationModule {

    @Provides
    @FragmentScoped
    fun provideNavController(fragment: Fragment): NavController {
        return fragment.findNavController()
    }

    @Provides
    @FragmentScoped
    fun provideTreeNavigator(navController: NavController): TreeNavigator {
        return TreeNavigatorImpl(navController)
    }
}