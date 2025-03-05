package com.example.presentation.state

import com.example.domain.model.TreeNode

sealed class TreeUiState {
    data object Loading : TreeUiState()
    data class Success(val node: TreeNode) : TreeUiState()
    data class Error(val message: String) : TreeUiState()
}