package com.example.data.db.repository

import com.example.data.db.dao.TreeDao
import com.example.data.db.mapper.TreeNodeMapper
import com.example.data.util.NodeUtils
import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository


class TreeRepositoryImpl(private val treeDao: TreeDao) : TreeRepository {

    override suspend fun getRootNode(): Result<TreeNode> = try {
        val root = treeDao.getRoot()
        if (root == null) {
            val newNode = NodeUtils.createNode(null)
            treeDao.insert(TreeNodeMapper.toEntity(newNode))
            Result.success(newNode)
        } else {
            val children =
                treeDao.getChildren(root.id).map { TreeNodeMapper.toDomain(it, emptyList()) }
            Result.success(TreeNodeMapper.toDomain(root, children))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getNodeById(id: String): Result<TreeNode> {
        return try {
            val entity = treeDao.getNodeById(id)
                ?: return Result.failure(IllegalArgumentException("Node not found"))
            val children = treeDao.getChildren(id).map { TreeNodeMapper.toDomain(it, emptyList()) }
            Result.success(TreeNodeMapper.toDomain(entity, children))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addNode(parentId: String?): Result<TreeNode> = try {
        val newNode = NodeUtils.createNode(parentId)
        treeDao.insert(TreeNodeMapper.toEntity(newNode))
        Result.success(newNode)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteNode(nodeId: String): Result<Unit> = try {
        treeDao.delete(nodeId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteAllNodes(): Result<Unit> = try {
        treeDao.deleteAll()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}