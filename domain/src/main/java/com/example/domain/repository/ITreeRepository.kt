package com.example.domain.repository

import com.example.domain.model.Node

interface ITreeRepository {
    suspend fun getRootNode(): Result<Node>
    suspend fun getNodeById(id: String): Result<Node>
    suspend fun addNode(parentId: String?, name: String): Result<Node>
    suspend fun deleteNode(nodeId: String): Result<Unit>
}

