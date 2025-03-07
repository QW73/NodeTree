package com.example.domain

import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.DeleteNodeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteNodeUseCaseTest {
    private lateinit var useCase: DeleteNodeUseCase
    private lateinit var repository: TreeRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = DeleteNodeUseCase(repository)
    }

    @Test
    fun `when deleting node successfully, use case returns success`() = runTest {
        // Arrange
        val nodeId = "nodeId"
        whenever(repository.deleteNode(nodeId)).thenReturn(Result.success(Unit))

        // Act
        val result = useCase(nodeId)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())
        verify(repository).deleteNode(nodeId)
    }

    @Test
    fun `when deleting node fails, use case returns failure with exception`() = runTest {
        // Arrange
        val nodeId = "nodeId"
        val exception = IllegalStateException("Database error")
        whenever(repository.deleteNode(nodeId)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(nodeId)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).deleteNode(nodeId)
    }
}