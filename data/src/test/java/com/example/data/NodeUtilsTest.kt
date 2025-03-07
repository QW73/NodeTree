package com.example.data

import com.example.data.util.NodeUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.MessageDigest
import java.util.UUID

class NodeUtilsTest {

    @Test
    fun `createNode generates node with valid id, name, parentId, and empty children`() {
        // Arrange
        val parentId = "parent-123"

        // Act
        val node = NodeUtils.createNode(parentId)

        // Assert
        assertNotNull(node.id)
        assertTrue(node.id.isNotEmpty())
        UUID.fromString(node.id)

        val expectedName = NodeUtils.generateNodeName(node.id)
        assertEquals(expectedName, node.name)

        assertEquals(parentId, node.parentId)
        assertTrue(node.children.isEmpty())
    }

    @Test
    fun `createNode generates root node with null parentId`() {
        // Act
        val node = NodeUtils.createNode(null)

        // Assert
        assertNotNull(node.id)
        assertTrue(node.id.isNotEmpty())
        UUID.fromString(node.id)

        val expectedName = NodeUtils.generateNodeName(node.id)
        assertEquals(expectedName, node.name)

        assertEquals(null, node.parentId)
        assertTrue(node.children.isEmpty())
    }

    @Test
    fun `createNode generates unique IDs for different nodes`() {
        val nodes = (1..100).map { NodeUtils.createNode("parentId") }
        val uniqueIds = nodes.map { it.id }.toSet()
        assertEquals(nodes.size, uniqueIds.size)
    }

    @Test
    fun `createNode generates node name with correct format`() {
        // Act
        val node = NodeUtils.createNode("parentId")

        // Assert
        assertEquals(40, node.name.length)

        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(node.id.toByteArray())
        val expectedName = hash.takeLast(20).joinToString("") { "%02x".format(it) }
        assertEquals(expectedName, node.name)
    }

    @Test
    fun `generateNodeName returns precomputed hash for fixed ID`() {
        // Arrange
        val id = "fixed-id"
        val expectedHash = "48459d18e4fad3bfbb715df0ad16957f9f4011f0"

        // Act
        val actualHash = NodeUtils.generateNodeName(id)

        // Assert
        assertEquals(expectedHash, actualHash)
    }
}