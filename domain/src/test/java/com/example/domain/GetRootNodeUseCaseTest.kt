package com.example.domain

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.GetRootNodeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetRootNodeUseCaseTest {

    private lateinit var useCase: GetRootNodeUseCase
    private lateinit var repository: TreeRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetRootNodeUseCase(repository)
    }

    @Test
    fun `when repository returns success, use case returns success with root node`() = runTest {
        // Arrange
        val rootNode = TreeNode(id = "rootId", name = "rootName", parentId = null)
        whenever(repository.getRootNode()).thenReturn(Result.success(rootNode))

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(rootNode, result.getOrNull())
        verify(repository).getRootNode()
    }

    @Test
    fun `when repository fails, use case returns failure with exception`() = runTest {
        // Arrange
        val exception = IllegalStateException("Database error")
        whenever(repository.getRootNode()).thenReturn(Result.failure(exception))

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        assertEquals("Database error", result.exceptionOrNull()?.message)
        verify(repository).getRootNode()
    }
}