package com.example.data

import android.database.sqlite.SQLiteException
import com.example.data.db.dao.TreeDao
import com.example.data.db.model.TreeNodeEntity
import com.example.data.db.repository.TreeRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class TreeRepositoryImplTest {

    private lateinit var repository: TreeRepositoryImpl
    private lateinit var treeDao: TreeDao

    @Before
    fun setUp() {
        treeDao = mock()
        repository = TreeRepositoryImpl(treeDao)
    }

    @Test
    fun `getRootNode returns root node when it exists`() = runTest {
        // Arrange
        val rootEntity = TreeNodeEntity(id = "rootId", name = "rootName", parentId = null)
        val childrenEntities = listOf(
            TreeNodeEntity(id = "child1", name = "child1Name", parentId = "rootId"),
            TreeNodeEntity(id = "child2", name = "child2Name", parentId = "rootId")
        )
        whenever(treeDao.getRoot()).thenReturn(rootEntity)
        whenever(treeDao.getChildren("rootId")).thenReturn(childrenEntities)

        // Act
        val result = repository.getRootNode()

        // Assert
        assertTrue(result.isSuccess)
        val rootNode = result.getOrNull()
        assertEquals("rootId", rootNode?.id)
        assertEquals("rootName", rootNode?.name)
        assertEquals(2, rootNode?.children?.size)
        assertEquals("child1", rootNode?.children?.get(0)?.id)
        assertEquals("child2", rootNode?.children?.get(1)?.id)
    }

    @Test
    fun `getNodeById returns node when it exists`() = runTest {
        // Arrange
        val nodeId = "node1"
        val nodeEntity = TreeNodeEntity(id = nodeId, name = "Node 1", parentId = "rootId")
        val childrenEntities = listOf(
            TreeNodeEntity(id = "child1", name = "Child 1", parentId = nodeId)
        )
        whenever(treeDao.getNodeById(nodeId)).thenReturn(nodeEntity)
        whenever(treeDao.getChildren(nodeId)).thenReturn(childrenEntities)

        // Act
        val result = repository.getNodeById(nodeId)

        // Assert
        assertTrue(result.isSuccess)
        val node = result.getOrNull()
        assertEquals(nodeId, node?.id)
        assertEquals("Node 1", node?.name)
        assertEquals(1, node?.children?.size)
    }

    @Test
    fun `getNodeById returns failure when node does not exist`() = runTest {
        // Arrange
        val nodeId = "nonExistentId"
        whenever(treeDao.getNodeById(nodeId)).thenReturn(null)

        // Act
        val result = repository.getNodeById(nodeId)

        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("Node not found", result.exceptionOrNull()?.message)
        verify(treeDao).getNodeById(nodeId)
    }

    @Test
    fun `addNode adds new node and returns success`() = runTest {
        // Arrange
        val parentId = "parentId"
        whenever(treeDao.insert(any())).thenReturn(Unit)

        // Act
        val result = repository.addNode(parentId)

        // Assert
        assertTrue(result.isSuccess)
        val newNode = result.getOrNull()
        assertNotNull(newNode)
        assertEquals(parentId, newNode?.parentId)
        verify(treeDao).insert(any())
    }

    @Test
    fun `addNode returns failure when insertion fails`() = runTest {
        val parentId = "parentId"
        val exception = SQLiteException("DB error")
        whenever(treeDao.insert(any())).thenThrow(exception)

        val result = repository.addNode(parentId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteNode returns success`() = runTest {
        // Arrange
        val nodeId = "nodeId"
        whenever(treeDao.delete(nodeId)).thenReturn(Unit)

        // Act
        val result = repository.deleteNode(nodeId)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())
        verify(treeDao).delete(nodeId)
    }

    @Test
    fun `deleteNode returns failure`() = runTest {
        val nodeId = "nodeId"
        val exception = SQLiteException("DB error")
        whenever(treeDao.delete(nodeId)).thenThrow(exception)

        val result = repository.deleteNode(nodeId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteAllNodes returns success`() = runTest {
        // Arrange
        whenever(treeDao.deleteAll()).thenReturn(Unit)

        // Act
        val result = repository.deleteAllNodes()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())
        verify(treeDao).deleteAll()
    }

    @Test
    fun `deleteAllNodes returns failure`() = runTest {
        val exception = SQLiteException("DB error")
        whenever(treeDao.deleteAll()).thenThrow(exception)

        val result = repository.deleteAllNodes()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}