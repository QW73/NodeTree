package com.example.domain.model

data class TreeNode(
    val id: String,
    val name: String,
    val parentId: String?,
    val children: List<TreeNode> = emptyList()
)