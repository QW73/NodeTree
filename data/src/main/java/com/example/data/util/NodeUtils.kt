package com.example.data.util

import com.example.domain.model.TreeNode
import java.security.MessageDigest
import java.util.UUID

object NodeUtils {
    private fun generateNodeName(id: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(id.toByteArray())
        return hash.takeLast(20).joinToString("") { "%02x".format(it) }
    }

    fun createNode(parentId: String?): TreeNode {
        val id = UUID.randomUUID().toString()
        val name = generateNodeName(id)
        return TreeNode(id, name, parentId)
    }
}