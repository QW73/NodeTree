package com.example.data.db.mapper

import com.example.data.db.model.NodeEntity
import com.example.domain.model.Node

object NodeMapper {
    fun toDomain(entity: NodeEntity, children: List<Node>): Node = Node(
        id = entity.id, name = entity.name, parentId = entity.parentId, children = children
    )

    fun toEntity(node: Node): NodeEntity = NodeEntity(
        id = node.id, name = node.name, parentId = node.parentId
    )
}