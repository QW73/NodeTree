package com.example.domain.repository

import com.example.domain.model.Node

interface ITreeRepository {
    suspend fun getRootNode(): Node
    suspend fun getNodeById(id: String): Node
    suspend fun addNode(parentId: String?, name: String): Node
    suspend fun deleteNode(nodeId: String)
}

