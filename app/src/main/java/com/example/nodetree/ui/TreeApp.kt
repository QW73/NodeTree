package com.example.nodetree.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nodetree.ui.utils.SystemUiConfig
import com.example.presentation.ui.TreeScreen

@Composable
fun TreeApp() {
    val navController = rememberNavController()

    SystemUiConfig()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavHost(navController = navController, startDestination = "tree/{nodeId}") {
                composable(route = "tree/{nodeId}", arguments = listOf(navArgument("nodeId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })) { backStackEntry ->
                    val nodeId = backStackEntry.arguments?.getString("nodeId")
                    TreeScreen(navController = navController, nodeId = nodeId)
                }
            }
        }
    }

}


