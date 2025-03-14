package com.example.nodetree.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.nodetree.R
import com.example.presentation.TreeNavigator

class TreeNavigatorImpl(private val navController: NavController) : TreeNavigator {
    override fun navigateToChild(nodeId: String) {
        navController.navigate(
            R.id.action_treeFragment_to_treeFragment, bundleOf("nodeId" to nodeId)
        )
    }

    override fun navigateUp() {
        navController.popBackStack()
    }

    override fun resetToRoot() {
        navController.popBackStack(R.id.treeFragment, true)
        navController.navigate(R.id.treeFragment)
    }
}