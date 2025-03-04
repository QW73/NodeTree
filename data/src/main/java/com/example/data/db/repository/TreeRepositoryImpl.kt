package com.example.data.db.repository

import com.example.data.db.dao.NodeDao
import com.example.data.db.mapper.NodeMapper
import com.example.data.util.NodeUtils
import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class TreeRepositoryImpl(private val nodeDao: NodeDao) : ITreeRepository {

    override suspend fun getRootNode(): Node {
        val root = nodeDao.getRoot() ?: run {
            val newRoot = NodeUtils.createNode(null)
            nodeDao.insert(NodeMapper.toEntity(newRoot))
            return newRoot
        }
        return getNodeWithChildren(root.id)
    }

    override suspend fun getNodeById(id: String): Node {
        val entity = nodeDao.getNodeById(id) ?: throw IllegalArgumentException("Node not found")
        return getNodeWithChildren(entity.id)
    }

    override suspend fun addNode(parentId: String?, name: String): Node {
        val newNode = NodeUtils.createNode(parentId)
        nodeDao.insert(NodeMapper.toEntity(newNode))
        return newNode
    }

    override suspend fun deleteNode(nodeId: String) {
        nodeDao.delete(nodeId)
    }

    private suspend fun getNodeWithChildren(id: String): Node {
        val entity = nodeDao.getNodeById(id) ?: throw IllegalArgumentException("Node not found")
        val childrenEntities = nodeDao.getChildren(id)
        val children = childrenEntities.map { getNodeWithChildren(it.id) }
        return NodeMapper.toDomain(entity, children)
    }
}