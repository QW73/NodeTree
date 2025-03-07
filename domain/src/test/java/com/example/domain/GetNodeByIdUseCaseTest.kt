package com.example.domain

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.GetNodeByIdUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetNodeByIdUseCaseTest {
    private lateinit var useCase: GetNodeByIdUseCase
    private lateinit var repository: TreeRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetNodeByIdUseCase(repository)
    }

    @Test
    fun `when repository returns success, use case returns success with node`() = runTest {
        // Arrange
        val nodeId = "node1"
        val node = TreeNode(id = nodeId, name = "Node 1", parentId = "rootId")
        whenever(repository.getNodeById(nodeId)).thenReturn(Result.success(node))

        // Act
        val result = useCase(nodeId)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(node, result.getOrNull())
        verify(repository).getNodeById(nodeId)
    }

    @Test
    fun `when repository fails, use case returns failure with exception`() = runTest {
        // Arrange
        val nodeId = "node1"
        val exception = IllegalStateException("Node not found")
        whenever(repository.getNodeById(nodeId)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(nodeId)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).getNodeById(nodeId)
    }

    @Test
    fun `when node does not exist, use case returns failure with specific exception`() = runTest {
        // Arrange
        val nodeId = "nonExistentId"
        val exception = IllegalArgumentException("Node not found")
        whenever(repository.getNodeById(nodeId)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(nodeId)

        // Assert
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("Node not found", result.exceptionOrNull()?.message)
        verify(repository).getNodeById(nodeId)
    }

}