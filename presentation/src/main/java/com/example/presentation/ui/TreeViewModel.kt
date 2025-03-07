package com.example.presentation.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AddNodeUseCase
import com.example.domain.usecase.DeleteAllNodesUseCase
import com.example.domain.usecase.DeleteNodeUseCase
import com.example.domain.usecase.GetNodeByIdUseCase
import com.example.domain.usecase.GetRootNodeUseCase
import com.example.presentation.state.TreeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeViewModel @Inject constructor(
    private val getRootNodeUseCase: GetRootNodeUseCase,
    private val getNodeByIdUseCase: GetNodeByIdUseCase,
    private val addNodeUseCase: AddNodeUseCase,
    private val deleteNodeUseCase: DeleteNodeUseCase,
    private val deleteAllNodesUseCase: DeleteAllNodesUseCase
) : ViewModel() {

    val _uiState = MutableStateFlow<TreeUiState>(TreeUiState.Loading)
    val uiState: StateFlow<TreeUiState> = _uiState.asStateFlow()

    fun loadNode(nodeId: String? = null) {
        viewModelScope.launch {
            _uiState.value = TreeUiState.Loading
            val result = if (nodeId == null) {
                getRootNodeUseCase()
            } else {
                getNodeByIdUseCase(nodeId)
            }
            _uiState.value = result.fold(onSuccess = { TreeUiState.Success(it) },
                onFailure = { TreeUiState.Error(it.message ?: "Unknown error") })
        }
    }

    fun addChild(parentId: String) {
        viewModelScope.launch {
            addNodeUseCase(parentId).fold(onSuccess = { newNode ->
                val currentNode = (_uiState.value as? TreeUiState.Success)?.node
                if (currentNode != null) {
                    _uiState.value = TreeUiState.Success(
                        currentNode.copy(children = currentNode.children + newNode)
                    )
                }
            }, onFailure = {
                _uiState.value = TreeUiState.Error(it.message ?: "Error adding node")
            })
        }
    }

    fun deleteChild(nodeId: String) {
        viewModelScope.launch {
            deleteNodeUseCase(nodeId).fold(onSuccess = {
                val currentNode = (_uiState.value as? TreeUiState.Success)?.node
                if (currentNode != null) {
                    _uiState.value = TreeUiState.Success(
                        currentNode.copy(children = currentNode.children.filter { it.id != nodeId })
                    )
                }
            }, onFailure = {
                _uiState.value = TreeUiState.Error(it.message ?: "Failed to delete node")
            })
        }
    }

    fun clearAllNodes() {
        viewModelScope.launch {
            deleteAllNodesUseCase().fold(onSuccess = {
                _uiState.value = TreeUiState.Loading
                loadNode(null)
            }, onFailure = {
                _uiState.value = TreeUiState.Error(it.message ?: "Failed to clear nodes")
            })
        }
    }
}
