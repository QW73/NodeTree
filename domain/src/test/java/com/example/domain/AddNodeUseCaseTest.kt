package com.example.domain

import com.example.domain.model.TreeNode
import com.example.domain.repository.TreeRepository
import com.example.domain.usecase.AddNodeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AddNodeUseCaseTest {
    private lateinit var useCase: AddNodeUseCase
    private lateinit var repository: TreeRepository

    @Before
    fun setUp() {
        repository = mock()
        useCase = AddNodeUseCase(repository)
    }

    @Test
    fun `when repository adds node successfully, use case returns success with new node`() =
        runTest {
            // Arrange
            val parentId = "parentId"
            val newNode = TreeNode(id = "newId", name = "New Node", parentId = parentId)
            whenever(repository.addNode(parentId)).thenReturn(Result.success(newNode))

            // Act
            val result = useCase(parentId)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(newNode, result.getOrNull())
            verify(repository).addNode(parentId)
        }

    @Test
    fun `when repository fails to add node, use case returns failure`() = runTest {
        // Arrange
        val parentId = "parentId"
        val exception = IllegalStateException("Cannot add node")
        whenever(repository.addNode(parentId)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(parentId)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(repository).addNode(parentId)
    }
}