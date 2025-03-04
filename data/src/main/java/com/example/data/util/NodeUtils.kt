package com.example.data.util

import com.example.domain.model.Node
import java.security.MessageDigest
import java.util.UUID

object NodeUtils {
    fun generateNodeName(id: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(id.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }.takeLast(40)
    }

    fun createNode(parentId: String?): Node {
        val id = UUID.randomUUID().toString()
        val name = generateNodeName(id)
        return Node(id, name, parentId)
    }
}