package com.example.presentation

import com.example.domain.model.TreeNode
import com.example.domain.usecase.AddNodeUseCase
import com.example.domain.usecase.DeleteAllNodesUseCase
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNodeByIdUseCase
import com.example.domain.usecase.GetRootNodeUseCase
import com.example.presentation.state.TreeUiState
import com.example.presentation.ui.TreeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever


class TreeViewModelTest {
    private lateinit var viewModel: TreeViewModel
    private lateinit var getRootNodeUseCase: GetRootNodeUseCase
    private lateinit var getNodeByIdUseCase: GetNodeByIdUseCase
    private lateinit var addNodeUseCase: AddNodeUseCase
    private lateinit var deleteNodeUseCase: DeleteNodeUseCase
    private lateinit var deleteAllNodesUseCase: DeleteAllNodesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getRootNodeUseCase = mock()
        getNodeByIdUseCase = mock()
        addNodeUseCase = mock()
        deleteNodeUseCase = mock()
        deleteAllNodesUseCase = mock()

        viewModel = TreeViewModel(
            getRootNodeUseCase,
            getNodeByIdUseCase,
            addNodeUseCase,
            deleteNodeUseCase,
            deleteAllNodesUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadNode with null id calls getRootNodeUseCase and updates uiState to Success`() =
        runTest {
            // Arrange
            val rootNode = TreeNode(id = "rootId", name = "Root", parentId = null)
            whenever(getRootNodeUseCase()).thenReturn(Result.success(rootNode))

            // Act
            viewModel.loadNode(null)
            testScheduler.advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value
            assertTrue(state is TreeUiState.Success)
            assertEquals(rootNode, (state as TreeUiState.Success).node)
            verify(getRootNodeUseCase).invoke()
        }

    @Test
    fun `loadNode with specific id calls getNodeByIdUseCase and updates uiState to Success`() =
        runTest {
            // Arrange
            val nodeId = "node1"
            val node = TreeNode(id = nodeId, name = "Node 1", parentId = "rootId")
            whenever(getNodeByIdUseCase(nodeId)).thenReturn(Result.success(node))

            // Act
            viewModel.loadNode(nodeId)
            testScheduler.advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value
            assertTrue(state is TreeUiState.Success)
            assertEquals(node, (state as TreeUiState.Success).node)
            verify(getNodeByIdUseCase).invoke(nodeId)
        }

    @Test
    fun `loadNode updates uiState to Error when use case fails`() = runTest {
        // Arrange
        val exception = IllegalStateException("Error loading node")
        whenever(getRootNodeUseCase()).thenReturn(Result.failure(exception))

        // Act
        viewModel.loadNode(null)
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Error)
        assertEquals("Error loading node", (state as TreeUiState.Error).message)
    }

    @Test
    fun `addChild adds new child and updates uiState`() = runTest {
        // Arrange
        val parentId = "parentId"
        val newNode = TreeNode(id = "newId", name = "New Node", parentId = parentId)
        whenever(addNodeUseCase(parentId)).thenReturn(Result.success(newNode))
        val currentNode =
            TreeNode(id = parentId, name = "Parent", parentId = null, children = emptyList())
        viewModel._uiState.value = TreeUiState.Success(currentNode)

        // Act
        viewModel.addChild(parentId)
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Success)
        val updatedNode = (state as TreeUiState.Success).node
        assertEquals(1, updatedNode.children.size)
        assertEquals(newNode, updatedNode.children.first())
        verify(addNodeUseCase).invoke(parentId)
    }

    @Test
    fun `deleteChild removes child and updates uiState`() = runTest {
        // Arrange
        val parentId = "parentId"
        val childId = "childId"
        val childNode = TreeNode(id = childId, name = "Child", parentId = parentId)
        val currentNode =
            TreeNode(id = parentId, name = "Parent", parentId = null, children = listOf(childNode))
        viewModel._uiState.value = TreeUiState.Success(currentNode)
        whenever(deleteNodeUseCase(childId)).thenReturn(Result.success(Unit))

        // Act
        viewModel.deleteChild(childId)
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Success)
        val updatedNode = (state as TreeUiState.Success).node
        assertTrue(updatedNode.children.isEmpty())
        verify(deleteNodeUseCase).invoke(childId)
    }

    @Test
    fun `clearAllNodes calls deleteAllNodesUseCase and reloads root node`() = runTest {
        // Arrange
        whenever(deleteAllNodesUseCase()).thenReturn(Result.success(Unit))
        val rootNode = TreeNode(id = "rootId", name = "Root", parentId = null)
        whenever(getRootNodeUseCase()).thenReturn(Result.success(rootNode))

        // Act
        viewModel.clearAllNodes()
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Success)
        assertEquals(rootNode, (state as TreeUiState.Success).node)
        verify(deleteAllNodesUseCase).invoke()
        verify(getRootNodeUseCase).invoke()
    }

    @Test
    fun `addChild updates uiState to Error when use case fails`() = runTest {
        // Arrange
        val parentId = "parentId"
        val exception = IllegalStateException("Error adding node")
        whenever(addNodeUseCase(parentId)).thenReturn(Result.failure(exception))
        val currentNode =
            TreeNode(id = parentId, name = "Parent", parentId = null, children = emptyList())
        viewModel._uiState.value = TreeUiState.Success(currentNode)

        // Act
        viewModel.addChild(parentId)
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Error)
        assertEquals("Error adding node", (state as TreeUiState.Error).message)
    }

    @Test
    fun `deleteChild updates uiState to Error when use case fails`() = runTest {
        // Arrange
        val childId = "childId"
        val exception = IllegalStateException("Failed to delete node")
        whenever(deleteNodeUseCase(childId)).thenReturn(Result.failure(exception))
        val currentNode = TreeNode(
            id = "parentId", name = "Parent", parentId = null, children = listOf(
                TreeNode(id = childId, name = "Child", parentId = "parentId")
            )
        )
        viewModel._uiState.value = TreeUiState.Success(currentNode)

        // Act
        viewModel.deleteChild(childId)
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Error)
        assertEquals("Failed to delete node", (state as TreeUiState.Error).message)
    }

    @Test
    fun `clearAllNodes updates uiState to Error when use case fails`() = runTest {
        // Arrange
        val exception = IllegalStateException("Failed to clear nodes")
        whenever(deleteAllNodesUseCase()).thenReturn(Result.failure(exception))

        // Act
        viewModel.clearAllNodes()
        testScheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is TreeUiState.Error)
        assertEquals("Failed to clear nodes", (state as TreeUiState.Error).message)
    }
}