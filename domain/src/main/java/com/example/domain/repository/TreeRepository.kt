package com.example.domain.repository

import com.example.domain.model.TreeNode

interface TreeRepository {
    suspend fun getRootNode(): Result<TreeNode>
    suspend fun getNodeById(id: String): Result<TreeNode>
    suspend fun addNode(parentId: String?): Result<TreeNode>
    suspend fun deleteNode(nodeId: String): Result<Unit>
    suspend fun deleteAllNodes(): Result<Unit>
}

