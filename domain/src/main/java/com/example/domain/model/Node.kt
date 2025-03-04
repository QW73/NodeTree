package com.example.domain.model


data class Node(
    val id: String, val name: String, val parentId: String?, val children: List<Node> = emptyList()
)