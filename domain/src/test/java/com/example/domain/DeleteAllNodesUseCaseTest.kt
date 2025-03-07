package com.example.domain

import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.DeleteAllNodesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteAllNodesUseCaseTest {
    private lateinit var useCase: DeleteAllNodesUseCase
    private lateinit var repository: TreeRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = DeleteAllNodesUseCase(repository)
    }

    @Test
    fun `when deleting all nodes successfully, use case returns success`() = runTest {
        // Arrange
        whenever(repository.deleteAllNodes()).thenReturn(Result.success(Unit))

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())
        verify(repository).deleteAllNodes()
    }

    @Test
    fun `when deleting all nodes fails, use case returns failure with exception`() = runTest {
        // Arrange
        val exception = IllegalStateException("Database error")
        whenever(repository.deleteAllNodes()).thenReturn(Result.failure(exception))

        // Act
        val result = useCase()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).deleteAllNodes()
    }
}