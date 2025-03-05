package com.example.data.db.mapper

import com.example.data.db.model.TreeNodeEntity
import com.example.domain.model.TreeNode

object TreeNodeMapper {
    fun toDomain(entity: TreeNodeEntity, children: List<TreeNode>): TreeNode = TreeNode(
        id = entity.id, name = entity.name, parentId = entity.parentId, children = children
    )

    fun toEntity(node: TreeNode): TreeNodeEntity = TreeNodeEntity(
        id = node.id, name = node.name, parentId = node.parentId
    )
}