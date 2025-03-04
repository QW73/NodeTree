package com.example.data.db.repository

import com.example.data.db.dao.NodeDao
import com.example.data.db.mapper.NodeMapper
import com.example.data.db.model.NodeEntity
import com.example.data.util.NodeUtils
import com.example.domain.model.Node
import com.example.domain.repository.ITreeRepository

class TreeRepositoryImpl(private val nodeDao: NodeDao) : ITreeRepository {

    override suspend fun getRootNode(): Result<Node> = try {
        val root: NodeEntity? = nodeDao.getRoot()
        if (root == null) {
            val newNode = NodeUtils.createNode(null)
            nodeDao.insert(NodeMapper.toEntity(newNode))
            Result.success(newNode)
        } else {
            Result.success(getNodeWithChildren(root.id))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getNodeById(id: String): Result<Node> {
        return try {
            val entity = nodeDao.getNodeById(id)
                ?: return Result.failure(IllegalArgumentException("Node not found"))
            Result.success(getNodeWithChildren(entity.id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addNode(parentId: String?, name: String): Result<Node> {
        return try {
            val newNode = NodeUtils.createNode(parentId)
            nodeDao.insert(NodeMapper.toEntity(newNode))
            Result.success(newNode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNode(nodeId: String): Result<Unit> {
        return try {
            nodeDao.delete(nodeId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getNodeWithChildren(id: String): Node {
        val entity = nodeDao.getNodeById(id) ?: throw IllegalArgumentException("Node not found")
        val childrenEntities = nodeDao.getChildren(id)
        val children = childrenEntities.map { getNodeWithChildren(it.id) }
        return NodeMapper.toDomain(entity, children)
    }
}